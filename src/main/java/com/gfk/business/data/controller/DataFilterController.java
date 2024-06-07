package com.gfk.business.data.controller;


import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.model.vo.ProductGroupFilterVO;
import com.gfk.business.data.service.DataFilterService;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 筛选条件查询
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Slf4j
@Api(tags = "筛选条件查询")
@RestController
@RequestMapping("/api/filter")
@RequiredArgsConstructor
public class DataFilterController {

    private final DataFilterService dataFilterService;


    /**
     * Product Names filter
     */
    @ApiOperation("Product Names filter")
    @ApiOperationSupport(order = 1)
    @PostMapping("/product/names")
    public Result<List<String>> queryProductNames(@RequestBody BaseForm form) {
        List<String> result = dataFilterService.queryProductNames(form);
        return Result.success(result);
    }

    /**
     * Product Names filter
     */
    @ApiOperation("Product Names filter")
    @ApiOperationSupport(order = 1)
    @PostMapping("/product/group")
    public Result<List<ProductGroupFilterVO>> queryProductGroups(@RequestBody BaseForm form) {
        List<ProductGroupFilterVO> result = dataFilterService.queryProductGroups(form);
        return Result.success(result);
    }

    /**
     * Product filter
     */
    @ApiOperation("Product filter")
    @ApiOperationSupport(order = 2)
    @GetMapping("/products")
    public Result<List<String>> queryAllProducts(@RequestParam Integer projectId) {
        List<String> result = dataFilterService.queryProducts(projectId);
        return Result.success(result);
    }
}
