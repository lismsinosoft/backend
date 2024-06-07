package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户登录表单")
@Data
public class UserLoginForm {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "id", example = "")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

}
