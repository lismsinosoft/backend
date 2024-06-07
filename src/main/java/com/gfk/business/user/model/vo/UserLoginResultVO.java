package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : 王哲霖
 * @date : 2022/01/24
 * @description :
 **/
@ApiModel("用户登录结果")
@Data
public class UserLoginResultVO {

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户名-英文")
    private String nameEn;

    /**
     * token值
     */
    @ApiModelProperty("token值")
    private String token;

    @ApiModelProperty("用户权限")
    private List<UserPermissionVO> permissionList;

    /**
     * 用户项目
     */
    @ApiModelProperty("用户项目")
    private List<UserProjectVO> projectList;
}
