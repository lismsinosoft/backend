package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Performance 年度图表VO
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Data
@ApiModel("Performance 年度图表VO")
public class PerformanceAnnuallyChartVO {

    /**
     * 图表list
     */
    @ApiModelProperty("图表list")
    private List<PerformanceGroupVO> list;

    /**
     * 总额增长率
     */
    @ApiModelProperty("总额增长率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalGrowth;

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
