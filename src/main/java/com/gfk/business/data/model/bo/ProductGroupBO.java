package com.gfk.business.data.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
public class ProductGroupBO {
    private String product;

    private String lGroup;

    private BigDecimal spendingFirst;

    private BigDecimal spendingLast;

    private BigDecimal salesContributionFirst;

    private BigDecimal salesContributionLast;

    public static ProductGroupBO empty() {
        ProductGroupBO groupBO = new ProductGroupBO();
        groupBO.setSpendingLast(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        groupBO.setSpendingFirst(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        groupBO.setSalesContributionLast(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        groupBO.setSalesContributionFirst(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        return groupBO;
    }
}
