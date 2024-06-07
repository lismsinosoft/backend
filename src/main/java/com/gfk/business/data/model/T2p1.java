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
@TableName("t2p1")
public class T2p1 extends BaseDataEntity {

    private String series;

    private Integer year;

    private String label;

    private BigDecimal value;

}
