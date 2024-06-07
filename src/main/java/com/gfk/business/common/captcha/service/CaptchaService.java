package com.gfk.business.common.captcha.service;

import com.gfk.business.common.captcha.controller.form.CaptchaCheckForm;

/**
 * 校验码管理 service
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public interface CaptchaService {

    /**
     * 校验码校验
     *
     * @param form 表单
     * @return 1=成功;2=校验码错误;3=校验码失效;4=校验码输入错误
     */
    Boolean checkCaptcha(CaptchaCheckForm form);

}
