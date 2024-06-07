package com.gfk.business.common.captcha.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.gfk.business.common.captcha.controller.form.CaptchaGenerateForm;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.key.CacheConstants;
import com.gfk.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 校验码管理
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Api(tags = "校验码管理")
@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    /**
     * base64 图片前缀
     */
    private static final String BASE64_PRE = "data:image/png;base64,";
    private final CacheRepository cacheRepository;

    /**
     * 获取校验码
     *
     * @return
     */
    @ApiOperation(value = "获取校验码", notes = "前端取当前日期的毫秒数作为校验码")
    @GetMapping("/get_captcha")
    public Result<String> getCaptcha(CaptchaGenerateForm form) {
        if (StrUtil.isBlank(form.getKey())) {
            throw new BusinessException("获取校验码异常，请检查请求密钥是否存在");
        }
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(105, 35, 4, 3);
        String code = captcha.getCode();
        String imageBase64 = BASE64_PRE + captcha.getImageBase64();
        String redisKey = DigestUtil.md5Hex(form.getKey());
        String captchaCacheKey = "";
        switch (form.getType()) {
            case 1:
                captchaCacheKey = CacheConstants.SMS_CAPTCHA;
                break;
            case 2:
                captchaCacheKey = CacheConstants.SYS_USER_LOGIN_CAPTCHA;
                break;
            case 3:
                captchaCacheKey = CacheConstants.STAFF_LOGIN_CAPTCHA;
                break;
        }
        cacheRepository.set(captchaCacheKey, redisKey, code, 5, TimeUnit.MINUTES);
        return Result.success(imageBase64);
    }
}
