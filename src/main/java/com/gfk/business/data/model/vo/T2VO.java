package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.T2p1;
import com.gfk.business.data.model.T2p2;
import com.gfk.common.enums.DynamicFieldEnum;
import com.gfk.common.util.CalcUtils;
import com.gfk.framework.jackson.BigDecimalFor3ScaleSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Investment Detail数据图表展示VO
 *
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
@ApiModel("Investment Detail数据图表展示VO")
public class T2VO {
    private final Map<String, List<T2P1DataVO>> dynamicT2p1Data = new HashMap<>();
    /**
     * 表格数据
     */
    @ApiModelProperty("表格数据")
    private List<T2P2DataVO> dataList;
    /**
     * 年份字段对应
     */
    @ApiModelProperty("年份字段对应")
    private List<Integer> yearMap;

    @JsonAnyGetter
    public Map<String, List<T2P1DataVO>> getDynamicT2p1Data() {
        return this.dynamicT2p1Data;
    }

    public void setDynamicT2p1Data(Integer year, List<T2P1DataVO> yearData) {
        this.dynamicT2p1Data.put(DynamicFieldEnum.PERCENT_LIST.name + year, yearData);
    }

    @Data
    public static class T2P1DataVO {
        /**
         * 名称
         */
        @ApiModelProperty("名称")
        private String name;

        /**
         * 前一年总和
         */
        @ApiModelProperty("实际值")
        @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
        private BigDecimal value;

        /**
         * 颜色
         */
        @ApiModelProperty("颜色")
        private String color;

        public static T2P1DataVO of(T2p1 entity) {
            T2P1DataVO vo = new T2P1DataVO();
            vo.setName(entity.getLabel());
            vo.setValue(entity.getValue());
            return vo;
        }
    }

    @Data
    public static class T2P2DataVO {
        /**
         * 产品名称
         */
        @ApiModelProperty("产品名称")
        private String product;

        /**
         * 分类1
         */
        @ApiModelProperty("分类1")
        private String type1;

        /**
         * 分类2(平台)
         */
        @ApiModelProperty("分类2(平台)")
        private String type2;

        private Map<String, BigDecimal> dynamicFields = new HashMap<>();

        @JsonAnyGetter
        public Map<String, BigDecimal> getDynamicFields() {
            return this.dynamicFields;
        }

        public void setYearSales(Integer year, BigDecimal percentSales) {
            this.dynamicFields.put(DynamicFieldEnum.SALES.name + year, percentSales);
        }

        public void setYearGrowth(Integer year, BigDecimal growth) {
            this.dynamicFields.put(DynamicFieldEnum.GROWTH.name + year, growth);
        }

        public void setYearSalesUsd(Integer year, BigDecimal value) {
            this.dynamicFields.put(DynamicFieldEnum.SALES_USD.name + year, value);
        }

        public void addOneYearData(T2p2 entity) {
            this.setProduct(entity.getSeries());
            this.setType1(entity.getType1());
            this.setType2(entity.getType2());
            this.setYearSales(entity.getYear(), CalcUtils.getHundredNumber(entity.getPercent(), 3));
            this.setYearGrowth(entity.getYear(), CalcUtils.getHundredNumber(entity.getGrowth(), 3));
            this.setYearSalesUsd(entity.getYear(), CalcUtils.safeValue(entity.getValue(), 3));
        }
    }
}
