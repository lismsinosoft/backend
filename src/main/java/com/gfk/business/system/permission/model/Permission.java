package com.gfk.business.system.permission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : 王哲霖
 * @date : 2022/01/25
 * @description :
 **/
@ApiModel("页面权限")
@Data
@TableName("permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends AuditBaseEntity {

    /**
     * 权限code
     */
    @ApiModelProperty("权限code")
    private String code;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String name;

    /**
     * 所属权限ID
     */
    @ApiModelProperty("所属权限ID")
    private Long parentId;
}
