package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户注册表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户注册表单")
@Data
public class UserRegisterForm {

    @NotBlank(message = "账户不能为空")
    @ApiModelProperty(value = "账户")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱", example = "admin@gfk.com")
    private String email;

    @ApiModelProperty(value = "手机号", example = "139********")
    private String mobile;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", example = "123456")
    private String code;
}
