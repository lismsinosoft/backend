package com.gfk.business.data.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Performance 详细图表VO
 *
 * @author wzl
 * @version 1.0 2023/5/20
 */
@Data
@ApiModel("Performance 详细图表VO")
public class PerformanceDetailChartVO {

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

    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<PerformanceDetailDataVO> dataList;
}
