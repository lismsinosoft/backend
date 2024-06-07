package com.gfk.business.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用户项目权限关联
 *
 * @author wzl
 * @version 1.0 2024/3/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户项目权限")
@TableName("user_project_permission_mapping")
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectPermissionMapping extends AuditBaseEntity {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不得为空")
    private Long userId;

    /**
     * 项目id
     */
    @ApiModelProperty("项目id")
    @NotNull(message = "项目id不得为空")
    private Long projectId;

    /**
     * 权限值
     */
    @ApiModelProperty("权限值")
    @NotNull(message = "权限值不得为空")
    private String permission;
}
