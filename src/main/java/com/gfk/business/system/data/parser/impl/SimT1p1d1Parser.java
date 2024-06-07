package com.gfk.business.system.data.parser.impl;

import com.gfk.business.data.model.SimT1p1;
import com.gfk.business.system.data.parser.AbstractParser;
import com.gfk.business.system.data.parser.BaseParser;
import com.gfk.business.system.data.service.DataImportServiceV2;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.util.CalcUtils;
import com.gfk.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wzl
 * @version 1.0 2023/8/31
 */
@Slf4j
public class SimT1p1d1Parser extends AbstractParser implements BaseParser {
    protected final List<SimT1p1> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.SIM_T1P1D1;

    public SimT1p1d1Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        SimT1p1 entity = new SimT1p1();
        entity.setProjectId(projectId);
        entity.setSeries(data.get(headNo.get("Series")));
        entity.setFrame1(data.get(headNo.get("timeFrame1")));
        entity.setFrame2(data.get(headNo.get("timeFrame2")));
        entity.setType1(data.get(headNo.get("Type1")));
        entity.setAspect(data.get(headNo.get("aspect")));
        entity.setSpending(CalcUtils.parseIgnoreException(data.get(headNo.get("Spending"))));
        entity.setRevenue(CalcUtils.parseIgnoreException(data.get(headNo.get("Revenue"))));
        entity.setIsImportant(false);
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).simT1p1Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        return new ArrayList<>();
    }
}
