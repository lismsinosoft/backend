package com.gfk.common.enums;

import com.gfk.business.data.model.bo.SalesDateChartBO;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author wzl
 * @version 1.0 2023/3/12
 */
public enum SalesDateTotalStrategyEnum {

    UNIT("Unit", SalesDateChartBO::getUnit),
    VALUE("Value K USD", SalesDateChartBO::getValue),

    PRICE("Price USD", SalesDateChartBO::getPrice);

    public final String name;
    public final Function<SalesDateChartBO, BigDecimal> func;

    SalesDateTotalStrategyEnum(String name, Function<SalesDateChartBO, BigDecimal> func) {
        this.name = name;
        this.func = func;
    }


}
