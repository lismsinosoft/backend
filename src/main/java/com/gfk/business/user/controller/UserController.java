package com.gfk.business.user.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.user.model.User;
import com.gfk.business.user.model.UserPermissionMapping;
import com.gfk.business.user.model.UserProjectMapping;
import com.gfk.business.user.model.form.UserListForm;
import com.gfk.business.user.model.form.UserPermissionMappingAddForm;
import com.gfk.business.user.model.form.UserPermissionQueryForm;
import com.gfk.business.user.model.form.UserProjectMappingAddForm;
import com.gfk.business.user.model.vo.UserInfoVO;
import com.gfk.business.user.model.vo.UserProjectMappingVO;
import com.gfk.business.user.service.UserPermissionMappingService;
import com.gfk.business.user.service.UserProjectMappingService;
import com.gfk.business.user.service.UserService;
import com.gfk.common.constant.BaseConstants;
import com.gfk.common.context.ContextHolder;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理
 *
 * @author wzl
 * @version 1.01 2023/02/26
 */
@Api(tags = "3 - 用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final UserPermissionMappingService userPermissionMappingService;

    private final UserProjectMappingService userProjectMappingService;

    @ApiOperation("用户列表查询")
    @ApiOperationSupport(order = 1)
    @PostMapping("/list")
    public Result<PageResponse<UserInfoVO>> queryUserList(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                                          @RequestParam(value = "name", required = false) @ApiParam(value = "用户名称", required = false) String name,
                                                          @RequestParam(value = "nameEn", required = false) @ApiParam(value = "用户名称英文", required = false) String nameEn,
                                                          @RequestParam(value = "account", required = false) @ApiParam(value = "用户账号", required = false) String account) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        PageRequest<UserListForm> form = new PageRequest<>();
        form.setPageNo(1);
        form.setPageSize(1000);
        UserListForm filter = new UserListForm(name, nameEn, account);
        form.setFilter(filter);
        return Result.success(userService.queryUserList(form));
    }

    /**
     * 新增用户
     */
    //@Log(title = "新增用户", businessType = BusinessType.INSERT)
    @ApiOperation("新增用户")
    @ApiOperationSupport(order = 2, ignoreParameters = {"id", "createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/add")
    public Result<User> add(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                            @RequestParam(value = "name", required = true) @ApiParam(value = "用户名称", required = true) String name,
                            @RequestParam(value = "nameEn", required = true) @ApiParam(value = "用户名称英文", required = true) String nameEn,
                            @RequestParam(value = "account", required = true) @ApiParam(value = "用户账号", required = true) String account,
                            @RequestParam(value = "password", required = true) @ApiParam(value = "用户密码", required = true) String password,
                            @RequestParam(value = "email", required = false) @ApiParam(value = "用户邮箱", required = false) String email,
                            @RequestParam(value = "phone", required = false) @ApiParam(value = "用户手机号", required = false) String phone) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        User entity = new User();
        entity.setName(name);
        entity.setNameEn(nameEn);
        entity.setAccount(account);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setPhone(phone);
        User result = userService.add(entity);
        return Result.success(result);
    }

    /**
     * 更新用户
     */
    //@Log(title = "更新用户", businessType = BusinessType.UPDATE)
    @ApiOperation("更新用户")
    @ApiOperationSupport(order = 3, ignoreParameters = {"createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/update")
    public Result<User> update(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                               @RequestParam(value = "userId", required = true) @ApiParam(value = "用户id", required = true) Long userId,
                               @RequestParam(value = "name", required = true) @ApiParam(value = "用户名称", required = true) String name,
                               @RequestParam(value = "nameEn", required = true) @ApiParam(value = "用户名称英文", required = true) String nameEn,
                               @RequestParam(value = "account", required = true) @ApiParam(value = "用户账号", required = true) String account,
                               @RequestParam(value = "password", required = true) @ApiParam(value = "用户密码", required = true) String password,
                               @RequestParam(value = "email", required = false) @ApiParam(value = "用户邮箱", required = false) String email,
                               @RequestParam(value = "phone", required = false) @ApiParam(value = "用户手机号", required = false) String phone) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        User entity = new User();
        entity.setId(userId);
        if (StrUtil.isNotBlank(name)) {
            entity.setName(name);
        }
        if (StrUtil.isNotBlank(nameEn)) {
            entity.setNameEn(nameEn);
        }
        if (StrUtil.isNotBlank(account)) {
            entity.setAccount(account);
        }
        if (StrUtil.isNotBlank(password)) {
            entity.setPassword(password);
        }
        if (StrUtil.isNotBlank(email)) {
            entity.setEmail(email);
        }
        if (StrUtil.isNotBlank(phone)) {
            entity.setPhone(phone);
        }
        User result = userService.update(entity);
        return Result.success(result);
    }

    /**
     * 删除用户
     */
    //@Log(title = "删除用户", businessType = BusinessType.DELETE)
    @ApiOperation("删除用户")
    @ApiOperationSupport(order = 4)
    @GetMapping("/delete")
    public Result<String> deleteUser(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                     @RequestParam(value = "userId", required = true) @ApiParam(value = "用户id", required = true) String userId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        userService.del(userId);
        return Result.success();
    }

    /**
     * 用户权限关联列表
     */
    @ApiIgnore
    @ApiOperation("用户权限关联列表")
    @ApiOperationSupport(order = 21)
    @PostMapping("/permission/list")
    public Result<PageResponse<UserPermissionMapping>> list(@RequestBody PageRequest<UserPermissionQueryForm> form) {
        ContextHolder.currentContext().adminValid();
        Page<UserPermissionMapping> result = userPermissionMappingService.pageList(form);
        return Result.success(PageResponse.ofPage(result));
    }

    /**
     * 新增用户权限关联
     */
    //@Log(title = "新增用户权限关联", businessType = BusinessType.INSERT)
    @ApiIgnore
    @ApiOperation("新增用户权限关联")
    @ApiOperationSupport(order = 22, ignoreParameters = {"id", "createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/permission/add")
    public Result<Integer> add(@Valid @RequestBody List<UserPermissionMappingAddForm> form) {
        ContextHolder.currentContext().adminValid();
        int result = userPermissionMappingService.add(form);
        return Result.success(result);
    }

    /**
     * 删除用户权限关联
     */
    @ApiIgnore
    @ApiOperation("删除用户权限关联")
    @ApiOperationSupport(order = 24)
    @PostMapping("/permission/delete/{ids}")
    public Result<String> deleteUserPermission(@PathVariable("ids") String ids) {
        ContextHolder.currentContext().adminValid();
        userPermissionMappingService.del(ids);
        return Result.success();
    }

    /**
     * 用户项目关联列表
     */
    @ApiOperation("用户项目关联列表")
    @ApiOperationSupport(order = 31)
    @PostMapping("/project/list")
    public Result<PageResponse<UserProjectMappingVO>> userProjectList(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                                                      @RequestParam(value = "userId", required = false) @ApiParam(value = "用户ID", required = false) Long userId,
                                                                      @RequestParam(value = "projectId", required = false) @ApiParam(value = "项目ID", required = false) Long projectId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        PageRequest<UserProjectMapping> form = new PageRequest<>();
        form.setPageNo(1);
        form.setPageSize(1000);
        UserProjectMapping filter = new UserProjectMapping(userId, projectId);
        form.setFilter(filter);
        PageResponse<UserProjectMappingVO> page = userProjectMappingService.pageList(form);
        return Result.success(page);
    }

    /**
     * 新增用户项目关联
     */
    //@Log(title = "新增用户项目关联", businessType = BusinessType.INSERT)
    @ApiOperation("新增用户项目关联")
    @ApiOperationSupport(order = 32, ignoreParameters = {"id", "createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/project/add")
    public Result<Integer> addUserProject(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                          @RequestParam(value = "userId", required = true) @ApiParam(value = "用户ID", required = true) Long userId,
                                          @RequestParam(value = "projectId", required = true) @ApiParam(value = "项目ID", required = true) Long projectId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        if (null == userId) {
            throw new BusinessException("用户ID为空");
        }
        UserProjectMappingAddForm entity = new UserProjectMappingAddForm(userId, projectId);
        int result = userProjectMappingService.add(entity);
        return Result.success(result);
    }

    /**
     * 删除用户项目关联
     */
    //@Log(title = "删除用户项目关联", businessType = BusinessType.DELETE)
    @ApiOperation("删除用户项目关联")
    @ApiOperationSupport(order = 34)
    @PostMapping("/project/delete/{ids}")
    public Result<String> deleteUserProject(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                            @RequestParam(value = "userId", required = true) @ApiParam(value = "用户ID", required = true) Long userId,
                                            @RequestParam(value = "projectId", required = true) @ApiParam(value = "项目ID", required = true) Long projectId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        userProjectMappingService.deleteByUserIdProjectId(userId, projectId);
        return Result.success();
    }

    /**
     * 删除用户项目权限
     */
    @ApiOperation("删除用户项目权限")
    @ApiOperationSupport(order = 40)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permission", value = "权限", dataType = "String", paramType = "query",
                    allowableValues = "dashboard,simulator,optimizer")
    })
    @PostMapping("/project/permission/delete")
    public Result<String> deleteUserProjectPermission(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                                      @RequestParam(value = "projectId", required = true) @ApiParam(value = "项目ID", required = true) Long projectId,
                                                      @RequestParam(value = "permission", required = true) @ApiParam(value = "权限", required = true) String permission,
                                                      @RequestParam(value = "userId", required = false) @ApiParam(value = "用户ID", required = false) Long userId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        userProjectMappingService.deleteProjectPermission(projectId, permission, userId);
        return Result.success("删除成功");
    }


    /**
     * 创建用户项目权限
     */
    @ApiOperation("创建用户项目权限")
    @ApiOperationSupport(order = 41)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permission", value = "权限", dataType = "String", paramType = "query",
                    allowableValues = "dashboard,simulator,optimizer")
    })
    @PostMapping("/project/permission/create")
    public Result<String> createUserProjectPermission(@RequestParam(value = "adminKey", required = true) @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                                      @RequestParam(value = "projectId", required = true) @ApiParam(value = "项目ID", required = true) Long projectId,
                                                      @RequestParam(value = "permission", required = true) @ApiParam(value = "权限", required = true) String permission,
                                                      @RequestParam(value = "userId", required = false) @ApiParam(value = "用户ID", required = false) Long userId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        userProjectMappingService.createProjectPermission(projectId, permission, userId);
        return Result.success("创建成功");
    }
}
