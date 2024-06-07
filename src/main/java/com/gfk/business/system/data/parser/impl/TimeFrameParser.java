package com.gfk.business.system.data.parser.impl;

import com.gfk.business.data.model.TimeFrame;
import com.gfk.business.system.data.parser.AbstractParser;
import com.gfk.business.system.data.parser.BaseParser;
import com.gfk.business.system.data.service.DataImportServiceV2;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wzl
 * @version 1.0 2023/8/29
 */
@Slf4j
public class TimeFrameParser extends AbstractParser implements BaseParser {
    protected final List<TimeFrame> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.T5P4;

    public TimeFrameParser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        TimeFrame entity = new TimeFrame();
        entity.setProjectId(projectId);
        entity.setFrame1(data.get(headNo.get("timeFrame1")));
        entity.setFrame2(data.get(headNo.get("timeFrame2")));
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).timeFrameImport(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        return new ArrayList<>();
    }
}
