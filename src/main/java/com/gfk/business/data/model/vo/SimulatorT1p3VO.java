package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.SimT1p3;
import com.gfk.common.util.CalcUtils;
import com.gfk.framework.jackson.BigDecimalFor3ScaleSerializer;
import com.gfk.framework.jackson.BigDecimalSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
public class SimulatorT1p3VO {
    private String type1;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendCurrent;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendMinimal;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendOptimal;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueCurrent;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueMinimal;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueOptimal;

    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal roiCurrent;
    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal roiMinimal;
    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal roiOptimal;

    private String status;

    private String action;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendGapNum;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal spendGapShare;

    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal roiGapNum;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiGapShare;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueGapNum;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal revenueGapShare;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiCurrentPct;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiMinimalPct;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiOptimalPct;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal roiGapPct;

    public static SimulatorT1p3VO of(SimT1p3 entity) {
        SimulatorT1p3VO vo = new SimulatorT1p3VO();
        vo.setType1(entity.getType1());
        vo.setAction(entity.getAction());
        vo.setStatus(entity.getStatus());

        vo.setSpendCurrent(entity.getSpendCurrent());
        vo.setSpendMinimal(entity.getSpendMinimal());
        vo.setSpendOptimal(entity.getSpendOptimal());

        vo.setRevenueCurrent(entity.getRevenueCurrent());
        vo.setRevenueMinimal(entity.getRevenueMinimal());
        vo.setRevenueOptimal(entity.getRevenueOptimal());

        vo.setRoiCurrent(CalcUtils.safeValue(entity.getRoiCurrent(), 2));
        vo.setRoiMinimal(CalcUtils.safeValue(entity.getRoiMinimal(), 2));
        vo.setRoiOptimal(CalcUtils.safeValue(entity.getRoiOptimal(), 2));

        vo.setSpendGapNum(entity.getSpendGapNum());
        vo.setSpendGapShare(entity.getSpendGapPct().multiply(CalcUtils.PERCENT));

        vo.setRevenueGapNum(entity.getRevenueGapNum());
        vo.setRevenueGapShare(entity.getRevenueGapPct().multiply(CalcUtils.PERCENT));

        vo.setRoiGapNum(CalcUtils.safeValue(entity.getRoiGapNum(), 2));
        vo.setRoiGapShare(entity.getRoiGapPct().multiply(CalcUtils.PERCENT));
        return vo;
    }
}
