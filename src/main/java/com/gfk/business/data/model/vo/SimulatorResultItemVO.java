package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/10/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimulatorResultItemVO extends SimulatorListItemVO {
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendingChange;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueChange;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiChange;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiChangePct;
}
