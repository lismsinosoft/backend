package com.gfk.business.common.unavailable.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gfk.common.model.Result;
import com.gfk.framework.properties.UnavailableProperties;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author : 王哲霖
 * @date : 2022/04/28
 * @description :
 **/
@RestController
@ApiIgnore
@RequestMapping("/unavailable")
@Api(tags = "系统停机时间查询")
public class UnavailableController {
    /**
     * 查询系统是否处于停机维护状态
     *
     * @return 是否已停机
     */
    @GetMapping("/status")
    @ApiOperation("查询系统是否处于停机维护状态")
    @ApiOperationSupport(order = 1)
    public Result<Boolean> isUnavailable() {
        if (null != UnavailableProperties.getEnable() && UnavailableProperties.getEnable()) {
            DateTime now = DateTime.now();
            String begin = UnavailableProperties.getBegin();
            String end = UnavailableProperties.getEnd();
            if (StrUtil.isNotBlank(begin) && StrUtil.isNotBlank(end)) {
                try {
                    DateTime beginTime = DateUtil.parse(now.toDateStr() + "-" + begin, "yyyy-MM-dd-HH:mm");
                    DateTime endTime = DateUtil.parse(now.toDateStr() + "-" + end, "yyyy-MM-dd-HH:mm");
                    if (beginTime.isAfter(endTime)) {
                        endTime = DateUtil.offsetDay(endTime, 1);
                    }
                    return Result.success(now.isAfterOrEquals(beginTime) && now.isBeforeOrEquals(endTime));
                } catch (Exception e) {
                    return Result.success(false);
                }
            }
        }
        return Result.success(false);
    }
}
