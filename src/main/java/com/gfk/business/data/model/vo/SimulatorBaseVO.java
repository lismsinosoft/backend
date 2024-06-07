package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/10/17
 */
@Data
public class SimulatorBaseVO {
    private String type1;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendCurrent;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueCurrent;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiCurrent;
}
