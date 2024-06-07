package com.gfk.framework.filter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gfk.common.exception.BusinessException;
import com.gfk.framework.properties.UnavailableProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 维护页设置
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Component
@ConditionalOnExpression(value = "${unavailable.enable}")
public class UnavailableFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DateTime now = DateTime.now();
        String begin = UnavailableProperties.getBegin();
        String end = UnavailableProperties.getEnd();
        if(StrUtil.isNotBlank(begin) && StrUtil.isNotBlank(end)){
                DateTime beginTime = DateUtil.parse(now.toDateStr() + "-" + begin, "yyyy-MM-dd-HH:mm");
                DateTime endTime = DateUtil.parse(now.toDateStr() + "-" + end, "yyyy-MM-dd-HH:mm");
                if(beginTime.isAfter(endTime)){
                    endTime = DateUtil.offsetDay(endTime, 1);
                }
                if(now.isAfterOrEquals(beginTime) && now.isBeforeOrEquals(endTime)){
                    throw new BusinessException("系统数据维护中，暂时无法访问");
                }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
