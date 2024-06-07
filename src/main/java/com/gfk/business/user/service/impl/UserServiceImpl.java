package com.gfk.business.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.mapper.PermissionMapper;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.user.mapper.UserMapper;
import com.gfk.business.user.mapper.UserPermissionMappingMapper;
import com.gfk.business.user.mapper.UserProjectMappingMapper;
import com.gfk.business.user.model.User;
import com.gfk.business.user.model.UserPermissionMapping;
import com.gfk.business.user.model.form.UserForgetPwdForm;
import com.gfk.business.user.model.form.UserListForm;
import com.gfk.business.user.model.form.UserLoginForm;
import com.gfk.business.user.model.form.UserRegisterForm;
import com.gfk.business.user.model.vo.*;
import com.gfk.business.user.service.UserService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.constant.BaseConstants;
import com.gfk.common.context.Context;
import com.gfk.common.context.ContextHolder;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.key.CacheConstants;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import com.gfk.common.util.EncryptUtils;
import com.gfk.common.util.PojoUtils;
import com.gfk.framework.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户 服务层实现
 *
 * @author : wzl
 * @version 1.0 2023/02/26
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * 登录失败重试次数
     */
    private static final int LOGIN_RETRY_COUNT = 5;

    /**
     * 登录失败过期时间 15分钟
     */
    private static final long LOGIN_FAIL_EXPIRE_TIME = 15;

    private final CacheRepository cacheRepository;

    private final UserMapper userMapper;

    private final PermissionMapper permissionMapper;

    private final UserPermissionMappingMapper userPermissionMappingMapper;

    private final UserProjectMappingMapper userProjectMappingMapper;

    /**
     * 用户登录
     *
     * @param form 用户登录表单
     * @return 用户对象
     */
    @Override
    public UserLoginResultVO login(UserLoginForm form) {
        String account = form.getAccount();
        String password = form.getPassword();

        User user = findUserByAccount(account);

        if (null == user) {
            throw new BusinessException("错误的用户名密码组合");
        }

        if (user.getIsDeleted()) {
            log.info("{}用户已被禁用", user.getName());
            throw new BusinessException("用户已被禁用，请联系管理员");
        }
        //锁定判断
        if (user.getLockFlag()) {
            Date unlockTime = user.getUnlockTime();
            if (null != unlockTime) {
                if (DateUtil.compare(new Date(), unlockTime) > 0) {
                    log.info("{}用户已被锁定", user.getName());
                    throw new BusinessException("用户已被锁定！");
                }
            }
            //解锁用户
            switchLockFlag(user.getId(), false);
        }
        String passwd = EncryptUtils.encryptPassword(account, password, user.getSalt());
        if (!passwd.equals(user.getPassword())) {
            // 账号登录密码多次错误锁定用户
            Integer failedCount = (Integer) cacheRepository.get(CacheConstants.STAFF_USER_LOGIN_FAILED, String.valueOf(user.getId()));
            failedCount = (null == failedCount ? 1 : (++failedCount));
            cacheRepository.set(CacheConstants.STAFF_USER_LOGIN_FAILED, String.valueOf(user.getId()), failedCount, LOGIN_FAIL_EXPIRE_TIME, TimeUnit.MINUTES);
            if (failedCount >= LOGIN_RETRY_COUNT) {
                // 锁定用户
                switchLockFlag(user.getId(), true);
                log.info("{}用户已被锁定", user.getName());
                throw new BusinessException("用户已被锁定！");
            }
            String errorMsg = StrUtil.format("密码错误，您还有{}次机会输入，超出次数后用户将被禁用", LOGIN_RETRY_COUNT - failedCount);
            throw new BusinessException(errorMsg);
        }

        //删除登录错误缓存
        cacheRepository.delete(CacheConstants.STAFF_USER_LOGIN_FAILED, String.valueOf(user.getId()));

        // 生成token
        String sign = JwtUtil.sign(BaseConstants.STAFF_USER_TYPE, account, passwd, String.valueOf(user.getId()));
        cacheRepository.set(CacheConstants.STAFF_USER_TOKEN, sign, sign, JwtUtil.EXPIRE_TIME * 2, TimeUnit.MILLISECONDS);

        //设置token
        UserLoginResultVO loginVO = new UserLoginResultVO();
        loginVO.setName(user.getName());
        loginVO.setNameEn(user.getNameEn());
        loginVO.setToken(sign);
        List<UserPermissionVO> userPermission = userPermissionMappingMapper.getUserPermission(user.getId());
        List<Project> userProject = userProjectMappingMapper.getUserProject(user.getId());
        List<UserProjectVO> userProjectVOList = userProject.stream().map(p -> {
            UserProjectVO userProjectVO = new UserProjectVO();
            BeanUtil.copyProperties(p, userProjectVO);
            // 图片地址处理
            userProjectVO.setPicUrl(p.getFullPicUrl());
            userProjectVO.setPicUrl2(p.getFullPicUrl2());
            return userProjectVO;
        }).collect(Collectors.toList());

        loginVO.setPermissionList(userPermission);
        loginVO.setProjectList(userProjectVOList);
        return loginVO;
    }

    @Override
    public PageResponse<UserInfoVO> queryUserList(PageRequest<UserListForm> form) {
        UserListForm filter = form.getFilter();
        if (null == filter) {
            filter = new UserListForm();
        }
        Page<User> userPage = new Page<User>(form.getPageNo(), form.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery()
                .like(StrUtil.isNotBlank(filter.getName()), User::getName, filter.getName())
                .like(StrUtil.isNotBlank(filter.getNameEn()), User::getNameEn, filter.getNameEn())
                .eq(StrUtil.isNotBlank(filter.getAccount()), User::getAccount, filter.getAccount());
        Page<User> result = userMapper.selectPage(userPage, queryWrapper);
        List<UserInfoVO> list = result.getRecords().stream().map(UserInfoVO::new).collect(Collectors.toList());
        return PageResponse.ofCustomizedPage(userPage, list);
    }

    /**
     * 新增用户对象
     *
     * @param entity 待新增对象
     * @return 用户对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User add(User entity) {
        if (StrUtil.isBlank(entity.getAccount())) {
            throw new BusinessException("用户账号为空");
        }
        if (StrUtil.isBlank(entity.getPassword())) {
            throw new BusinessException("用户密码为空");
        }
        User user = findUserByAccount(entity.getAccount());
        if (user != null) {
            throw new BusinessException("账号已存在");
        }
        String password = entity.getPassword();
        String salt = String.valueOf(System.currentTimeMillis());
        String encryptPassword = EncryptUtils.encryptPassword(entity.getAccount(), password, salt);
        entity.setPassword(encryptPassword);
        entity.setSalt(salt);
        entity.setLockFlag(false);
        entity.setCreateBy(ContextHolder.currentContext().get(Context.PreferenceName.userName));
        entity.setCreateTime(new Date());
        userMapper.insert(entity);
        UserPermissionMapping userPermissionMapping = new UserPermissionMapping();
        userPermissionMapping.setUserId(entity.getId());
        userPermissionMapping.setPermissionId(1L);
        userPermissionMapping.setSort(1);
        userPermissionMapping.setIsDeleted(false);
        userPermissionMappingMapper.insert(userPermissionMapping);
        return entity;
    }

    /**
     * 更新用户对象
     *
     * @param entity 待更新对象
     * @return 用户对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User update(User entity) {
        if (null == entity.getId()) {
            throw new BusinessException("用户ID为空");
        }
        User oldEntity = userMapper.selectById(entity.getId());
        if (oldEntity == null) {
            throw new BusinessException("对象不存在，请检查");
        }
        if (StrUtil.isNotBlank(entity.getPassword())) {
            String salt = oldEntity.getSalt();
            String encryptPassword = EncryptUtils.encryptPassword(entity.getAccount(), entity.getPassword(), salt);
            entity.setPassword(encryptPassword);
        }
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        oldEntity.setUpdateBy("admin");
        userMapper.updateById(oldEntity);
        return oldEntity;
    }

    /**
     * 删除用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        userMapper.deleteBatchIds(CollectionUtil.toList(ids.split(",")));
        return true;
    }

    /**
     * 用户注册
     *
     * @param entity 用户注册表单
     * @return 用户注册返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRegisterResultVO register(UserRegisterForm entity) {
//        if(entity.getCode() !=null){
//            if(cacheRepository.get(CacheConstants.USER_REG_CODE, entity.getMobile())!=null){
//                String redisStr = cacheRepository.get(CacheConstants.USER_REG_CODE, entity.getMobile()).toString();
//                if(entity.getCode().equals(redisStr)){
//                    //验证码通过进入下一步
//                    entity.setEmail(entity.getEmail().toLowerCase());
//                    User user=userMapper.selectOne(Wrappers.<User>lambdaQuery()
//                            .eq(User::getAccount, entity.getEmail())
//                            .eq(User::getName, entity.getName())
//                            .eq(User::getPhone, entity.getMobile()));
//                    if(user==null){
//                        throw new BusinessException("该用户不存在，请检查");
//                    }
////                    if (BaseConstants.STAFF_USER_STATUS_BLOCKED == user.getIsActive()) {
////                        throw new BusinessException("用户已被禁用，请联系管理员");
////                    }
//
//                    //生成临时token
//                    String sign = JwtUtil.temporarySign(String.valueOf(user.getId()), entity.getCode());
//                    cacheRepository.set(CacheConstants.STAFF_USER_TEMPORARY_TOKEN, sign, sign, 10, TimeUnit.MINUTES);
//                    UserRegisterResultVO resultVO=new UserRegisterResultVO();
//                    resultVO.setId(user.getId());
//                    resultVO.setToken(sign);
//                    return resultVO;
//                }else{
//                    throw new BusinessException("验证码不正确");
//                }
//            }else{
//                throw new BusinessException("验证码已过期");
//            }
//        }else{
//            throw new BusinessException("验证码不能为空");
//        }
        return null;
    }

    /**
     * 锁定/解锁
     *
     * @param userId 用户id
     * @param status 锁定状态
     * @return
     */
    @Override
    public void switchLockFlag(Long userId, Boolean status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("未找到该用户，请检查");
        }
        user.setLockFlag(status);
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
    }

    /**
     * 用户token校验
     *
     * @param token token字符串
     * @return 是否通过
     */
    @Override
    public Boolean tokenCheck(String token) {
        String redisToken = (String) cacheRepository.get(CacheConstants.STAFF_USER_TOKEN, token);
        if (StrUtil.isNotBlank(redisToken)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 根据账户获得用户信息
     *
     * @param account 邮箱
     * @return
     */
    @Override
    public User findUserByAccount(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount, account);
        User userInfo = userMapper.selectOne(wrapper);
        return userInfo;
    }

    /**
     * 用户忘记密码
     *
     * @param user 用户
     */
    public void forgetPwd(UserForgetPwdForm user) {

//        user.setEmail(user.getEmail().toLowerCase());
//        //通过name mobile email 判断是否存在
//        User userInfo = findUserByEmailMobile(user);
//        if(userInfo == null){
//           throw new BusinessException("请核对您输入的信息有误");
//        }
//
//        if(userInfo.getLockFlag()){
//            throw new BusinessException("您的账号已被冻结,无法设置密码");
//        }
//
//        if(user.getCode() !=null){
//            if(cacheRepository.get(CacheConstants.USER_REG_CODE, user.getMobile())!=null){
//                String redisStr = cacheRepository.get(CacheConstants.USER_REG_CODE, user.getMobile()).toString();
//
//                if(user.getCode().equals(redisStr)){
//                    //验证码通过进入下一步，报错注册信息
//                    QueryWrapper<User> wrapper = new QueryWrapper<>();
//                    wrapper.lambda().eq(User::getAccount, user.getEmail());
//                    wrapper.lambda().eq(User::getPhone, user.getMobile());
//                    User userInf = userMapper.selectOne(wrapper);
//
//                    //设置密码
//                    String salt = String.valueOf(System.currentTimeMillis());
//                    userInfo.setSalt(salt);
//                    // 密码加密（与shiro密码校验时的解密机制一致）
//                    String password = EncryptUtils.encryptPassword(userInf.getAccount(), user.getPwd(), userInf.getSalt());
//                    userInf.setPassword(password);
//                    userMapper.updateById(userInf);
//
//                }else{
//                    throw new BusinessException("验证码不正确");
//                }
//            }else{
//                throw new BusinessException("验证码已过期");
//            }
//
//        }else{
//            throw new BusinessException("验证码不正确");
//        }
    }

    /**
     * 修改密码
     * @param form    修改密码表单
     */
//    public void changePwd(UserChangePwdForm form) {
//        String account = form.getAccount();
//        String password = form.getOldPwd();
//
//        User user = findUserByAccount(account);
//        if (user == null) {
//            throw new BusinessException("未找到该用户，请重新再试");
//        }
//        if (user.getLockFlag()) {
//            log.info("{}用户已被禁用", user.getName());
//            throw new BusinessException("用户已被禁用，请联系管理员");
//        }
//        String passwd = EncryptUtils.encryptPassword(account, password, user.getSalt());
//        if (!passwd.equals(user.getPassword())) {
//            throw new BusinessException("密码错误，请检查!");
//        }
//
//        //设置密码
//        String salt = String.valueOf(System.currentTimeMillis());
//        user.setSalt(salt);
//        // 密码加密（与shiro密码校验时的解密机制一致）
//        String newPassword = EncryptUtils.encryptPassword(account, form.getNewPwd(), user.getSalt());
//        user.setPassword(newPassword);
//        userMapper.updateById(user);
//    }

/*****************************  private method  ********************************/
    /**
     * 根据邮箱手机号获得用户信息
     *
     * @param user 邮箱
     * @return
     */
    private User findUserByEmailMobile(UserForgetPwdForm user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount, user.getEmail());
        wrapper.lambda().eq(User::getPhone, user.getMobile());
        return userMapper.selectOne(wrapper);
    }

    /**
     * 私有方法，根据token获取用户Entity
     *
     * @param token 用户token
     * @return 用户entity
     */
    private User tokenValidate(String token) {
        if (StrUtil.isBlank(token)) {
            throw new BusinessException("token信息有误");
        }
        String redisToken = (String) cacheRepository.get(CacheConstants.STAFF_USER_TOKEN, token);
        if (StrUtil.isBlank(redisToken)) {
            throw new BusinessException("找不到token信息");
        }
        String userId = JwtUtil.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("未找到该用户，请重新再试");
        }
        return user;
    }
}
