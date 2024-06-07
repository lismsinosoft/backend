package com.gfk.common.context;/**
 * @author wzl
 * @Date 2023/2/26
 * @description
 */

import com.gfk.common.exception.BusinessException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下文
 *
 * @author wzl
 * @version 1.0 2023/2/26
 */
public class Context {
    private final ConcurrentHashMap<String, String> container = new ConcurrentHashMap<>();

    public String get(String key) {
        return container.get(key);
    }

    public String get(PreferenceName key) {
        return this.get(key.name());
    }

    public void adminValid() {
        String isAdmin = this.get(PreferenceName.isAdmin);
        if (!Boolean.parseBoolean(isAdmin)) {
            throw new BusinessException("非法请求");
        }
    }

    public String getUserId() {
        return this.get(PreferenceName.userId);
    }

    public String getUserName() {
        return this.get(PreferenceName.userName);
    }

    public void set(String key, String value) {
        container.put(key, value);
    }

    public enum PreferenceName {
        userId,
        userName,
        language,
        isAdmin
    }
}
