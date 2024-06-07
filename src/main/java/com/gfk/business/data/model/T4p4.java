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
@TableName("t4p4")
public class T4p4 extends BaseDataEntity {

    private String series;

    private String type2;

    private String type3;

    private String actOn;

    private Integer year;

    private BigDecimal roi;
}
