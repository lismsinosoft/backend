package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.bo.ImpressionBO;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品Impression图表数据
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("产品Impression图表数据")
public class ImpressionDetailDataVO {
    /**
     * 日期
     */
    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date date;

    /**
     * Impression
     */
    @ApiModelProperty("Impression")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal impression;

    /**
     *
     */
    @ApiModelProperty("Ad-stock")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal adStock;

    /**
     * max margin
     */
    @ApiModelProperty("max margin")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal maxMargin;

    /**
     * max roi
     */
    @ApiModelProperty("max roi")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal maxRoi;

    public static ImpressionDetailDataVO of(ImpressionBO bo) {
        ImpressionDetailDataVO vo = new ImpressionDetailDataVO();
        vo.setDate(bo.getDate());
        vo.setImpression(bo.getValue());
        return vo;
    }
}
