package com.gfk.business.system.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfk.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 管理员信息
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("管理员信息")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends AuditBaseEntity {

    /**
     * 账号
     */
    @NotBlank(message = "管理员名不能为空")
    @ApiModelProperty(value = "帐号", required = true)
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 加密密码的盐
     */
    @JsonIgnore
    @ApiModelProperty("加密密码的盐")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;


    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

    @ApiModelProperty("管理员类型 0-系统")
    private Integer userType;

    /**
     * token
     */
    @ApiModelProperty("token")
    @TableField(exist = false)
    private String token;

    /**
     * 权限Code
     */
    @ApiModelProperty("权限Code")
    @TableField(exist = false)
    private List<String> permissionCode;


    @JsonIgnore
    public String getCredentialsSalt() {
        return username + salt;
    }
}
