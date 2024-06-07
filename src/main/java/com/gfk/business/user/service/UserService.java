package com.gfk.business.user.service;

import com.gfk.business.user.model.User;
import com.gfk.business.user.model.form.UserListForm;
import com.gfk.business.user.model.form.UserLoginForm;
import com.gfk.business.user.model.form.UserRegisterForm;
import com.gfk.business.user.model.vo.UserInfoVO;
import com.gfk.business.user.model.vo.UserLoginResultVO;
import com.gfk.business.user.model.vo.UserRegisterResultVO;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户 服务层
 *
 * @author : wzl
 * @version 1.0 2023/02/26
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param form 用户登录表单
     * @return 用户对象
     */
    UserLoginResultVO login(UserLoginForm form);

    /**
     * 查询用户列表
     *
     * @param form 查询条件form
     */
    PageResponse<UserInfoVO> queryUserList(PageRequest<UserListForm> form);

    /**
     * 新增用户对象
     *
     * @param entity 待新增对象
     * @return 用户对象
     */
    @Transactional(rollbackFor = Exception.class)
    User add(User entity);


    /**
     * 更新用户对象
     *
     * @param entity 待更新对象
     * @return 用户对象
     */
    @Transactional(rollbackFor = Exception.class)
    User update(User entity);


    /**
     * 删除用户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean del(String ids);

    /**
     * 用户注册
     *
     * @param entity 用户注册表单
     * @return 用户注册返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    UserRegisterResultVO register(UserRegisterForm entity);

    /**
     * 锁定/解锁
     *
     * @param userId 用户id
     * @param status 状态
     */
    void switchLockFlag(Long userId, Boolean status);

    /**
     * 用户token校验
     *
     * @param token token字符串
     * @return 是否通过
     */
    Boolean tokenCheck(String token);


    /**
     * 根据邮箱获得用户信息
     *
     * @param account 邮箱
     * @return 用户实体
     */
    User findUserByAccount(String account);

//    /**
//     * 修改密码
//     *
//     * @param entity 修改密码表单
//     * @return 用户修改密码返回结果
//     */
//    void changePwd(UserChangePwdForm entity);
}
