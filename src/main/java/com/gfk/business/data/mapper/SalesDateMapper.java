package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.mapper.query.SalesDateQuery;
import com.gfk.business.data.model.SalesDate;
import com.gfk.business.data.model.bo.SalesDateChartBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SalesDate 数据层
 *
 * @author wzl
 * @date 2023/03/11
 */
public interface SalesDateMapper extends BaseMapper<SalesDate> {
    /**
     * 查询当前数据年份
     *
     * @return 年份(yyyy)
     */
    String selectCurrentYear();

    /**
     * 查询SalesDate图表数据
     *
     * @return 图表数据BO
     */
    List<SalesDateChartBO> selectChartData(@Param("query") SalesDateQuery query);

    /**
     * 导入中间数据转换业务数据
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformImport(@Param("taskId") String taskId);

    void truncateSalesDate();

    /**
     * SalesDate筛选条件
     *
     * @param query 查询条件
     * @return product筛选条件
     */
    List<String> filter(@Param("query") SalesDateQuery query);
}