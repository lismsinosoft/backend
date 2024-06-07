package com.gfk.business.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.user.model.UserPermissionMapping;
import com.gfk.business.user.model.form.UserPermissionMappingAddForm;
import com.gfk.business.user.model.form.UserPermissionQueryForm;
import com.gfk.common.model.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户权限关联 服务层
 *
 * @author wzl
 * @date 2023/02/26
 */
public interface UserPermissionMappingService {

    /**
     * 查询用户权限关联列表
     *
     * @param form 查询条件
     * @return 用户权限关联集合
     */
    Page<UserPermissionMapping> pageList(PageRequest<UserPermissionQueryForm> form);

    /**
     * 新增用户权限关联对象
     *
     * @param entity 待新增对象
     * @return 用户权限关联对象
     */
    @Transactional(rollbackFor = Exception.class)
    int add(List<UserPermissionMappingAddForm> entity);


    /**
     * 删除用户权限关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean del(String ids);

}
