package com.gfk.business.data.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gfk.business.data.model.SimT1p2;
import com.gfk.framework.jackson.BigDecimalFor3ScaleSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
public class SimulatorT1p2VO {
    private String name;

    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal spend;

    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal revenue;

    @JsonSerialize(using = BigDecimalFor3ScaleSerializer.class)
    private BigDecimal roi;

    public static SimulatorT1p2VO of(SimT1p2 entity) {
        SimulatorT1p2VO vo = new SimulatorT1p2VO();
        vo.setName(entity.getLabel());
        vo.setSpend(entity.getSpend());
        vo.setRevenue(entity.getRevenue());
        vo.setRoi(null == entity.getRoi() ? null : entity.getRoi().setScale(2, RoundingMode.HALF_UP));
        return vo;
    }
}
