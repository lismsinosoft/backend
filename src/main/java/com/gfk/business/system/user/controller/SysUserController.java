package com.gfk.business.system.user.controller;

import com.gfk.business.system.user.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理员信息
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Api(tags = "管理员管理")
//@RestController
//@RequestMapping("/system/user/sys_user")
@Slf4j
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

//    /**
//     * 管理员列表
//     * @param form 分页请求参数
//     * @return 管理员列表
//     */
////    @RequiresPermissions("system:sysUser:list")
//    @ApiOperation("管理员列表")
//    @ApiOperationSupport(order = 1)
//    @PostMapping("/list")
//    public SimpleResponseForm<PageResponseForm<SysUser>> list(@RequestBody PageRequestForm<QueryUserForm> form) {
//        List<SysUser> list = sysUserService.list(form);
//        return page(form, list);
//    }
//
//    /**
//     * 新增管理员
//     * @param entity 管理员信息
//     * @return 管理员信息
//     */
//    //@Log(title = "新增管理员", businessType = BusinessType.INSERT)
//    @ApiOperation(value = "保存管理员信息")
//    @ApiOperationSupport(order = 2, ignoreParameters = {"id","createTime","updateTime","createBy","updateBy","roleList"})
//    @PostMapping("/add")
//    public SimpleResponseForm<String> add(@Valid @RequestBody SysUser entity) {
//        sysUserService.add(entity);
//        return success();
//    }
//
//    /**
//     * 更新
//     *
//     * @param entity 管理员信息
//     * @return 管理员信息
//     */
//    //@Log(title = "更新管理员信息", businessType = BusinessType.UPDATE)
//    @ApiOperation(value = "更新管理员信息")
//    @ApiOperationSupport(order = 3, ignoreParameters = {"createTime", "updateTime", "createBy", "updateBy", "password", "roleList"})
//    @PostMapping("/update")
//    public SimpleResponseForm<String> update(@Valid @RequestBody SysUser entity) {
//        sysUserService.update(entity);
//        return success();
//    }
//
//    /**
//     * 删除
//     * @param ids 待删除的ID数组
//     */
//    //@Log(title = "删除管理员信息", businessType = BusinessType.DELETE)
//    @ApiOperation(value = "删除管理员信息")
//    @ApiOperationSupport(order = 4)
//    @GetMapping("/delete")
//    public SimpleResponseForm<String> delete(String ids) {
//        sysUserService.delete(ids);
//        return success();
//    }
//
//    /**
//     * 重置密码
//     * @param form
//     * @return
//     */
//    //@Log(title = "重置密码", businessType = BusinessType.UPDATE)
//    @ApiOperation(value = "重置密码")
//    @ApiOperationSupport(order = 5)
//    @PostMapping(value = "/reset")
//    public SimpleResponseForm<String> reset(@Valid @RequestBody ResetPasswordForm form) {
//        sysUserService.resetPassword(form);
//        return success();
//    }
//
//    /**
//     * 冻结/解冻
//     * @param list
//     * @return
//     */
//    //@Log(title = "管理员冻结/解冻", businessType = BusinessType.UPDATE)
//    @ApiOperation(value = "冻结/解冻")
//    @ApiOperationSupport(order = 6)
//    @PostMapping("/frozen")
//    public SimpleResponseForm<String> frozen(@Valid @RequestBody List<UserFrozenForm> list) {
//        sysUserService.frozen(list);
//        return success();
//    }
//
//    /**
//     * 获取管理员信息
//     * @param id 管理员ID
//     */
//    @ApiOperation(value = "获取管理员信息")
//    @ApiOperationSupport(order = 10)
//    @GetMapping("/info")
//    public SimpleResponseForm<SysUser> info(String id) {
//        return success(sysUserService.info(id));
//    }
//
//    /**
//     * 获取当前管理员信息
//     */
//    @ApiOperation(value = "获取当前管理员信息")
//    @ApiOperationSupport(order = 11)
//    @GetMapping("/current_user_info")
//    public SimpleResponseForm<SysUser> currentUserInfo(){
//        return success(sysUserService.info(JwtUtil.getUserId()));
//    }
//
//    /**
//     * 重置当前管理员密码
//     */
//    //@Log(title = "重置当前管理员密码", businessType = BusinessType.UPDATE)
//    @ApiOperation(value = "重置当前管理员密码")
//    @ApiOperationSupport(order = 12)
//    @PostMapping("/reset_current_user")
//    public SimpleResponseForm<String> resetCurrentUser(@Valid @RequestBody ResetCurrentUserPwdForm form){
//        sysUserService.resetCurrentUserPwd(form);
//        return success();
//    }
//
//    /**
//     * 根据管理员名称获取管理员信息
//     */
//    @ApiOperation("根据管理员名称获取管理员列表")
//    @ApiOperationSupport(order = 13)
//    @ApiImplicitParam(name = "name", value = "管理员名称")
//    @GetMapping("/find_by_name")
//    public SimpleResponseForm<List<SysUser>> findByName(String name) {
//        return success(sysUserService.findByName(name));
//    }

}
