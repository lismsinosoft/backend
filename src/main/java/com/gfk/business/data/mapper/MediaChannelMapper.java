package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.mapper.query.ConsideringQuery;
import com.gfk.business.data.mapper.query.InvestmentDetailQuery;
import com.gfk.business.data.mapper.query.PerformanceDetailQuery;
import com.gfk.business.data.mapper.query.ProductGroupQuery;
import com.gfk.business.data.model.MediaChannel;
import com.gfk.business.data.model.bo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SalesDate 数据层
 *
 * @author wzl
 * @version 1.0 2023/03/11
 */
public interface MediaChannelMapper extends BaseMapper<MediaChannel> {

    /**
     * 查询Considering图表原始数据
     *
     * @param query 查询条件
     * @return 图表数据BO
     */
    List<ConsideringBO> selectConsideringData(@Param("query") ConsideringQuery query);

    /**
     * 查询Investment Detail图表原始数据
     *
     * @param query 查询条件
     * @return 图表数据BO
     */
    List<InvestmentDetailBO> selectInvestmentDetailData(@Param("query") InvestmentDetailQuery query);

    /**
     * 查询Investment Detail图表原始数据
     *
     * @param query 查询条件
     * @return 图表数据BO
     */
    List<PerformanceDetailBO> selectPerformanceDetail(@Param("query") PerformanceDetailQuery query);

    /**
     * 查询ProductGroup数据
     *
     * @param query 查询条件
     * @return Spending和Sales contribution两年数据
     */
    ProductGroupBO selectProductGroup(@Param("query") ProductGroupQuery query);

    TwoYearsValue selectRoiAnnuallyTotal(@Param("query") ProductGroupQuery query);

    /**
     * 查询Product及Group关联数据
     *
     * @param projectId 项目ID
     * @return 产品包含的group
     */
    List<ProductGroupFilterBO> selectProductGroupFilter(@Param("projectId") Long projectId);

    List<String> selectAllProducts(@Param("projectId") Integer projectId);


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
    void truncateMediaChannel();
}