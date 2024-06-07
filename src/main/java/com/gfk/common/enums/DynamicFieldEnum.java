package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2023/7/18
 */
public enum DynamicFieldEnum {
    VALUE("value"),
    GROWTH("growth"),
    PERCENT("percent"),
    PERCENT_LIST("pctList"),
    SALES("sales"),
    SALES_USD("salesUsd");

    public final String name;

    DynamicFieldEnum(String name) {
        this.name = name;
    }
}
