package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户忘记密码表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户忘记密码表单")
@Data
public class UserForgetPwdForm {
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱", example = "admin@gfk.com")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", example = "139********")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", example = "123456")
    private String code;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456")
    private String pwd;

}
