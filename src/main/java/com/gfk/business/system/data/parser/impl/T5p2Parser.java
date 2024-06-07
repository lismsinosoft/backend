package com.gfk.business.system.data.parser.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.gfk.business.data.model.T5p2;
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
 * @version 1.0 2023/8/4
 */
@Slf4j
public class T5p2Parser extends AbstractParser implements BaseParser {
    protected final List<T5p2> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.T5P2;

    public T5p2Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        List<T5p2> entityList = years.stream().map(year -> {
            T5p2 entity = new T5p2();
            entity.setProjectId(projectId);
            entity.setSeries(data.get(headNo.get("Series")));
            entity.setType1(data.get(headNo.get("Type1")));
            entity.setType2(data.get(headNo.get("Type2")));
            entity.setActOn(data.get(headNo.get("Act On")));
            entity.setYear(Integer.valueOf(year));
            entity.setValue(CalcUtils.parseIgnoreException(data.get(headNo.get(String.format(VALUE_PATTERN, year)))));
            entity.setCreateTime(new Date());
            return entity;
        }).collect(Collectors.toList());
        totalData.addAll(entityList);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).t5p2Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
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
