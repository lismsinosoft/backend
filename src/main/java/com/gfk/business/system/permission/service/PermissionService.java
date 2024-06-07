package com.gfk.business.system.permission.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.model.Permission;
import com.gfk.business.system.permission.model.form.PermissionAddForm;
import com.gfk.common.model.PageRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限数据 服务层
 *
 * @author wzl
 * @date 2023/02/26
 */
public interface PermissionService {

    /**
     * 查询权限数据列表
     *
     * @param form 查询条件
     * @return 权限数据集合
     */
    Page<Permission> list(PageRequest<Permission> form);

    /**
     * 新增权限数据对象
     *
     * @param entity 待新增对象
     * @return 权限数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    Permission add(PermissionAddForm entity);


    /**
     * 更新权限数据对象
     *
     * @param entity 待更新对象
     * @return 权限数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    Permission update(Permission entity);


    /**
     * 删除权限数据对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean del(String ids);

}
