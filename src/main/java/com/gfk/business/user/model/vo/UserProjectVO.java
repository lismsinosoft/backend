package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户项目VO
 *
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
public class UserProjectVO {
    @ApiModelProperty("项目id")
    private Long id;
    @ApiModelProperty("项目code")
    private String code;
    @ApiModelProperty("项目名")
    private String name;

    @ApiModelProperty("项目名英文")
    private String nameEn;

    @ApiModelProperty("项目url地址")
    private String picUrl;

    @ApiModelProperty("项目url地址")
    private String picUrl2;
}
