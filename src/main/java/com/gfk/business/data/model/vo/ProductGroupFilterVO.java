package com.gfk.business.data.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ProductGroup筛选条件VO
 *
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@ApiModel("ProductGroup筛选条件VO")
public class ProductGroupFilterVO {
    /**
     * 产品
     */
    @ApiModelProperty("产品")
    private String product;

    /**
     * lGroup
     */
    @ApiModelProperty("lGroup")
    private List<String> groups;
}
