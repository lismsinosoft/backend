package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.framework.jackson.BigDecimalSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/10/17
 */
@Data
public class SimulatorListVO {
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendingTotal;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueTotal;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiTotal;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiTotalPct;


    private List<SimulatorListItemVO> items;
}
