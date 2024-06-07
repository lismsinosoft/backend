package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户注册结果VO
 *
 * @author whk
 * @date 2021/03/24
 **/
@ApiModel("用户注册结果VO")
@Data
public class UserRegisterResultVO {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long id;

    /**
     * 注册token
     */
    @ApiModelProperty("注册token")
    private String token;
}
