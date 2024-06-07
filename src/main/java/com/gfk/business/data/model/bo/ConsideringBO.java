package com.gfk.business.data.model.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/4/8
 */
@Data
public class ConsideringBO {
    private String name;

    private BigDecimal roiFirst;

    private BigDecimal roiLast;

    private BigDecimal salesFirst;

    private BigDecimal salesLast;

    private BigDecimal spendingFirst;

    private BigDecimal spendingLast;
}
