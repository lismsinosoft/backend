package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t1p1")
public class T1p1 extends BaseDataEntity {

    private String series;

    private String type1;

    private Integer year;

    private BigDecimal value;

    private BigDecimal growth;

    private BigDecimal percent;
}
