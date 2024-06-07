package com.gfk.business.system.user.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 管理员冻结/解冻 表单
 */
@ApiModel("管理员冻结/解冻 表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFrozenForm {

    @NotBlank(message = "ID不能为空")
    @ApiModelProperty("管理员编号")
    private String id;

    @NotBlank(message = "（冻结）状态不能为空")
    @ApiModelProperty("（冻结）状态")
    private Integer status;

}
