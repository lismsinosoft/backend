package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Roi Annually Total VO
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("Roi 年度总和 Total VO")
@NoArgsConstructor
@AllArgsConstructor
public class RoiAnnuallyTotalVO {
    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private Integer year;

    /**
     * 数值
     */
    @ApiModelProperty("数值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal value;
}
