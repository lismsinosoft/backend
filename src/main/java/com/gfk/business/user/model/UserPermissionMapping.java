package com.gfk.business.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 用户权限关联
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户页面权限")
@TableName("user_permission_mapping")
public class UserPermissionMapping extends AuditBaseEntity {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不得为空")
    private Long userId;

    /**
     * 权限id
     */
    @ApiModelProperty("权限名id")
    @NotNull(message = "权限名id不得为空")
    private Long permissionId;

    /**
     * 排序由
     */
    @ApiModelProperty("排序")
    private int sort;
}
