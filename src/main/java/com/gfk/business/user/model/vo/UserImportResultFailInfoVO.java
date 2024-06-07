package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户导入结果错误信息VO
 *
 * @author whk
 * @date 2021/05/212
 **/
@ApiModel("用户导入结果错误信息VO")
@Data
public class UserImportResultFailInfoVO {

    /**
     * 行数
     */
    @ApiModelProperty("行数")
    private Integer row;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 原因
     */
    @ApiModelProperty("原因")
    private String info;

    public UserImportResultFailInfoVO(Integer row, String email, String info) {
        this.row = row;
        this.email = email;
        this.info = info;
    }
}
