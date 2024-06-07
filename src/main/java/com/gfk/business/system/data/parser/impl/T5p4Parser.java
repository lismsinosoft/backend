package com.gfk.business.system.data.parser.impl;

import cn.hutool.core.date.DateTime;
import com.gfk.business.data.model.T5p4;
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
 * @version 1.0 2023/8/4
 */
@Slf4j
public class T5p4Parser extends AbstractParser implements BaseParser {
    protected final List<T5p4> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.T5P4;

    public T5p4Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        T5p4 entity = new T5p4();
        entity.setProjectId(projectId);
        entity.setVarnm(data.get(headNo.get("varnm")));
        entity.setSeries(data.get(headNo.get("Series")));
        entity.setType1(data.get(headNo.get("Type1")));
        entity.setType2(data.get(headNo.get("Type2")));
        entity.setActOn(data.get(headNo.get("Act On")));
        entity.setMetrics(data.get(headNo.get("metrics")));
        DateTime period = excelDateParse(data.get(headNo.get("period")));
        entity.setPeriod(period);
        entity.setValue(CalcUtils.parseIgnoreException(data.get(headNo.get("value"))));
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).t5p4Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        return new ArrayList<>();
    }
}
