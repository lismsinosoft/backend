package com.gfk.common.util.request;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.gfk.framework.shiro.ShiroUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class RequestUtils {

    /**
     * 客户请求来源IP
     *  配合nginx配置使用：proxy_set_header  x-client-ip  $remote_addr;
     */
    public static final String CLIENT_IP_REQUEST_HEADER = "x-client-ip";

    /**
     * 获取当前上下文中的请求
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 获取当前上下文中请求的IP地址
     * @return  IP地址
     */
    public static String currentRequestIP() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            String ip = request.getHeader(CLIENT_IP_REQUEST_HEADER);
            if (StrUtil.isBlank(ip)) {
                return ShiroUtils.getIp();
            }
            return NetUtil.getMultistageReverseProxyIp(ip);
        }
        return null;
    }

    /**
     * 获取当前上下文中的请求地址
     * @return  请求地址
     */
    public static String currentRequestUrl() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getRequestURI();
        }
        return null;
    }

    /**
     * 获取当前上下文中的请求方式
     * @return  请求地址
     */
    public static String currentRequestMethod() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getMethod();
        }
        return null;
    }
}
