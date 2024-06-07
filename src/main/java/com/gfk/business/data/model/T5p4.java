package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t5p4")
public class T5p4 extends BaseDataEntity {

    private String varnm;

    private String series;

    private String type1;

    private String type2;

    private String actOn;

    private String metrics;

    private Date period;

    private BigDecimal value;
}
