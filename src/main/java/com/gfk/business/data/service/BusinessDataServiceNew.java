package com.gfk.business.data.service;

import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.TypeActForm;
import com.gfk.business.data.controller.form.TypeForm;
import com.gfk.business.data.controller.form.YearForm;
import com.gfk.business.data.model.T5p3;
import com.gfk.business.data.model.vo.*;

import java.util.List;

/**
 * SalesDate 服务层
 *
 * @author wzl
 * @version 1.0 2023/03/11
 */
public interface BusinessDataServiceNew {

    /**
     * 查询Spending图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T1p1VO t1p1Chart(BaseForm query);

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T1p2VO t1p2Chart(BaseForm query);

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T1p3VO t1p3Chart(BaseForm query);

    /**
     * 查询Investment Detail图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T2VO t2Chart(YearForm query);

    /**
     * 查询Considering图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    List<T3p1VO> t3p1(YearForm query);

    /**
     * 查询Performance 年度图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T4p1VO t4p1Chart(BaseForm query);

    /**
     * 查询Performance 详情图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T4p2VO t4p2Chart(BaseForm query);

    /**
     * 查询Performance 年度图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T4p3VO t4p3Chart(TypeForm query);

    /**
     * 查询Performance 详情图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T4p4VO t4p4Chart(TypeForm query);

    /**
     * 查询Product Group 图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    T5p1VO t5p1Chart(TypeActForm query);

    /**
     * 查询ROI年度对比图表数据
     *
     * @param query 查询条件
     * @return 两年的ROI总和数据对比
     */
    T5p2VO t5p2Chart(TypeActForm query);

    /**
     * 查询Curve表格数据
     *
     * @param query 查询条件
     * @return curve数据List
     */
    List<T5p3> t5p3Chart(TypeActForm query);

    /**
     * 查询Impression图表数据
     *
     * @param query 查询条件
     * @return Impression图表
     */
    T5p4VO t5p4Chart(TypeActForm query);

    List<String> getProduct(Long projectId);

    List<Integer> getT2p1Years(Long projectId);

    List<Integer> getT3p1Years(Long projectId);

    List<TreeVO> getT5Filter(Long projectId, String product);
}
