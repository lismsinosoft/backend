package com.gfk.business.user.model.form;/**
 * @author wzl
 * @Date 2023/2/28
 * @description
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzl
 * @Date 2023/2/28
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListForm {
    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("用户名英文")
    private String nameEn;

    @ApiModelProperty("账户")
    private String account;
}
