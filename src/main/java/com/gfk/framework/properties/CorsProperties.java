package com.gfk.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ConfigurationProperties(prefix = "cors")
@Component
public class CorsProperties {
    private static List<String> allowed;

    public static List<String> getAllowed() {
        return allowed;
    }

    public void setAllowed(List<String> allowed) {
        CorsProperties.allowed = allowed;
    }
}
