package com.gfk.common.ga.engine.core;

import lombok.Data;

@Data
public class ValidationResult {
    private boolean isValid;
    private String message;

    private ValidationResult() {
    }

    public static ValidationResult ok() {
        ValidationResult result = new ValidationResult();
        result.isValid = true;
        result.message = null;
        return result;
    }

    public static ValidationResult fail(String msg) {
        ValidationResult result = new ValidationResult();
        result.isValid = false;
        result.message = msg;
        return result;
    }
}
