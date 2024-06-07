package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.gfk.business.data.model.T4p1;
import com.gfk.common.enums.DynamicFieldEnum;
import com.gfk.common.util.CalcUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Performance Annually数据图表展示VO
 * T4P1
 *
 * @author wzl
 * @version 1.0 2023/7/26
 */
@Data
public class T4p1VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T4p1ChartData> chartData;

    /**
     * 年份字段对应
     */
    @ApiModelProperty("年份字段对应")
    private List<Integer> yearMap;

    @ApiModelProperty("次级筛选下拉框数据")
    private List<String> type2;

    @Data
    public static class T4p1ChartData {
        /**
         * 平台名
         */
        @ApiModelProperty("平台名")
        private String name;

        private Map<String, BigDecimal> yearFields = new HashMap<>();

        @JsonAnyGetter
        public Map<String, BigDecimal> getYearFields() {
            return this.yearFields;
        }

        public void setYearValue(Integer year, BigDecimal value) {
            this.yearFields.put(DynamicFieldEnum.VALUE.name + year, value);
        }

        public void setYearGrowth(Integer year, BigDecimal growth) {
            this.yearFields.put(DynamicFieldEnum.GROWTH.name + year, growth);
        }

        public void addOneYearData(T4p1 entity) {
            this.setName(entity.getType2());
            this.setYearValue(entity.getYear(), CalcUtils.safeValue(entity.getValue(), 1));
            this.setYearGrowth(entity.getYear(), CalcUtils.getHundredNumber(entity.getGrowth(), 0));
        }
    }
}
