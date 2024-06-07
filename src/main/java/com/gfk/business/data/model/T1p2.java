package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t1p2")
public class T1p2 extends BaseDataEntity {

    private String series;

    private String year;

    private Date period;

    private BigDecimal units;

    private BigDecimal price;
}
