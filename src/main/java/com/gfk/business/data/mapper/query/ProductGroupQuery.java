package com.gfk.business.data.mapper.query;

import lombok.Data;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
public class ProductGroupQuery {
    private Long projectId;

    private String product;

    private String group;

    private Integer firstYear;

    private Integer lastYear;
}
