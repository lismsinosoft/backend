package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.Curve;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Curve数据
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("Curve数据")
public class CurveVO {
    /**
     * Curve表格X值
     */
    @ApiModelProperty("Curve表格X值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal x;

    /**
     * Curve表格Y值
     */
    @ApiModelProperty("Curve表格Y值")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal y;

    /**
     * 特殊点label 非特殊点为空
     */
    @ApiModelProperty("特殊点label 非特殊点为空")
    private String label;

    public static CurveVO of(Curve curve) {
        CurveVO vo = new CurveVO();
        vo.setX(curve.getX());
        vo.setY(curve.getY());
        if (null != curve.getIsImportant() && curve.getIsImportant()) {
            vo.setLabel(curve.getImportantLabel());
        }
        return vo;
    }
}
