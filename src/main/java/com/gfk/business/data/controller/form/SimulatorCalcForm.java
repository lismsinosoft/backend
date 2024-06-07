package com.gfk.business.data.controller.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.vo.SimulatorBaseVO;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimulatorCalcForm extends SimulatorForm {

    private List<CalcItem> items;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CalcItem extends SimulatorBaseVO {
        @JsonSerialize(using = BigDecimalSerializer.class)
        @ApiModelProperty("变化率百分比数值")
        private BigDecimal change;
    }
}
