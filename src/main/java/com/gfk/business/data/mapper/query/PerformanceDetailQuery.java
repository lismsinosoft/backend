package com.gfk.business.data.mapper.query;

import lombok.Data;

/**
 * @author wzl
 * @version 1.0 2023/5/20
 */
@Data
public class PerformanceDetailQuery {
    private Long projectId;

    private String product;

    private Integer yearFirst;

    private Integer yearLast;
}
