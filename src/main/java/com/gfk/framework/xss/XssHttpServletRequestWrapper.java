package com.gfk.framework.xss;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS过滤处理
 * 处理form表单参数
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String strParameter = super.getParameter(name);
        if(StrUtil.isEmpty(strParameter)){
            return strParameter;
        }
        return HtmlUtil.filter(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = HtmlUtil.filter(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}