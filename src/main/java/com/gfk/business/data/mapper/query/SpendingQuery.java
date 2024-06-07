package com.gfk.business.data.mapper.query;

import lombok.Data;

/**
 * @author wzl
 * @Date 2023/3/12
 * @description
 */
@Data
public class SpendingQuery {
    private Long projectId;

    private String product;

    private String firstYear;

    private String lastYear;
}
