package com.gfk.business.system.permission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.mapper.PermissionMapper;
import com.gfk.business.system.permission.model.Permission;
import com.gfk.business.system.permission.model.form.PermissionAddForm;
import com.gfk.business.system.permission.service.PermissionService;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageRequest;
import com.gfk.common.util.PojoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限数据 服务层实现
 *
 * @author wzl
 * @date 2023/02/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;


    /**
     * 查询权限数据列表
     *
     * @param form 查询条件
     * @return 权限数据集合
     */
    @Override
    public Page<Permission> list(PageRequest<Permission> form) {
        Page<Permission> queryPage = new Page<>(form.getPageNo(), form.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = Wrappers.<Permission>lambdaQuery();
        return permissionMapper.selectPage(queryPage, queryWrapper);
    }

    /**
     * 新增权限数据对象
     *
     * @param form 待新增对象
     * @return 权限数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Permission add(PermissionAddForm form) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(form, permission);
        permission.setIsDeleted(false);
        permissionMapper.insert(permission);
        return permission;
    }

    /**
     * 更新权限数据对象
     *
     * @param entity 待更新对象
     * @return 权限数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Permission update(Permission entity) {
        Permission oldEntity = permissionMapper.selectById(entity.getId());
        if (oldEntity == null) {
            throw new BusinessException("对象不存在，请检查");
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        permissionMapper.updateById(oldEntity);
        return oldEntity;
    }

    /**
     * 删除权限数据对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        permissionMapper.deleteBatchIds(CollectionUtil.toList(ids.split(",")));
        return true;
    }

}
