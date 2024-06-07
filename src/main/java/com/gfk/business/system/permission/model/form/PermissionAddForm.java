package com.gfk.business.system.permission.model.form;/**
 * @author wzl
 * @Date 2023/2/28
 * @description
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增权限form
 *
 * @author wzl
 * @Date 2023/2/28
 * @description
 */
@Data
public class PermissionAddForm {
    /**
     * 权限code
     */
    @ApiModelProperty("权限code")
    @NotBlank(message = "权限code不能为空")
    private String code;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    @NotBlank(message = "权限名不能为空")
    private String name;

    /**
     * 所属权限ID
     */
    @ApiModelProperty("所属权限ID")
    private Long parentId;
}
