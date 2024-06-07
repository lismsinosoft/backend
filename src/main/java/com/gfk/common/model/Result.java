package com.gfk.common.model;

import com.gfk.common.enums.ErrorEnum;
import lombok.Data;

/**
 * @author wzl
 * @Date 2023/3/1
 * @description
 */
@Data
public class Result<T> {
    private int code;

    private boolean success;

    private String message;

    private T result;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("");
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("");
        result.setResult(t);
        return result;
    }

    public static <T> Result<T> success(String message, T t) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage(message);
        result.setResult(t);
        return result;
    }

    public static <T> Result<T> empty() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> failed(int code, String message) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> failed(ErrorEnum error) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(error.code);
        result.setMessage(error.message);
        return result;
    }

    public static <T> Result<T> forbidden() {
        Result<T> result = new Result<>();
        result.setMessage("暂无当前项目权限");
        result.setCode(403);
        result.setSuccess(false);
        return result;
    }
}
