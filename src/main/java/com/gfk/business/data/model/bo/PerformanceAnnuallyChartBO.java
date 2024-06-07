package com.gfk.business.data.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Performance 年度图表数据BO
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Data
public class PerformanceAnnuallyChartBO {

    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;

    /**
     * Value
     */
    @ApiModelProperty("Value")
    private BigDecimal value;
}
