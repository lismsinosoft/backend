package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Performance group数据记录
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Data
@ApiModel("Performance group数据记录")
public class PerformanceGroupVO {
    /**
     * group名称
     */
    @ApiModelProperty("group名称")
    private String name;

    /**
     * 前一年值
     */
    @ApiModelProperty("前一年值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueFirstYear;

    /**
     * 后一年值
     */
    @ApiModelProperty("后一年值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal valueLastYear;

    /**
     * 增长率
     */
    @ApiModelProperty("增长率")
    private BigDecimal growth;
}
