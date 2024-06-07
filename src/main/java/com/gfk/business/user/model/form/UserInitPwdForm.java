package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户初始化密码表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户初始化密码表单")
@Data
public class UserInitPwdForm {

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private String id;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "abc")
    private String password;

    @NotBlank(message = "token不能为空")
    @ApiModelProperty(value = "token", example = "abc123456")
    private String token;

    @NotBlank(message = "code不能为空")
    @ApiModelProperty(value = "code", example = "123456")
    private String code;

}
