package com.gfk.business.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.mapper.PermissionMapper;
import com.gfk.business.system.permission.model.Permission;
import com.gfk.business.user.mapper.UserMapper;
import com.gfk.business.user.mapper.UserPermissionMappingMapper;
import com.gfk.business.user.model.User;
import com.gfk.business.user.model.UserPermissionMapping;
import com.gfk.business.user.model.form.UserPermissionMappingAddForm;
import com.gfk.business.user.model.form.UserPermissionQueryForm;
import com.gfk.business.user.service.UserPermissionMappingService;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户权限关联 服务层实现
 *
 * @author wzl
 * @version 1.1 2023/6/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserPermissionMappingServiceImpl implements UserPermissionMappingService {

    private final UserPermissionMappingMapper userPermissionMappingMapper;

    private final UserMapper userMapper;

    private final PermissionMapper permissionMapper;

    /**
     * 查询用户权限关联列表
     *
     * @param form 查询条件
     * @return 用户权限关联集合
     */
    @Override
    public Page<UserPermissionMapping> pageList(PageRequest<UserPermissionQueryForm> form) {
        Page<UserPermissionMapping> queryPage = new Page<>(form.getPageNo(), form.getPageSize());
        UserPermissionQueryForm filter = Optional.ofNullable(form.getFilter()).orElseGet(UserPermissionQueryForm::new);
        LambdaQueryWrapper<UserPermissionMapping> queryWrapper = Wrappers.<UserPermissionMapping>lambdaQuery()
                .eq(UserPermissionMapping::getIsDeleted, false)
                .eq(null != filter.getUserId(), UserPermissionMapping::getUserId, filter.getUserId())
                .eq(null != filter.getPermissionId(), UserPermissionMapping::getPermissionId, filter.getPermissionId());
        return userPermissionMappingMapper.selectPage(queryPage, queryWrapper);
    }

    /**
     * 新增用户权限关联对象
     *
     * @param entityList 待新增对象
     * @return 用户权限关联对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int add(List<UserPermissionMappingAddForm> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            throw new BusinessException("传入参数为空");
        }
        List<Long> userIds = entityList.stream().map(UserPermissionMappingAddForm::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(userIds)) {
            throw new BusinessException("用户ID为空");
        }
        List<Long> permissionIds = entityList.stream().map(UserPermissionMappingAddForm::getPermissionId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(permissionIds)) {
            throw new BusinessException("项目ID为空");
        }
        Map<Long, User> userMap = Optional.ofNullable(userMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getId, userIds)))
                .orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(User::getId, Function.identity(), (oldVal, newVal) -> newVal));
        Map<Long, Permission> projectMap = Optional.ofNullable(permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().in(Permission::getId, permissionIds)))
                .orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(Permission::getId, Function.identity(), (oldVal, newVal) -> newVal));
        Map<Long, List<UserPermissionMappingAddForm>> userGroup = entityList.stream().collect(Collectors.groupingBy(UserPermissionMappingAddForm::getUserId));
        int total = 0;
        for (Map.Entry<Long, List<UserPermissionMappingAddForm>> entry : userGroup.entrySet()) {
            Long userId = entry.getKey();
            List<UserPermissionMappingAddForm> list = entry.getValue();
            if (null == userId || !userMap.containsKey(userId)) {
                throw new BusinessException("用户id不存在" + userId);
            }
            List<UserPermissionMapping> saveList = list.stream().map(form -> {
                if (null == form.getPermissionId() || !projectMap.containsKey(form.getPermissionId())) {
                    throw new BusinessException("权限id不存在" + form.getPermissionId());
                }
                UserPermissionMapping mapping = new UserPermissionMapping();
                BeanUtil.copyProperties(form, mapping);
                mapping.setIsDeleted(false);
                return mapping;
            }).collect(Collectors.toList());
            userPermissionMappingMapper.delete(Wrappers.<UserPermissionMapping>lambdaQuery().eq(UserPermissionMapping::getUserId, userId));
            int i = userPermissionMappingMapper.batchInsert(saveList);
            total += i;
        }
        return total;
    }

    /**
     * 删除用户权限关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        userPermissionMappingMapper.deleteBatchIds(CollectionUtil.toList(ids.split(",")));
        return true;
    }

}
