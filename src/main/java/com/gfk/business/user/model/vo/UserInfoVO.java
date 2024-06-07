package com.gfk.business.user.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gfk.business.user.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author : 王哲霖
 * @date : 2022/01/24
 * @description : 用户权限VO
 **/
@ApiModel("用户权限VO")
@Data
public class UserInfoVO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 用户英文姓名
     */
    @ApiModelProperty("用户英文姓名")
    private String nameEn;

    /**
     * 账户
     */
    @ApiModelProperty("账户")
    private String account;

    /**
     * 邮件
     */
    @ApiModelProperty("邮件")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 解锁时间
     */
    @ApiModelProperty("解锁时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date unlockTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public UserInfoVO() {
    }

    public UserInfoVO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nameEn = user.getNameEn();
        this.account = user.getAccount();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.unlockTime = user.getUnlockTime();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }
}
