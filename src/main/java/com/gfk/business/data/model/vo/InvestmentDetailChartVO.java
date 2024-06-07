package com.gfk.business.data.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Investment Detail数据图表展示VO
 *
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
@ApiModel("Investment Detail数据图表展示VO")
public class InvestmentDetailChartVO {
    /**
     * 表格数据
     */
    @ApiModelProperty("表格数据")
    private List<InvestmentDetailDataVO> dataList;

    /**
     * 占比数据
     */
    @ApiModelProperty("去年占比数据")
    private List<InvestmentDetailPctVO> firstPctList;

    /**
     * 占比数据
     */
    @ApiModelProperty("今年占比数据")
    private List<InvestmentDetailPctVO> lastPctList;

    /**
     * 前一年年份
     */
    @ApiModelProperty("前一年年份")
    private Integer yearFirst = 2021;

    /**
     * 后一年年份
     */
    @ApiModelProperty("后一年年份")
    private Integer yearLast = 2022;
}
