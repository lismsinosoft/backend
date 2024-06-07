package com.gfk.business.data.controller;


import com.gfk.business.data.controller.form.OptimizerForm;
import com.gfk.business.data.controller.form.SimulatorCalcForm;
import com.gfk.business.data.controller.form.SimulatorForm;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.SimulatorService;
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
 * Simulator Controller
 *
 * @author wzl
 * @version 1.0  2023/08/29
 */
@Slf4j
@Api(tags = "Simulator")
@RestController
@RequestMapping("/simulator")
@RequiredArgsConstructor
public class SimulatorController {

    private final SimulatorService simulatorService;

    private final UserProjectMappingService userProjectMappingService;

    /**
     * timeframe筛选条件查询
     */
    @ApiOperation("timeframe筛选条件查询")
    @ApiOperationSupport(order = 0)
    @GetMapping("/timeframe")
    public Result<List<TreeVO>> getTimeframeTree(@RequestParam Long projectId, @RequestParam(value = "product", required = false) String product) {
        List<TreeVO> result = simulatorService.getTimeframe(projectId, product);
        return Result.success(result);
    }

    /**
     * Simulator T1p1数据
     */
    @ApiOperation("Simulator T1p1")
    @ApiOperationSupport(order = 1)
    @PostMapping("/t1p1")
    public Result<SimulatorT1p1VO> t1p1(@RequestBody SimulatorForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        SimulatorT1p1VO result = simulatorService.getT1p1(query);
        return Result.success(result);
    }

    /**
     * Simulator T1p2数据
     */
    @ApiOperation("Simulator T1p2")
    @ApiOperationSupport(order = 2)
    @PostMapping("/t1p2")
    public Result<List<SimulatorT1p2VO>> t1p2(@RequestBody SimulatorForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        List<SimulatorT1p2VO> result = simulatorService.getT1p2(query);
        return Result.success(result);
    }

    /**
     * Simulator T1p3数据
     */
    @ApiOperation("Simulator T1p3")
    @ApiOperationSupport(order = 3)
    @PostMapping("/t1p3")
    public Result<List<SimulatorT1p3VO>> t1p3(@RequestBody SimulatorForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        List<SimulatorT1p3VO> result = simulatorService.getT1p3(query);
        return Result.success(result);
    }

    /**
     * Simulator List数据
     */
    @ApiOperation("Simulator List")
    @ApiOperationSupport(order = 4)
    @PostMapping("/list")
    public Result<SimulatorListVO> list(@RequestBody SimulatorForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        SimulatorListVO result = simulatorService.getList(query);
        return Result.success(result);
    }

    /**
     * Simulator Optimization数据
     */
    @ApiOperation("Simulator Simulate")
    @ApiOperationSupport(order = 5)
    @PostMapping("/simulate")
    public Result<SimulatorResultVO> simulate(@RequestBody SimulatorCalcForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        SimulatorResultVO result = simulatorService.simulate(query);
        return Result.success(result);
    }

    /**
     * Simulator Optimization数据
     */
    @ApiOperation("Simulator Optimization")
    @ApiOperationSupport(order = 6)
    @PostMapping("/optimization")
    public Result<SimulatorResultVO> optimization(@RequestBody OptimizerForm query) {
        if (!userProjectMappingService.hasProject(ContextHolder.currentContext().getUserId(), query.getProjectId())) {
            return Result.forbidden();
        }
        SimulatorResultVO result = simulatorService.optimization(query);
        return Result.success(result);
    }
}
