package com.gfk.business.data.model.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class SpendingChartBO {

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    private String platform;

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;

    /**
     * Value
     */
    @ApiModelProperty("Value")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal value;
}
