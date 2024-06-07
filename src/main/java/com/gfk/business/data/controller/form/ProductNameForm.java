package com.gfk.business.data.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Name查询条件
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Name查询条件")
public class ProductNameForm extends BaseForm {
    /**
     * name
     */
    @ApiModelProperty("name")
    private String name;
}
