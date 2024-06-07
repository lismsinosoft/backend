package com.gfk.business.data.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
public class TwoYearsValue {

    private BigDecimal firstYear;

    private BigDecimal lastYear;

    public static TwoYearsValue empty() {
        TwoYearsValue impBO = new TwoYearsValue();
        impBO.setFirstYear(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        impBO.setLastYear(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_DOWN));
        return impBO;
    }
}
