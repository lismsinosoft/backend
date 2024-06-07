package com.gfk.business.data.controller.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.vo.SimulatorBaseVO;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.util.CalcUtils;
import com.gfk.framework.jackson.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OptimizerForm extends BaseForm {
    private String frame1;
    private String frame2;
    private List<TypeLimit> typeLimits;
    @ApiModelProperty("目标值，可为空")
    private Long target;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TypeLimit extends SimulatorBaseVO {
        @JsonSerialize(using = BigDecimalSerializer.class)
        @ApiModelProperty("变化率最小值")
        private BigDecimal changeMin;

        @JsonSerialize(using = BigDecimalSerializer.class)
        @ApiModelProperty("变化率最大值")
        private BigDecimal changeMax;

        @JsonIgnore
        public BigDecimal getMin() {
            if (null == changeMin) {
                return null;
            }
            if (BigDecimal.ZERO.compareTo(changeMin) < 0) {
                throw new BusinessException(this.getType1() + "变化率最小值大于零");
            }
            if (null == this.getSpendCurrent()) {
                throw new BusinessException(this.getType1() + "变动基数为空");
            }
            return new BigDecimal("1.00").add(changeMin.divide(CalcUtils.PERCENT, 2, RoundingMode.HALF_UP)).multiply(this.getSpendCurrent());
        }

        @JsonIgnore
        public BigDecimal getMax() {
            if (null == changeMax) {
                return null;
            }
            if (BigDecimal.ZERO.compareTo(changeMax) > 0) {
                throw new BusinessException(this.getType1() + "变化率最大值小于零");
            }
            if (null == this.getSpendCurrent()) {
                throw new BusinessException(this.getType1() + "变动基数为空");
            }
            return new BigDecimal("1.00").add(changeMax.divide(CalcUtils.PERCENT, 2, RoundingMode.HALF_UP)).multiply(this.getSpendCurrent());
        }
    }
}
