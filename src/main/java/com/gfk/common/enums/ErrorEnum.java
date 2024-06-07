package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2023/3/1
 */
public enum ErrorEnum {
    SAMPLE(0, "示例错误"),
    IMPORT_FAIL(20001, "导入失败");

    public final int code;

    public final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
