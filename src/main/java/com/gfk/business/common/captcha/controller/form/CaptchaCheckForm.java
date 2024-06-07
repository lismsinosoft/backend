package com.gfk.business.common.captcha.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 校验校验码 表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("校验校验码 表单")
@Data
public class CaptchaCheckForm {

    /**
     * 校验码类型
     */
    @NotNull(message = "校验码类型不能为空")
    @ApiModelProperty(value = "校验码类型", notes = "1=短信通知,2=管理端用户登录,3=用户端注册")
    private Integer type;

    /**
     * 前端取当前日期的毫秒数作为校验码
     */
    @NotBlank(message = "校验码Key不能为空")
    @ApiModelProperty(value = "校验码Key", notes = "即生成验证码所使用的校验码")
    private String checkKey;

    @NotBlank(message = "校验码不能为空")
    @ApiModelProperty(value = "校验码", notes = "用户输入的验证码")
    private String captcha;
}
