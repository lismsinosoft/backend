package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.mapper.query.PerformanceAnnuallyQuery;
import com.gfk.business.data.mapper.query.ProductGroupQuery;
import com.gfk.business.data.mapper.query.SpendingQuery;
import com.gfk.business.data.model.Media;
import com.gfk.business.data.model.bo.ImpressionBO;
import com.gfk.business.data.model.bo.PerformanceAnnuallyChartBO;
import com.gfk.business.data.model.bo.SpendingChartBO;
import com.gfk.business.data.model.bo.TwoYearsValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SalesDate 数据层
 *
 * @author wzl
 * @version 1.0 2023/03/11
 */
public interface MediaMapper extends BaseMapper<Media> {
    /**
     * 查询当前数据年份
     *
     * @return 年份(yyyy)
     */
    String selectCurrentYear();

    /**
     * 查询Spending图表数据
     *
     * @return 图表数据BO
     */
    List<SpendingChartBO> selectSpendingChartData(@Param("query") SpendingQuery query);

    /**
     * 查询Performance Annually图表数据
     *
     * @return 图表数据BO
     */
    List<PerformanceAnnuallyChartBO> selectPerformanceAnnuallyData(@Param("query") PerformanceAnnuallyQuery query);

    /**
     * 查询ProductGroup Impress数据
     *
     * @param query 查询条件
     * @return 前后两年的Impress数据
     */
    TwoYearsValue selectProductGroupImp(@Param("query") ProductGroupQuery query);

    List<ImpressionBO> selectImpression(@Param("projectId") Long projectId, @Param("name") String name);

    /**
     * 导入中间数据转换业务数据
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformImport(@Param("taskId") String taskId);

    /**
     * 清空主表数据
     */
    void truncateMedia();
}