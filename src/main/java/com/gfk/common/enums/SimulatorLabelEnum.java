package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2023/10/17
 */
public enum SimulatorLabelEnum {
    CURRENT("Current"),
    MINIMAL("Minimal"),

    OPTIMAL("Optimal");

    public final String label;

    SimulatorLabelEnum(String label) {
        this.label = label;
    }
}
