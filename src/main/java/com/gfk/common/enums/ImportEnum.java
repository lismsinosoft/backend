package com.gfk.common.enums;

import com.gfk.business.system.data.parser.BaseParser;
import com.gfk.business.system.data.parser.impl.*;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
public enum ImportEnum {
    T1P1(1, "Dashboard T1P1", T1p1Parser::new, "T1P1"),
    T1P2(2, "Dashboard T1P2", T1p2Parser::new, "T1P2"),
    T1P3(3, "Dashboard T1P3", T1p3Parser::new, "T1P3"),
    T2P1(4, "Dashboard T2P1", T2p1Parser::new, "T2P1"),
    T2P2(5, "Dashboard T2P2", T2p2Parser::new, "T2P2"),
    T3P1(6, "Dashboard T3P1", T3p1Parser::new, "T3P1"),
    T4P1(7, "Dashboard T4P1", T4p1Parser::new, "T4P1"),
    T4P2(8, "Dashboard T4P2", T4p2Parser::new, "T4P2"),
    T4P3(9, "Dashboard T4P3", T4p3Parser::new, "T4P3"),
    T4P4(10, "Dashboard T4P4", T4p4Parser::new, "T4P4"),
    T5P1(11, "Dashboard T5P1", T5p1Parser::new, "T5P1"),
    T5P2(12, "Dashboard T5P2", T5p2Parser::new, "T5P2"),
    T5P3D1(13, "Dashboard T5P3D1", T5p3d1Parser::new, "T5P3D1"),
    T5P3D2(13, "Dashboard T5P3D2", T5p3d2Parser::new, "T5P3D2"),
    T5P4(14, "Dashboard T5P4", T5p4Parser::new, "T5P4"),
    TIME_FRAME(15, "SO timeFrame", TimeFrameParser::new, "TimeFrame"),

    SIM_T1P1D1(16, "Simulator T1P1D1", SimT1p1d1Parser::new, "Simulator T1P1D1"),

    SIM_T1P1D2(17, "Simulator T1P1D2", SimT1p1d2Parser::new, "Simulator T1P2D1"),
    SIM_T1P2(18, "Simulator T1P2", SimT1p2Parser::new, "Simulator T1P2"),

    SIM_T1P3(19, "Simulator T1P3", SimT1p3Parser::new, "Simulator T1P3"),

    TEXT(20, "language_text", TextParser::new, "Language Text"),
    ;

    public final int tableNo;
    public final String sheetName;
    public final Function<Long, BaseParser> func;
    public final String displayName;

    ImportEnum(int tableNo, String sheetName, Function<Long, BaseParser> func, String displayName) {
        this.tableNo = tableNo;
        this.sheetName = sheetName;
        this.func = func;
        this.displayName = displayName;
    }

    public static ImportEnum getByTableNo(Integer tableNo) {
        if (null == tableNo) {
            return null;
        }
        for (ImportEnum value : values()) {
            if (Objects.equals(tableNo, value.tableNo)) {
                return value;
            }
        }
        return null;
    }
}
