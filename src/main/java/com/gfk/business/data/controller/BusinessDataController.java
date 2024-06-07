package com.gfk.business.data.controller;


import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.GroupForm;
import com.gfk.business.data.controller.form.ProductNameForm;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.BusinessDataService;
import com.gfk.common.model.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 业务数据查询
 *
 * @author wzl
 * @version 1.0  2023/03/11
 */
@Slf4j
@Api(tags = "业务数据查询")
@RestController
@RequestMapping("/business_data")
@RequiredArgsConstructor
public class BusinessDataController {

    private final BusinessDataService businessDataService;


    /**
     * SalesDate chart
     */
    @ApiOperation("SalesDate chart")
    @ApiOperationSupport(order = 1)
    @PostMapping("/sales_date")
    public Result<SalesDateChartVO> salesDateChart(@RequestBody BaseForm form) {
        SalesDateChartVO result = businessDataService.salesDateChart(form);
        return Result.success(result);
    }

    /**
     * Spending chart
     */
    @ApiOperation("Spending chart")
    @ApiOperationSupport(order = 2)
    @PostMapping("/spending")
    public Result<SpendingChartVO> spendingChart(@RequestBody BaseForm form) {
        SpendingChartVO result = businessDataService.spendingChart(form);
        return Result.success(result);
    }

    /**
     * Considering chart
     */
    @ApiOperation("Considering chart")
    @ApiOperationSupport(order = 3)
    @PostMapping("/considering")
    public Result<List<ConsideringChartVO>> consideringChart(@RequestBody BaseForm form) {
        List<ConsideringChartVO> result = businessDataService.consideringChart(form);
        return Result.success(result);
    }

    /**
     * Investment Detail chart
     */
    @ApiOperation("Investment Detail chart")
    @ApiOperationSupport(order = 4)
    @PostMapping("/investment_detail")
    public Result<InvestmentDetailChartVO> investmentDetailChart(@RequestBody BaseForm form) {
        InvestmentDetailChartVO result = businessDataService.investmentDetailChart(form);
        return Result.success(result);
    }

    /**
     * Performance Annually chart
     */
    @ApiOperation("Performance Annually chart")
    @ApiOperationSupport(order = 5)
    @PostMapping("/performance_annually")
    public Result<PerformanceAnnuallyChartVO> performanceAnnuallyChart(@RequestBody BaseForm form) {
        PerformanceAnnuallyChartVO result = businessDataService.performanceAnnuallyChart(form);
        return Result.success(result);
    }

    /**
     * Performance Detail chart
     */
    @ApiOperation("Performance Detail chart")
    @ApiOperationSupport(order = 6)
    @PostMapping("/performance_detail")
    public Result<PerformanceDetailChartVO> performanceDetailChart(@RequestBody BaseForm form) {
        PerformanceDetailChartVO result = businessDataService.performanceDetailChart(form);
        return Result.success(result);
    }

    /**
     * Product Group chart
     */
    @ApiOperation("Product Group chart")
    @ApiOperationSupport(order = 7)
    @PostMapping("/product_group")
    public Result<ProductGroupChartVO> productGroupChart(@RequestBody GroupForm form) {
        ProductGroupChartVO result = businessDataService.productGroupChart(form);
        return Result.success(result);
    }

    /**
     * ROI Annually Total chart
     */
    @ApiOperation("ROI Annually Total chart")
    @ApiOperationSupport(order = 8)
    @PostMapping("/roi_annually_total")
    public Result<List<RoiAnnuallyTotalVO>> roiAnnuallyTotalChart(@RequestBody GroupForm form) {
        List<RoiAnnuallyTotalVO> result = businessDataService.roiAnnuallyTotalChart(form);
        return Result.success(result);
    }

    /**
     * Curve chart
     */
    @ApiOperation("Curve chart")
    @ApiOperationSupport(order = 9)
    @PostMapping("/curve")
    public Result<List<CurveVO>> roiAnnuallyTotalChart(@RequestBody ProductNameForm form) {
        List<CurveVO> result = businessDataService.curveList(form);
        return Result.success(result);
    }

    /**
     * Impression Detail chart
     */
    @ApiOperation("Impression Detail chart")
    @ApiOperationSupport(order = 10)
    @PostMapping("/impression_detail")
    public Result<ImpressionDetailChartVO> impressionDetailChart(@RequestBody ProductNameForm form) {
        ImpressionDetailChartVO result = businessDataService.impressionDetailChart(form);
        return Result.success(result);
    }
}
