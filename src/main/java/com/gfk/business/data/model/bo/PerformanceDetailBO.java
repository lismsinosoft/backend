package com.gfk.business.data.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/5/20
 */
@Data
public class PerformanceDetailBO {

    /**
     * 分类(平台)
     */
    @ApiModelProperty("分类(平台)")
    private String name;

    /**
     * 影响方式
     */
    private String effect;

    /**
     * ROI 数据
     */
    private BigDecimal roi;

    /**
     * ROI 后一年数据
     */
    private BigDecimal roiLast;

}
