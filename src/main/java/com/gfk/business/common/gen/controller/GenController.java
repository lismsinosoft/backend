package com.gfk.business.common.gen.controller;

import com.gfk.business.common.gen.controller.form.GenEntityForm;
import com.gfk.business.common.gen.controller.form.GenEntitySqlForm;
import com.gfk.business.common.gen.controller.form.GenFileForm;
import com.gfk.business.common.gen.controller.form.GenTargetPathForm;
import com.gfk.business.common.gen.service.GenService;
import com.gfk.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 代码生成
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Slf4j
@Api(tags = "[a]代码生成")
@RestController
@RequestMapping("/api/gen")
@RequiredArgsConstructor
public class GenController{

    private final GenService genService;


    /**
     * 生成实体相关代码
     * @param dto 实体类信息
     */
//    @ApiOperation("生成实体相关代码")
    @PostMapping("/gen_code")
    public Result<String> genCode(GenEntityForm dto) {
        genService.generatorCodeForEntity(dto);
        return Result.success();
    }


    /**
     * 生成指定目录下的实体相关代码
     * @param form
     */
    @ApiOperation(value = "生成指定目录下的实体相关代码", notes = "包含到实体所在包下的目录")
    @GetMapping("/gen_code_to_target_path")
    public Result<String> genCodeToTargetPath(@Valid GenTargetPathForm form) {
        genService.genCodeFromTargetPath(form);
        return Result.success();
    }

    /**
     * 生成指定文件
     * @param form
     */
    @ApiOperation(value = "生成指定文件", notes = "自定义模板和参数进行指定文件生成")
    @PostMapping("/gen_file")
    public Result<String> genFile(@RequestBody @Valid GenFileForm form) {
        genService.genFile(form);
        return Result.success();
    }

    @ApiOperation(value = "生成实体SQL", notes = "参数为模型类包名+类名")
    @PostMapping("/get_model_sql")
    public Result<String> getModelSql(@Valid GenEntitySqlForm form) {
        return Result.success(genService.createModelSql(form));
    }
}
