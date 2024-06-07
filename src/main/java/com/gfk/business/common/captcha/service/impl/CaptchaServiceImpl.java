package com.gfk.business.common.captcha.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.gfk.business.common.captcha.controller.form.CaptchaCheckForm;
import com.gfk.business.common.captcha.service.CaptchaService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.key.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 校验码 服务层实现
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private CacheRepository cacheRepository;

    /**
     * 验证码校验
     *
     * @param form 表单
     * @return 1=成功;2=校验码错误;3=校验码失效;4=校验码输入错误
     */
    @Override
    public Boolean checkCaptcha(CaptchaCheckForm form) {
        if (StrUtil.isBlank(form.getCheckKey()) || StrUtil.isBlank(form.getCheckKey())) {
            throw new BusinessException("图片校验码无效，请重新检查");
        }
        String redisKey = DigestUtil.md5Hex(form.getCheckKey());
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
        Object captchaObject = cacheRepository.get(captchaCacheKey, redisKey);
        if (captchaObject == null) {
            throw new BusinessException("图片校验码失效，请刷新验证码");
        }
        if (!form.getCaptcha().toLowerCase().equals(captchaObject.toString().toLowerCase())) {
            throw new BusinessException("图片校验码错误，请检查");
        }
        return true;
    }
}
