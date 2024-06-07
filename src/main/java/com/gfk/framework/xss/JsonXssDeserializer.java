package com.gfk.framework.xss;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gfk.common.util.request.RequestUtils;
import com.gfk.framework.properties.XssProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSON XSS 处理
 * https://blog.csdn.net/qq_36521507/article/details/105770952
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
class JsonXssDeserializer extends JsonDeserializer<String> {

    /** xss key */
    private static final String DO_XSS = "doXss";

    /** 排除链接 */
    public List<String> excludes = new ArrayList<>();

    /** 包括链接 */
    public List<String> includes = new ArrayList<>();

    /** xss过滤开关 */
    public boolean enabled = false;

    public JsonXssDeserializer() {
        super();
        init();
    }

    /**
     * 初始化参数
     */
    public void init() {
        if (StrUtil.isNotEmpty(XssProperties.getEnabled())) {
            enabled = Boolean.valueOf(XssProperties.getEnabled());
            // 添加包括链接
            String tempIncludes = XssProperties.getUrlPatterns();
            if (StrUtil.isNotEmpty(tempIncludes)) {
                String[] split = tempIncludes.split(",");
                for (int i = 0; split != null && i < split.length; i++) {
                    includes.add(split[i]);
                }
            }
            // 添加排除链接
            String tempExcludes = XssProperties.getExcludes();
            if (StrUtil.isNotEmpty(tempExcludes)) {
                String[] split = tempExcludes.split(",");
                for (int i = 0; split != null && i < split.length; i++) {
                    excludes.add(split[i]);
                }
            }
        }
    }

    /**
     * json string value解析
     * @param jsonParser
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        // 获取上下文标记，避免同一个类重复判断是否xss
        Boolean doXss = (Boolean) ctxt.getAttribute(DO_XSS);
        if (doXss == null) {
            if (noXss(RequestUtils.getRequest().getServletPath())) {
                doXss = Boolean.FALSE;
            }
            else {
                doXss = Boolean.TRUE;
            }
            ctxt.setAttribute(DO_XSS, doXss);
        }
        if (Boolean.TRUE.equals(doXss)) {
            if (StrUtil.isNotBlank(value)) {
                return HtmlUtil.filter(value.toString());
            }
        }
        return value;
    }

    /**
     * 是否不需要xss处理
     * @param url
     * @return
     */
    public boolean noXss(String url) {
        if (!enabled) {
            return true;
        }
        if (StrUtil.isBlank(url)) {
            return true;
        }
        if (CollectionUtil.isEmpty(includes)) {
            return true;
        }
        // 处理包含链接
        boolean isInclude = false;
        for (String includePattern : includes) {
            Pattern p = Pattern.compile("^" + includePattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                isInclude = true;
                break;
            }
        }
        if (isInclude == false) {
            return true;
        }

        // 处理排除链接
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        for (String excludePattern : excludes) {
            Pattern p = Pattern.compile("^" + excludePattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}