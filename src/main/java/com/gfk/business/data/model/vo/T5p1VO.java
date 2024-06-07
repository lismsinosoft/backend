package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.gfk.business.data.model.T5p1;
import com.gfk.common.enums.DynamicFieldEnum;
import com.gfk.common.util.CalcUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Product Group chart数据图表展示VO
 * T5P1
 *
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
public class T5p1VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T5p1ChartData> chartData;

    /**
     * 年份字段对应
     */
    @ApiModelProperty("年份字段对应")
    private List<Integer> yearMap;

    @Data
    public static class T5p1ChartData {
        /**
         * 平台名
         */
        @ApiModelProperty("平台名")
        private String name;

        private Integer sort;

        private Map<String, BigDecimal> yearFields = new HashMap<>();

        @JsonAnyGetter
        public Map<String, BigDecimal> getYearFields() {
            return this.yearFields;
        }

        public void setYearValue(Integer year, BigDecimal value) {
            this.yearFields.put(DynamicFieldEnum.VALUE.name + year, value);
        }

        public void setGrowthValue(Integer year, BigDecimal growth) {
            this.yearFields.put(DynamicFieldEnum.GROWTH.name + year, growth);
        }

        public void addOneYearData(T5p1 entity) {
            this.setName(entity.getName());
            this.setSort(entity.getSort());
            this.setYearValue(entity.getYear(), entity.getValue());
            this.setGrowthValue(entity.getYear(), CalcUtils.getHundredNumber(entity.getGrowth(), 1));
        }
    }
}
