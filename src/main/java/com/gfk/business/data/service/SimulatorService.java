package com.gfk.business.data.service;

import com.gfk.business.data.controller.form.OptimizerForm;
import com.gfk.business.data.controller.form.SimulatorCalcForm;
import com.gfk.business.data.controller.form.SimulatorForm;
import com.gfk.business.data.model.vo.*;

import java.util.List;

/**
 * Simulator 服务层
 *
 * @author wzl
 * @version 1.0 2023/08/29
 */
public interface SimulatorService {

    /**
     * 获取timeframe筛选条件
     *
     * @param projectId 项目id
     * @param product   产品线
     * @return 筛选条件树形结构
     */
    List<TreeVO> getTimeframe(Long projectId, String product);

    /**
     * Simulator 曲线图
     *
     * @param query 查询条件
     * @return Simulator曲线图数据
     */
    SimulatorT1p1VO getT1p1(SimulatorForm query);

    /**
     * Simulator 曲线表格
     *
     * @param query 查询条件
     * @return Simulator曲线表格数据
     */
    List<SimulatorT1p2VO> getT1p2(SimulatorForm query);

    /**
     * Simulator 详情表格表格
     *
     * @param query 查询条件
     * @return Simulator详情表格数据
     */
    List<SimulatorT1p3VO> getT1p3(SimulatorForm query);


    /**
     * 查询Simulator List
     *
     * @param query 查询条件
     * @return simulator表格数据
     */
    SimulatorListVO getList(SimulatorForm query);

    /**
     * Simulator 计算核心方法
     *
     * @param query 计算参数
     * @return simulator计算表格数据
     */
    SimulatorResultVO simulate(SimulatorCalcForm query);

    SimulatorResultVO optimization(OptimizerForm query);


}
