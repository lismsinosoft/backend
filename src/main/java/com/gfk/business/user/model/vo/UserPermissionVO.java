package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权限VO
 *
 * @author wzl
 * @Date 2023/2/28
 * @description
 */
@Data
public class UserPermissionVO {
    @ApiModelProperty("权限id")
    private Long id;
    @ApiModelProperty("权限code")
    private String code;
    @ApiModelProperty("权限名")
    private String name;
}
