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
public class SimulatorListItemVO extends SimulatorBaseVO {
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendingShare;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendingSharePct;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueShare;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueSharePct;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiSharePct;
}
