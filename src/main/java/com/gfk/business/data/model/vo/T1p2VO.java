package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * sales数据图表展示VO（T1P2）
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class T1p2VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T1p2ChartVO> chartData;

    @Data
    public static class T1p2ChartVO {
        /**
         * 年份
         */
        @ApiModelProperty("年份")
        private String year;

        /**
         * 日期
         */
        @ApiModelProperty("日期")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date date;

        /**
         * Unit
         */
        @ApiModelProperty("Unit")
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal unit;

        /**
         * Price
         */
        @ApiModelProperty("Price")
        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal price;
    }
}
