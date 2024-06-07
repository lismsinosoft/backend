package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户修改密码表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户修改密码表单")
@Data
public class UserChangePwdForm {
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱", example = "admin@gfk.com")
    private String account;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码", example = "123456")
    private String oldPwd;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", example = "123456")
    private String newPwd;

}
