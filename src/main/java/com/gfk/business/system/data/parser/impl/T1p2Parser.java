package com.gfk.business.system.data.parser.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.gfk.business.data.model.T1p2;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
@Slf4j
public class T1p2Parser extends AbstractParser implements BaseParser {
    protected final List<T1p2> totalData = new ArrayList<>();
    private final ImportEnum importEnum = ImportEnum.T1P2;

    public T1p2Parser(Long projectId) {
        super(projectId);
    }

    @Override
    public void parseData(Map<Integer, String> headMap, Map<Integer, String> data) {
        Map<String, Integer> headNo = headMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (oldVal, newVal) -> newVal));
        T1p2 entity = new T1p2();
        entity.setProjectId(projectId);
        entity.setSeries(data.get(headNo.get("Series")));
        String yearRowStr = data.get(headNo.get("yearrpt"));
        if (StrUtil.isNotBlank(yearRowStr)) {
            Pattern pattern = Pattern.compile(this.YEAR_REGEX);
            Matcher matcher = pattern.matcher(yearRowStr);
            if (matcher.find()) {
                entity.setYear(matcher.group(1));
            }
        }
        DateTime period = excelDateParse(data.get(headNo.get("period")));
        entity.setPeriod(period);
        entity.setUnits(CalcUtils.parseIgnoreException(data.get(headNo.get("units"))));
        entity.setPrice(CalcUtils.parseIgnoreException(data.get(headNo.get("price"))));
        entity.setCreateTime(new Date());
        totalData.add(entity);
    }

    @Override
    public void saveData() {
        SpringUtils.getBean(DataImportServiceV2.class).t1p2Import(this.projectId, new ArrayList<>(this.years), this.totalData, this.importEnum);
    }
}
