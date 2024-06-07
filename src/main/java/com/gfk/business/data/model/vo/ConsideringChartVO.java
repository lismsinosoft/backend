package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
@ApiModel("Considering图表VO")
public class ConsideringChartVO {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("是否当年数据（当年数据样式为实心圆）")
    private Boolean isCurrent;

    @ApiModelProperty("roi")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roi;

    @ApiModelProperty("Sales Contribution")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesContribution;

    @ApiModelProperty("Spending（面积）")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spending;
}
