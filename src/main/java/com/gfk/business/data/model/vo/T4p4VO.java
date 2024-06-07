package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.gfk.business.data.model.T4p4;
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
 * T4P4
 *
 * @author wzl
 * @version 1.0 2023/7/26
 */
@Data
public class T4p4VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T4p4ChartData> chartData;

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

    /**
     * type2（前端回显用）
     */
    @ApiModelProperty("type2（前端回显用）")
    private String type2;

    @Data
    public static class T4p4ChartData {
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

        public void addOneYearData(T4p4 entity) {
            this.setName(entity.getType3());
            this.setYearValue(entity.getYear(), entity.getActOn(), CalcUtils.safeValue(entity.getRoi(), 1));
        }
    }
}
