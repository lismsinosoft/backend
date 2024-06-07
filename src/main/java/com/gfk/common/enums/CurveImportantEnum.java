package com.gfk.common.enums;

import java.util.Objects;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
public enum CurveImportantEnum {
    MAX_MARGIN("max_margin", "Max Margin"),
    MAX_ROI("max_roi", "Max ROI"),
    SATURATION("saturation", "Saturation"),
    X2021("X2021", "MAT 2021"),
    X2022("X2022", "MAT 2022");
    public final String name;
    public final String displayName;

    CurveImportantEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public static String getDisplayName(String labelName) {
        for (CurveImportantEnum value : values()) {
            if (Objects.equals(value.name, labelName)) {
                return value.displayName;
            }
        }
        return labelName;
    }
}
