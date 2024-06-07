package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权限查询form
 *
 * @author wzl
 * @Date 2023/3/2
 * @description
 */
@Data
public class UserPermissionQueryForm {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 权限id
     */
    @ApiModelProperty("权限id")
    private Long permissionId;
}
