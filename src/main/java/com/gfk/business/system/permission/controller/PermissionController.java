package com.gfk.business.system.permission.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.model.Permission;
import com.gfk.business.system.permission.model.form.PermissionAddForm;
import com.gfk.business.system.permission.service.PermissionService;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 权限数据管理
 *
 * @author wzl
 * @date 2023/02/26
 */
@Slf4j
@Api(tags = "权限数据管理")
@ApiIgnore
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;


    /**
     * 权限数据列表
     */
    @ApiOperation("权限数据列表")
    @ApiOperationSupport(order = 1)
    @PostMapping("/list")
    public Result<PageResponse<Permission>> list(@RequestBody PageRequest<Permission> form) {
        Page<Permission> list = permissionService.list(form);
        return Result.success(PageResponse.ofPage(list));
    }

    /**
     * 新增权限数据
     */
    //@Log(title = "新增权限数据", businessType = BusinessType.INSERT)
    @ApiOperation("新增权限数据")
    @ApiOperationSupport(order = 2, ignoreParameters = {"id", "createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/add")
    public Result<Permission> add(@Valid @RequestBody PermissionAddForm form) {
        Permission result = permissionService.add(form);
        return Result.success(result);
    }

    /**
     * 更新权限数据
     */
    //@Log(title = "更新权限数据", businessType = BusinessType.UPDATE)
    @ApiOperation("更新权限数据")
    @ApiOperationSupport(order = 3, ignoreParameters = {"createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/update")
    public Result<Permission> update(@Valid @RequestBody Permission entity) {
        Permission result = permissionService.update(entity);
        return Result.success(result);
    }

    /**
     * 删除权限数据
     */
    //@Log(title = "删除权限数据", businessType = BusinessType.DELETE)
    @ApiOperation("删除权限数据")
    @ApiOperationSupport(order = 4)
    @GetMapping("/delete")
    public Result<String> delete(String ids) {
        permissionService.del(ids);
        return Result.success();
    }

}
