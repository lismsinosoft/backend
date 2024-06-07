package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户忘记密码检查表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户忘记密码检查表单")
@Data
public class UserForgetCheckForm {

    @NotNull(message = "检查类型")
    @ApiModelProperty(value = "检查类型 1=email; 2=name; 3=mobile")
    private Integer checkType;

    @NotBlank(message = "检查值")
    @ApiModelProperty(value = "检查值")
    private String checkValue;
}
