package com.gfk.business.data.service;

import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.GroupForm;
import com.gfk.business.data.controller.form.ProductNameForm;
import com.gfk.business.data.model.vo.*;

import java.util.List;

/**
 * SalesDate 服务层
 *
 * @author wzl
 * @version 1.0 2023/03/11
 */
public interface BusinessDataService {

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    SalesDateChartVO salesDateChart(BaseForm query);

    /**
     * 查询Spending图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    SpendingChartVO spendingChart(BaseForm query);

    /**
     * 查询Considering图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    List<ConsideringChartVO> consideringChart(BaseForm query);


    /**
     * 查询Investment Detail图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    InvestmentDetailChartVO investmentDetailChart(BaseForm query);

    /**
     * 查询Performance 年度图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    PerformanceAnnuallyChartVO performanceAnnuallyChart(BaseForm query);

    /**
     * 查询Performance 详情图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    PerformanceDetailChartVO performanceDetailChart(BaseForm query);


    /**
     * 查询Product Group 图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    ProductGroupChartVO productGroupChart(GroupForm query);

    /**
     * 查询ROI年度对比图表数据
     *
     * @param query 查询条件
     * @return 两年的ROI总和数据对比
     */
    List<RoiAnnuallyTotalVO> roiAnnuallyTotalChart(GroupForm query);

    /**
     * 查询Curve表格数据
     *
     * @param query 查询条件
     * @return curve数据List
     */
    List<CurveVO> curveList(ProductNameForm query);

    /**
     * 查询Impression图表数据
     *
     * @param query 查询条件
     * @return Impression图表
     */
    ImpressionDetailChartVO impressionDetailChart(ProductNameForm query);

    /**
     * SalesDate导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformSalesDate(String taskId);

    /**
     * Media导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformMedia(String taskId);

    /**
     * Media Channel导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformMediaChannel(String taskId);

    /**
     * HL导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     */
    void transformHlAndGroupRelation(String taskId);

    /**
     * curve导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformCurve(String taskId);
}
