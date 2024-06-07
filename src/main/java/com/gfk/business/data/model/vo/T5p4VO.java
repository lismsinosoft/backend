package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.common.enums.T5p4FieldsEnum;
import com.gfk.common.util.CalcUtils;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品Impression图表(T5P4)
 *
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
@ApiModel("产品Impression图表(T5P4)")
public class T5p4VO {
    /**
     * Chart title
     */
    @ApiModelProperty("Chart title")
    private String title;

    /**
     * 柱状图动态名称
     */
    @ApiModelProperty("柱状图动态名称")
    private List<DynamicFields> dynamicField;
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
    private List<T5p4DataVO> dataList;

    @Data
    @ApiModel("产品Impression图表数据")
    public static class T5p4DataVO {
        private final Map<String, BigDecimal> dynamicT2p1Data = new HashMap<>();
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

        @JsonSerialize(using = BigDecimalSerializer.class)
        private BigDecimal grp;

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

        @JsonAnyGetter
        public Map<String, BigDecimal> getDynamicT2p1Data() {
            return this.dynamicT2p1Data;
        }

        public void setDynamicT2p1Data(T5p4FieldsEnum fieldsEnum, BigDecimal value) {
            this.dynamicT2p1Data.put(fieldsEnum.fieldName, CalcUtils.safeValue(value, 1));
        }
    }

    @Data
    @ApiModel("动态字段类型")
    public static class DynamicFields {
        private String fieldName;
        private String displayName;
        @ApiModelProperty("字段展示形状 1-折线 2-柱状")
        private int type;
        private String color;

        public DynamicFields(T5p4FieldsEnum fieldsEnum) {
            this.fieldName = fieldsEnum.fieldName;
            this.displayName = fieldsEnum.displayName;
            this.type = fieldsEnum.type;
            this.color = fieldsEnum.color;
        }
    }
}
