package com.gfk.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wzl
 * @version 1.0 2023/2/28
 */
@Data
public class PageRequest<T> {

    @ApiModelProperty(example = "1")
    private int pageNo = 1;

    @ApiModelProperty(example = "10")
    private int pageSize = 10;

    private T filter;
}
