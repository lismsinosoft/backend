package com.gfk.business.data.mapper.query;

import lombok.Data;

/**
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Data
public class PerformanceAnnuallyQuery {
    private Long projectId;

    private String product;

    private String firstYear;

    private String lastYear;
}
