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
@TableName("t4p2")
public class T4p2 extends BaseDataEntity {

    private String series;

    private String type1;

    private String type2;

    private String actOn;

    private Integer year;

    private BigDecimal roi;
}
