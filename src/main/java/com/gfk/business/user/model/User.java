package com.gfk.business.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfk.common.model.AuditBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("用户")
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends AuditBaseEntity {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 英文名称
     */
    @ApiModelProperty("英文名称")
    @NotBlank(message = "英文名称不能为空")
    private String nameEn;

    /**
     * 账户
     */
    @ApiModelProperty("账户")
    @NotBlank(message = "账户不能为空")
    private String account;

    /**
     * 加密密码
     */
    @ApiModelProperty("加密密码")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty("盐")
    @JsonIgnore
    private String salt;

    /**
     * 电子邮件--登录账号
     */
    @ApiModelProperty("电子邮件")
    @NotBlank(message = "电子邮件不能为空")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("锁定标识")
    private Boolean lockFlag;

    /**
     * 解锁时间（yyyy-MM-dd HH:mm:ss）
     */
    @ApiModelProperty("解锁时间（yyyy-MM-dd HH:mm:ss）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unlockTime;

    /*****************************/
    /**
     * token
     */
    @ApiModelProperty("token")
    @TableField(exist = false)
    private String token;
}
