package com.gfk.business.system.data.parser.impl;

import com.gfk.business.data.model.SimT1p3;
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
 * @version 1.0 2023/9/2
 */
@Slf4j
public class SimT1p3Parser extends AbstractParser implements BaseParser {
    protected final List<SimT1p3> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.SIM_T1P3;

    public SimT1p3Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        SimT1p3 entity = new SimT1p3();
        entity.setProjectId(projectId);
        entity.setSeries(data.get(headNo.get("Series")));
        entity.setFrame1(data.get(headNo.get("timeFrame1")));
        entity.setFrame2(data.get(headNo.get("timeFrame2")));
        entity.setType1(data.get(headNo.get("Type1")));
        entity.setSpendCurrent(CalcUtils.parseIgnoreException(data.get(headNo.get("spend_current"))));
        entity.setSpendMinimal(CalcUtils.parseIgnoreException(data.get(headNo.get("spend_minimal"))));
        entity.setSpendOptimal(CalcUtils.parseIgnoreException(data.get(headNo.get("spend_optimal"))));
        entity.setRevenueCurrent(CalcUtils.parseIgnoreException(data.get(headNo.get("revenue_current"))));
        entity.setRevenueMinimal(CalcUtils.parseIgnoreException(data.get(headNo.get("revenue_minimal"))));
        entity.setRevenueOptimal(CalcUtils.parseIgnoreException(data.get(headNo.get("revenue_optimal"))));
        entity.setRoiCurrent(CalcUtils.parseIgnoreException(data.get(headNo.get("roi_current"))));
        entity.setRoiMinimal(CalcUtils.parseIgnoreException(data.get(headNo.get("roi_minimal"))));
        entity.setRoiOptimal(CalcUtils.parseIgnoreException(data.get(headNo.get("roi_optimal"))));
        entity.setStatus(data.get(headNo.get("status")));
        entity.setAction(data.get(headNo.get("action")));
        entity.setSpendGapNum(CalcUtils.parseIgnoreException(data.get(headNo.get("spendGap_num"))));
        entity.setSpendGapPct(CalcUtils.parseIgnoreException(data.get(headNo.get("spendGap_pct"))));
        entity.setRoiGapNum(CalcUtils.parseIgnoreException(data.get(headNo.get("roiGap_gap"))));
        entity.setRoiGapPct(CalcUtils.parseIgnoreException(data.get(headNo.get("roiGap_pct"))));
        entity.setRevenueGapNum(CalcUtils.parseIgnoreException(data.get(headNo.get("revenueGap_num"))));
        entity.setRevenueGapPct(CalcUtils.parseIgnoreException(data.get(headNo.get("revenueGap_pct"))));
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).simT1p3Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        return new ArrayList<>();
    }
}
