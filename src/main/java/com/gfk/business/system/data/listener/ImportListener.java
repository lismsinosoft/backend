package com.gfk.business.system.data.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gfk.business.system.data.parser.BaseParser;
import com.gfk.common.enums.ImportEnum;

import java.util.Map;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
public class ImportListener extends AnalysisEventListener<Map<Integer, String>> {

    private final BaseParser parser;

    private Map<Integer, String> HEAD_MAP;

    public ImportListener(ImportEnum importEnum, Long projectId) {
        this.parser = importEnum.func.apply(projectId);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        parser.parseData(HEAD_MAP, data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.parser.saveData();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        this.HEAD_MAP = headMap;
        this.parser.getYear(HEAD_MAP);
    }
}
