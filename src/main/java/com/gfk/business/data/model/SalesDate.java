package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sales_date")
public class SalesDate extends BaseEntity {

    private Long projectId;

    private String product;

    private String year;

    private Date yearMonth;

    private Date endPeriod;

    private String channel;

    private BigDecimal units;

    private BigDecimal value;

    private BigDecimal price;

    private Integer type;
}
