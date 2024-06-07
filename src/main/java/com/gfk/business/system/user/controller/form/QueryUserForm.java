package com.gfk.business.system.user.controller.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询管理员表单
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("查询管理员表单")
@Data
public class QueryUserForm {

    @ApiModelProperty("账号")
    private String username_WithLike;

    @ApiModelProperty("姓名")
    private String name_WithLike;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Integer status;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("创建日期（起始）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime_WithGreatEqual;

    @ApiModelProperty("创建日期（结束）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime_WithLessEqual;

}
