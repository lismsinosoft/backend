package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品Impression图表
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("产品Impression图表")
public class ImpressionDetailChartVO {
    /**
     * Chart title
     */
    @ApiModelProperty("Chart title")
    private String title;

    /**
     * MaxMargin偏移量
     */
    @ApiModelProperty("MaxMargin偏移量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal maxMarginOffset;

    /**
     * MaxRoi偏移量
     */
    @ApiModelProperty("MaxRoi偏移量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal maxRoiOffset;

    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<ImpressionDetailDataVO> dataList;
}
