package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * spending数据图表展示VO
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class SpendingChartVO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<SpendingDataVO> dataList;

    @ApiModelProperty("前一年总额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal firstTotal;

    @ApiModelProperty("后一年总额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal lastTotal;

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
