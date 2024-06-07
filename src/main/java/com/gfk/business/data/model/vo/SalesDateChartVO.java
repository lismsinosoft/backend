package com.gfk.business.data.model.vo;

import com.gfk.business.data.model.bo.SalesDateChartBO;
import com.gfk.business.data.model.bo.SalesDateTotalBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * sales数据图表展示VO
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class SalesDateChartVO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<SalesDateChartBO> chartData;

    /**
     * 总和
     */
    @ApiModelProperty("总和")
    private List<SalesDateTotalBO> totalData;

    /**
     * 前一年年份
     */
    @ApiModelProperty("前一年年份")
    private Integer yearFirst;

    /**
     * 后一年年份
     */
    @ApiModelProperty("后一年年份")
    private Integer yearLast;
}
