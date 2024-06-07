package com.gfk.business.system.data.parser.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.gfk.business.data.model.T3p1;
import com.gfk.business.system.data.parser.AbstractParser;
import com.gfk.business.system.data.parser.BaseParser;
import com.gfk.business.system.data.service.DataImportServiceV2;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.exception.BusinessException;
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
 * @version 1.0 2023/7/25
 */
@Slf4j
public class T3p1Parser extends AbstractParser implements BaseParser {
    protected final List<T3p1> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.T3P1;

    private final String DRIVEN_PATTERN = "Driven MAT %s";

    private final String SPENDING_PATTERN = "Spending MAT %s";

    private final String ROI_PATTERN = "ROI MAT %s";

    public T3p1Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        List<T3p1> entityList = years.stream().map(year -> {
            T3p1 entity = new T3p1();
            entity.setProjectId(projectId);
            entity.setSeries(data.get(headNo.get("Series")));
            entity.setPlatform(data.get(headNo.get("Platform")));
            entity.setYear(Integer.valueOf(year));
            entity.setDriven(CalcUtils.parseIgnoreException(data.get(headNo.get(String.format(DRIVEN_PATTERN, year)))));
            entity.setSpending(CalcUtils.parseIgnoreException(data.get(headNo.get(String.format(SPENDING_PATTERN, year)))));
            entity.setRoi(CalcUtils.parseIgnoreException(data.get(headNo.get(String.format(ROI_PATTERN, year)))));
            entity.setCreateTime(new Date());
            return entity;
        }).collect(Collectors.toList());
        totalData.addAll(entityList);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).t3p1Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        List<String> headYears = BaseParser.super.getYear(headMap);
        log.info("解析后的表头年份：" + JSON.toJSONString(headYears));
        if (CollUtil.isEmpty(headYears)) {
            throw new BusinessException("表头解析年份失败");
        }
        this.years.addAll(headYears);
        return headYears;
    }
}
