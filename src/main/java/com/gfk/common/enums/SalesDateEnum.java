package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2023/3/11
 */
public enum SalesDateEnum {
    WEEKLY(1, "周度数据"),
    MONTHLY(2, "月度数据");

    public final int code;
    public final String remark;

    SalesDateEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public static SalesDateEnum typeOf(Integer code) {
        if (null == code) {
            return null;
        }
        for (SalesDateEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
