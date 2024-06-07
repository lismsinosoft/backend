package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * spending数据图表数据VO
 *
 * @author wzl
 * @version 1.0 2023/7/16
 */
@Data
public class T1p1DataVO {
    /**
     * 平台名
     */
    @ApiModelProperty("平台名")
    private String name;

    /**
     * 前一年总和
     */
    @ApiModelProperty("前一年总和")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueFirstYear;

    /**
     * 后一年总和
     */
    @ApiModelProperty("后一年总和")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueLastYear;

    /**
     * 前一年占比
     */
    @ApiModelProperty("前一年占比")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal percentageFirstYear;

    /**
     * 后一年占比
     */
    @ApiModelProperty("后一年占比")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal percentageLastYear;

    /**
     * 增长率
     */
    @ApiModelProperty("增长率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal growth;

    /**
     * 第三年总和
     */
    @ApiModelProperty("第三年总和")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueExtraYear;

    /**
     * 第三年占比
     */
    @ApiModelProperty("第三年占比")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal percentageExtraYear;

    /**
     * 第三年增长率
     */
    @ApiModelProperty("第三年增长率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal growthExtra;
}
