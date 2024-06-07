package com.gfk.business.system.project.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.system.project.model.vo.LanguageTextVo;
import com.gfk.business.system.project.service.ProjectService;
import com.gfk.common.constant.BaseConstants;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageResponse;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 项目管理
 *
 * @author wzl
 * @date 2023/03/11
 */
@Slf4j
@Api(tags = "1 - 项目管理")
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    /**
     * 项目列表
     */
    @ApiOperation("项目列表")
    @ApiOperationSupport(order = 2)
    @PostMapping("/list")
    public Result<PageResponse<Project>> list(@RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        Page<Project> page = projectService.pageList(null);
        PageResponse<Project> pageResponse = PageResponse.ofPage(page);
        return Result.success(pageResponse);
    }

    /**
     * 项目文本列表
     */
    @ApiOperation("项目文本列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/text")
    @ApiIgnore
    public Result<List<LanguageTextVo>> getProjectTextList(@RequestParam("projectId") Long projectId,
                                                           @RequestParam(value = "language", required = false) String language) {
        return Result.success(projectService.getProjectLanguageText(projectId, language));
    }

    /**
     * 新增项目
     */
    //@Log(title = "新增项目", businessType = BusinessType.INSERT)
    @ApiOperation("新增项目")
    @ApiOperationSupport(order = 1, ignoreParameters = {"id", "createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/add")
    public Result<String> add(@RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                              @RequestParam("code") @ApiParam(value = "项目code", required = true) String code,
                              @RequestParam("name") @ApiParam(value = "项目名称", required = true) String name,
                              @RequestParam("nameEn") @ApiParam(value = "项目英文名称", required = true) String nameEn,
                              @RequestParam("picFile1") @ApiParam(value = "项目图片1", required = true) MultipartFile picFile1,
                              @RequestParam("picFile2") @ApiParam(value = "项目图片2", required = true) MultipartFile picFile2
    ) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        Project entity = new Project(code, name, nameEn);
        entity.setCreateBy("admin");
        entity.setUpdateBy("admin");
        String result = projectService.addWithPic(entity, picFile1, picFile2);
        return Result.success(result);
    }

    /**
     * 更新项目
     */
    //@Log(title = "更新项目", businessType = BusinessType.UPDATE)
    @ApiOperation("更新项目")
    @ApiOperationSupport(order = 4, ignoreParameters = {"createTime", "updateTime", "createBy", "updateBy"})
    @PostMapping("/update")
    public Result<Project> update(@RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                  @RequestParam("projectId") @ApiParam(value = "项目id", required = true) Long projectId,
                                  @RequestParam(value = "code", required = false) @ApiParam(value = "项目code", required = false) String code,
                                  @RequestParam(value = "name", required = false) @ApiParam(value = "项目名称", required = false) String name,
                                  @RequestParam(value = "nameEn", required = false) @ApiParam(value = "项目英文名称", required = false) String nameEn,
                                  @RequestParam(value = "picFile1", required = false) @ApiParam(value = "项目图片1", required = false) MultipartFile picFile1,
                                  @RequestParam(value = "picFile2", required = false) @ApiParam(value = "项目图片2", required = false) MultipartFile picFile2) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        Project entity = new Project(code, name, nameEn);
        entity.setId(projectId);
        if ("null".equalsIgnoreCase(entity.getCode())
                || "".equalsIgnoreCase(entity.getCode())) {
            entity.setCode(null);
        }
        if ("null".equalsIgnoreCase(entity.getName())
                || "".equalsIgnoreCase(entity.getName())) {
            entity.setName(null);
        }
        if ("null".equalsIgnoreCase(entity.getNameEn())
                || "".equalsIgnoreCase(entity.getNameEn())) {
            entity.setNameEn(null);
        }
        Project result = projectService.update(entity, picFile1, picFile2);
        return Result.success(result);
    }

    /**
     * 删除项目
     */
    //@Log(title = "删除项目", businessType = BusinessType.DELETE)
    @ApiOperation("删除项目")
    @ApiOperationSupport(order = 5)
    @GetMapping("/delete")
    public Result<String> delete(@RequestParam("adminKey") @ApiParam(value = "管理员秘钥", required = true) String adminKey,
                                 @RequestParam("projectId") @ApiParam(value = "项目id", required = true) String projectId) {
        if (!BaseConstants.ADMIN_KEY.equalsIgnoreCase(adminKey)) {
            throw new BusinessException("请输入正确的key");
        }
        projectService.del(projectId);
        return Result.success();
    }

}
