package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/8/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sim_t1p1")
public class SimT1p1 extends BaseDataEntity {

    private String series;

    private String frame1;

    private String frame2;

    private String type1;

    private String aspect;

    private BigDecimal spending;

    private BigDecimal revenue;

    private Boolean isImportant;

    private String label;
}
