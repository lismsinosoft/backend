package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.gfk.business.data.model.T5p2;
import com.gfk.common.enums.DynamicFieldEnum;
import com.gfk.common.util.CalcUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ROI Annually Total chart数据图表展示VO
 * T5P1
 *
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
public class T5p2VO {
    /**
     * 图表数据
     */
    @ApiModelProperty("图表数据")
    private List<T5p2ChartData> chartData;

    /**
     * 年份字段对应
     */
    @ApiModelProperty("年份字段对应")
    private List<Integer> yearMap;

    @Data
    public static class T5p2ChartData {

        private Map<String, BigDecimal> yearFields = new HashMap<>();

        @JsonAnyGetter
        public Map<String, BigDecimal> getYearFields() {
            return this.yearFields;
        }

        public void setYearValue(Integer year, BigDecimal value) {
            this.yearFields.put(DynamicFieldEnum.VALUE.name + year, value);
        }

        public void addOneYearData(T5p2 entity) {
            this.setYearValue(entity.getYear(), CalcUtils.safeValue(entity.getValue(), 1));
        }
    }
}
