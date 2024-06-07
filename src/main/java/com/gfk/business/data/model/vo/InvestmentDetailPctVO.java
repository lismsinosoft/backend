package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Investment Detail数据百分比数据VO
 *
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
@ApiModel("Investment Detail数据百分比数据VO")
public class InvestmentDetailPctVO {
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 前一年总和
     */
    @ApiModelProperty("实际值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal value;

}
