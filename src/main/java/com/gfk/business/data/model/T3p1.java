package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/7/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t3p1")
public class T3p1 extends BaseDataEntity {

    private String series;

    private String platform;

    private Integer year;

    private BigDecimal driven;

    private BigDecimal spending;

    private BigDecimal roi;
}
