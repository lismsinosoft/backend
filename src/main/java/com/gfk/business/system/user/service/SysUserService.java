package com.gfk.business.system.user.service;


import com.gfk.business.system.user.controller.form.UserFrozenForm;
import com.gfk.business.system.user.model.SysUser;

/**
 * 管理员信息 服务
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public interface SysUserService {

    /**
     * 根据管理员名查找管理员
     *
     * @param username 管理员名
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 冻结管理员
     *
     * @param list
     */
    void frozen(UserFrozenForm list);
}
