package com.gfk.business.data.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class SalesDateChartBO {

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * Unit
     */
    @ApiModelProperty("Unit")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unit;

    /**
     * Value
     */
    @ApiModelProperty("Value")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal value;

    /**
     * Price
     */
    @ApiModelProperty("Price")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal price;
}
