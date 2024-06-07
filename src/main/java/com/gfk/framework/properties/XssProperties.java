package com.gfk.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * xss过滤
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Component
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /** 是否启用xss过滤*/
    private static String enabled;

    /** 需要处理的url*/
    private static String excludes;

    /** 不进行处理的url*/
    private static String urlPatterns;

    public static String getEnabled() {
        return enabled;
    }

    public static String getExcludes() {
        return excludes;
    }

    public static String getUrlPatterns() {
        return urlPatterns;
    }

    public void setEnabled(String enabled) {
        XssProperties.enabled = enabled;
    }

    public void setExcludes(String excludes) {
        XssProperties.excludes = excludes;
    }

    public void setUrlPatterns(String urlPatterns) {
        XssProperties.urlPatterns = urlPatterns;
    }
}
