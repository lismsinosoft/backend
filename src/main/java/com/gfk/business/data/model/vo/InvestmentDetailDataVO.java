package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Investment Detail数据图表数据VO
 *
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
@ApiModel("Investment Detail数据图表数据VO")
public class InvestmentDetailDataVO {
    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    private String product;

    /**
     * 分类1
     */
    @ApiModelProperty("分类1")
    private String type1;

    /**
     * 分类2(平台)
     */
    @ApiModelProperty("分类2(平台)")
    private String type2;

    /**
     * Sales Contribution 前一年数据
     */
    @ApiModelProperty("Sales Contribution 前一年数据")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesFirst;

    /**
     * Sales Contribution 后一年数据
     */
    @ApiModelProperty("Sales Contribution 后一年数据")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesLast;

    /**
     * Sales Contribution K USD前一年数据
     */
    @ApiModelProperty("Sales Contribution K USD前一年数据")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesUsdFirst;

    /**
     * Sales Contribution K USD后一年数据
     */
    @ApiModelProperty("Sales Contribution K USD后一年数据")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesUsdLast;

    /**
     * 增长率
     */
    @ApiModelProperty("增长率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal growth;

}
