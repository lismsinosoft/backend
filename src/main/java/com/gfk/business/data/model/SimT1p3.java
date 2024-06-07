package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/8/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sim_t1p3")
public class SimT1p3 extends BaseDataEntity {

    private String series;

    private String frame1;

    private String frame2;

    private String type1;

    private BigDecimal spendCurrent;
    private BigDecimal spendMinimal;
    private BigDecimal spendOptimal;

    private BigDecimal revenueCurrent;
    private BigDecimal revenueMinimal;
    private BigDecimal revenueOptimal;

    private BigDecimal roiCurrent;
    private BigDecimal roiMinimal;
    private BigDecimal roiOptimal;

    private String status;

    private String action;

    private BigDecimal spendGapNum;
    private BigDecimal spendGapPct;

    private BigDecimal roiGapNum;
    private BigDecimal roiGapPct;

    private BigDecimal revenueGapNum;
    private BigDecimal revenueGapPct;
}
