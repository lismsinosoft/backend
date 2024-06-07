package com.gfk.business.data.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ProductGroup表格VO
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("ProductGroup表格VO")
public class ProductGroupChartVO {

    /**
     * 产品
     */
    @ApiModelProperty("产品")
    private String product;

    /**
     * lGroup
     */
    @ApiModelProperty("lGroup")
    private String group;

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

    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<ProductGroupDataVO> dataList;
}
