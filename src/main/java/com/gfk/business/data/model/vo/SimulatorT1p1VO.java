package com.gfk.business.data.model.vo;

import com.gfk.business.data.model.SimT1p1;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
public class SimulatorT1p1VO {

    private List<SimulatorPointVO> list;

    private Set<String> aspectMap;

    private Set<String> typeMap;

    @Data
    public static class SimulatorPointVO {
        private String type1;

        private String aspect;

        private BigDecimal x;

        private BigDecimal y;

        private Boolean isImportant;

        private String label;

        public static SimulatorPointVO of(SimT1p1 entity) {
            SimulatorPointVO vo = new SimulatorPointVO();
            vo.setAspect(entity.getAspect());
            vo.setType1(entity.getType1());
            vo.setX(entity.getSpending());
            vo.setY(entity.getRevenue());
            vo.setIsImportant(entity.getIsImportant());
            vo.setLabel(entity.getLabel());
            return vo;
        }
    }
}
