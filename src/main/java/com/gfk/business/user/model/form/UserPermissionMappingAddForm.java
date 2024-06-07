package com.gfk.business.user.model.form;/**
 * @author wzl
 * @Date 2023/2/28
 * @description
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wzl
 * @Date 2023/2/28
 * @description
 */
@Data
public class UserPermissionMappingAddForm {
    /**
     * 权限code
     */
    @ApiModelProperty("权限code")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 权限ID
     */
    @ApiModelProperty("权限ID")
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private int sort;
}
