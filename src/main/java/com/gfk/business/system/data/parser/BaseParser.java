package com.gfk.business.system.data.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
public interface BaseParser {

    void parseData(Map<Integer, String> headMap, Map<Integer, String> data);

    void saveData();

    default List<String> getYear(Map<Integer, String> headMap) {
        List<String> years = new ArrayList<>();
        // 通过header
        headMap.forEach((k, v) -> {
            Pattern yearPattern = Pattern.compile("MAT (20\\d{2})$");
            Matcher matcher = yearPattern.matcher(v);
            if (matcher.find()) {
                years.add(matcher.group(1));
            }
        });
        return years;
    }
}
