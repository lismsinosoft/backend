package com.gfk.business.data.controller;


import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.TypeActForm;
import com.gfk.business.data.controller.form.TypeForm;
import com.gfk.business.data.controller.form.YearForm;
import com.gfk.business.data.model.T5p3;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.BusinessDataServiceNew;
import com.gfk.business.user.service.UserProjectMappingService;
import com.gfk.common.context.ContextHolder;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务数据查询
 *
 * @author wzl
 * @version 1.0  2023/03/11
 */
@Slf4j
@Api(tags = "业务数据查询V2")
@RestController
@RequestMapping("/business_data/v2")
@RequiredArgsConstructor
public class BusinessDataNewController {

    private final BusinessDataServiceNew businessDataServiceNew;

    private final UserProjectMappingService userProjectMappingService;


    /**
     * Spending chart(T1P1)
     */
    @ApiOperation("Spending chart(T1P1)")
    @ApiOperationSupport(order = 1)
    @PostMapping("/spending")
    public Result<T1p1VO> t1p1(@RequestBody BaseForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T1p1VO result = businessDataServiceNew.t1p1Chart(form);
        return Result.success(result);
    }

    /**
     * SalesDate graph(T1P2)
     */
    @ApiOperation("SalesDate graph(T1P2)")
    @ApiOperationSupport(order = 2)
    @PostMapping("/sales_date")
    public Result<T1p2VO> t1p2(@RequestBody BaseForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T1p2VO result = businessDataServiceNew.t1p2Chart(form);
        return Result.success(result);
    }

    /**
     * SalesDate chart(T1P3)
     */
    @ApiOperation("SalesDate chart(T1P3)")
    @ApiOperationSupport(order = 3)
    @PostMapping("/t1p3")
    public Result<T1p3VO> t1p3(@RequestBody BaseForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T1p3VO result = businessDataServiceNew.t1p3Chart(form);
        return Result.success(result);
    }

    /**
     * Investment Detail chart(T2P1 & T2P2)
     */
    @ApiOperation("Investment Detail chart(T2P1 & T2P2)")
    @ApiOperationSupport(order = 4)
    @PostMapping("/t2p1-t2p2")
    public Result<T2VO> t2(@RequestBody YearForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T2VO result = businessDataServiceNew.t2Chart(form);
        return Result.success(result);
    }

    /**
     * Considering chart（T3P1）
     */
    @ApiOperation("Considering chart（T3P1）")
    @ApiOperationSupport(order = 5)
    @PostMapping("/t3p1")
    public Result<List<T3p1VO>> t3p1(@RequestBody YearForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        List<T3p1VO> result = businessDataServiceNew.t3p1(form);
        return Result.success(result);
    }

    /**
     * Performance Annually chart(T4P1)
     */
    @ApiOperation("Performance Annually chart(T4P1)")
    @ApiOperationSupport(order = 6)
    @PostMapping("/t4p1")
    public Result<T4p1VO> t4p1(@RequestBody BaseForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T4p1VO result = businessDataServiceNew.t4p1Chart(form);
        return Result.success(result);
    }

    /**
     * Performance Detail chart(T4P2)
     */
    @ApiOperation("Performance Detail chart(T4P2)")
    @ApiOperationSupport(order = 7)
    @PostMapping("/t4p2")
    public Result<T4p2VO> t4p2(@RequestBody BaseForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T4p2VO result = businessDataServiceNew.t4p2Chart(form);
        return Result.success(result);
    }

    /**
     * Performance Annually chart(T4P3)
     */
    @ApiOperation("Performance Annually chart(T4P3)")
    @ApiOperationSupport(order = 8)
    @PostMapping("/t4p3")
    public Result<T4p3VO> t4p3(@RequestBody TypeForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T4p3VO result = businessDataServiceNew.t4p3Chart(form);
        return Result.success(result);
    }

    /**
     * Performance Detail chart(T4P4)
     */
    @ApiOperation("Performance Detail chart(T4P4)")
    @ApiOperationSupport(order = 9)
    @PostMapping("/t4p4")
    public Result<T4p4VO> t4p4(@RequestBody TypeForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T4p4VO result = businessDataServiceNew.t4p4Chart(form);
        return Result.success(result);
    }

    /**
     * Product Group chart(T5P1)
     */
    @ApiOperation("Product Group chart(T5P1)")
    @ApiOperationSupport(order = 10)
    @PostMapping("/t5p1")
    public Result<T5p1VO> t5p1(@RequestBody TypeActForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T5p1VO result = businessDataServiceNew.t5p1Chart(form);
        return Result.success(result);
    }

    /**
     * ROI Annually Total chart(T5P2)
     */
    @ApiOperation("ROI Annually Total chart(T5P2)")
    @ApiOperationSupport(order = 11)
    @PostMapping("/t5p2")
    public Result<T5p2VO> t5p2(@RequestBody TypeActForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T5p2VO result = businessDataServiceNew.t5p2Chart(form);
        return Result.success(result);
    }

    /**
     * Curve chart(T5P3)
     */
    @ApiOperation("Curve chart(T5P3)")
    @ApiOperationSupport(order = 12)
    @PostMapping("/t5p3")
    public Result<List<T5p3>> t5p3(@RequestBody TypeActForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        List<T5p3> result = businessDataServiceNew.t5p3Chart(form);
        return Result.success(result);
    }

    /**
     * Impression Detail chart(T5P4)
     */
    @ApiOperation("Impression Detail chart(T5P4)")
    @ApiOperationSupport(order = 13)
    @PostMapping("/t5p4")
    public Result<T5p4VO> t5p4(@RequestBody TypeActForm form) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), form.getProjectId())) {
            return Result.forbidden();
        }
        T5p4VO result = businessDataServiceNew.t5p4Chart(form);
        return Result.success(result);
    }

    /**
     * 产品筛选条件查询
     */
    @ApiOperation("产品筛选条件查询")
    @ApiOperationSupport(order = 99)
    @PostMapping("/filters/product")
    public Result<List<String>> getProduct(@RequestParam Long projectId) {
        List<String> result = businessDataServiceNew.getProduct(projectId);
        return Result.success(result);
    }

    /**
     * T2P1年份查询
     */
    @ApiOperation("T2P1年份查询")
    @ApiOperationSupport(order = 101)
    @PostMapping("/filters/t2p1")
    public Result<List<Integer>> getT2p1Years(@RequestParam Long projectId) {
        List<Integer> result = businessDataServiceNew.getT2p1Years(projectId);
        return Result.success(result);
    }

    /**
     * T3P1年份查询
     */
    @ApiOperation("T3P1年份查询")
    @ApiOperationSupport(order = 102)
    @PostMapping("/filters/t3p1")
    public Result<List<Integer>> getT3p1Years(@RequestParam Long projectId) {
        List<Integer> result = businessDataServiceNew.getT3p1Years(projectId);
        return Result.success(result);
    }

    /**
     * T5筛选条件查询
     */
    @ApiOperation("T5筛选条件查询")
    @ApiOperationSupport(order = 103)
    @PostMapping("/filters/t5p1")
    public Result<List<TreeVO>> getT5p1FilterTree(@RequestParam Long projectId, @RequestParam String product) {
        List<TreeVO> result = businessDataServiceNew.getT5Filter(projectId, product);
        return Result.success(result);
    }
}
