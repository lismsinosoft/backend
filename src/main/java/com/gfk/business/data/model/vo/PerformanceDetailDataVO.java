package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Performance 详细图表数据VO
 *
 * @author wzl
 * @version 1.0 2023/5/26
 */
@Data
@ApiModel("Performance 详细图表数据VO")
public class PerformanceDetailDataVO {

    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;

    /**
     *
     */
    @ApiModelProperty("level")
    @JsonIgnore
    private Integer level;

    /**
     * 前一年ROI
     */
    @ApiModelProperty("前一年ROI")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiFirst;

    /**
     * 前一年ROI HALO
     */
    @ApiModelProperty("前一年ROI HALO")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiHaloFirst;

    /**
     * 后一年ROI
     */
    @ApiModelProperty("后一年ROI")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiLast;

    /**
     * 后一年ROI HALO
     */
    @ApiModelProperty("后一年ROI HALO")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiHaloLast;

    public PerformanceDetailDataVO() {
    }

    public static PerformanceDetailDataVO level1InstanceOfYear(Integer year) {
        PerformanceDetailDataVO instance = new PerformanceDetailDataVO();
        instance.setLevel(1);
        return instance;
    }
}
