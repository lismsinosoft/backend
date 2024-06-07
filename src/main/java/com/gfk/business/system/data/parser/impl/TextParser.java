package com.gfk.business.system.data.parser.impl;

import com.gfk.business.data.model.LanguageText;
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
 * @version 1.0 2023/12/13
 */
@Slf4j
public class TextParser extends AbstractParser implements BaseParser {
    protected final List<LanguageText> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.SIM_T1P3;

    public TextParser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        LanguageText entity = new LanguageText();
        entity.setProjectId(projectId);
        entity.setTextKey(data.get(headNo.get("key")));
        entity.setTextValue(data.get(headNo.get("text")));
        entity.setLanguage(data.get(headNo.get("language")));
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).textImport(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }

    @Override
    public List<String> getYear(Map<Integer, String> headMap) {
        return new ArrayList<>();
    }
}
