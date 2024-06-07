package com.gfk.business.common.captcha.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 生成校验码 表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("生成校验码 表单")
@Data
public class CaptchaGenerateForm {

    /**
     * 校验码类型
     */
    @NotNull(message = "校验码类型不能为空")
    @ApiModelProperty(value = "校验码类型 1=短信通知,2=管理端用户登录", notes = "1=短信通知,2=管理端用户登录,3=用户端登录")
    private Integer type;
    
    /**
     * 密钥
     */
    @NotBlank(message = "校验码Key不能为空")
    @ApiModelProperty("密钥 前端取当前日期的毫秒数作为校验码")
    private String key;
}
