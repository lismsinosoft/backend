package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ProductGroup表格数据记录VO
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("ProductGroup表格数据记录VO")
public class ProductGroupDataVO {
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 前一年总和
     */
    @ApiModelProperty("前一年数值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueFirstYear;

    /**
     * 后一年总和
     */
    @ApiModelProperty("后一年数值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueLastYear;

    /**
     * 增长率
     */
    @ApiModelProperty("增长率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal growth;
}
