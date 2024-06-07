package com.gfk.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 维护页配置
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ConfigurationProperties(prefix = "unavailable")
@Component
public class UnavailableProperties {
    private static String begin;
    private static String end;

    private static Boolean enable;
    public static String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        UnavailableProperties.begin = begin;
    }

    public static String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        UnavailableProperties.end = end;
    }

    public static Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        UnavailableProperties.enable = enable;
    }
}
