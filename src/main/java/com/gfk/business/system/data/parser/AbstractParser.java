package com.gfk.business.system.data.parser;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public abstract class AbstractParser {

    protected final Long projectId;
    protected final String VALUE_PATTERN = "MAT %s";
    protected final String GROWTH_PATTERN = "Growth %s";
    protected final String PERCENT_PATTERN = "MAT %s pct";
    protected final String YEAR_REGEX = "(20\\d{2})";
    protected final Set<String> years = new HashSet<>();

    public AbstractParser(Long projectId) {
        this.projectId = projectId;
    }

    protected DateTime excelDateParse(String dateStr) {
        DateTime period = DateUtil.parse(dateStr, "yyyy/MM/dd");
        if (period.year() < 1949) {
            period = DateUtil.parse(dateStr, "MM/dd/yyyy");
        }
        if (period.year() < 1949) {
            period = DateUtil.parse(dateStr, "dd/MM/yyyy");
        }
        return period;
    }
}
