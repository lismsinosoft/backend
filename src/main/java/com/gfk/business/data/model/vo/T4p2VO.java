package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.gfk.business.data.model.T4p2;
import com.gfk.common.enums.DynamicFieldEnum;
import com.gfk.common.util.CalcUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Performance Detail数据图表展示VO
 * T4P2
 *
 * @author wzl
 * @version 1.0 2023/7/26
 */
@Data
public class T4p2VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T4p2ChartData> chartData;

    /**
     * 年份字段对应
     */
    @ApiModelProperty("年份字段对应")
    private List<Integer> yearMap;

    /**
     * actOn对应
     */
    @ApiModelProperty("actOn对应")
    private List<String> actOnMap;

    @Data
    public static class T4p2ChartData {
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

        public void setYearValue(Integer year, String actOn, BigDecimal value) {
            this.yearFields.put(DynamicFieldEnum.VALUE.name + actOn + year, value);
        }

        public void addOneYearData(T4p2 entity) {
//            this.setName(entity.getType1() + "-" + entity.getType2());
            this.setName(entity.getType2());
            this.setYearValue(entity.getYear(), entity.getActOn(), CalcUtils.safeValue(entity.getRoi(), 1));
        }
    }
}
