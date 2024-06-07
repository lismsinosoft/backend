package com.gfk.business.system.data.service;

import com.gfk.business.data.model.*;
import com.gfk.common.enums.ImportEnum;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface DataImportServiceV2 {
    void t1p1Import(Long projectId, List<String> years, List<T1p1> data, ImportEnum importEnum);

    void t1p2Import(Long projectId, List<String> years, List<T1p2> data, ImportEnum importEnum);

    void t1p3Import(Long projectId, List<String> years, List<T1p3> data, ImportEnum importEnum);

    void t2p1Import(Long projectId, List<String> years, List<T2p1> data, ImportEnum importEnum);

    void t2p2Import(Long projectId, List<String> years, List<T2p2> data, ImportEnum importEnum);

    void t3p1Import(Long projectId, List<String> years, List<T3p1> data, ImportEnum importEnum);

    void t4p1Import(Long projectId, List<String> years, List<T4p1> data, ImportEnum importEnum);

    void t4p2Import(Long projectId, List<String> years, List<T4p2> data, ImportEnum importEnum);

    void t4p3Import(Long projectId, List<String> years, List<T4p3> data, ImportEnum importEnum);

    void t4p4Import(Long projectId, List<String> years, List<T4p4> data, ImportEnum importEnum);

    void t5p1Import(Long projectId, List<String> years, List<T5p1> data, ImportEnum importEnum);

    void t5p2Import(Long projectId, List<String> years, List<T5p2> data, ImportEnum importEnum);

    void t5p3Import(Long projectId, List<String> years, List<T5p3> data, ImportEnum importEnum);

    void t5p4Import(Long projectId, List<String> years, List<T5p4> data, ImportEnum importEnum);

    void timeFrameImport(Long projectId, List<String> years, List<TimeFrame> data, ImportEnum importEnum);

    void simT1p1Import(Long projectId, List<String> years, List<SimT1p1> data, ImportEnum importEnum);

    void simT1p2Import(Long projectId, List<String> years, List<SimT1p2> data, ImportEnum importEnum);

    void simT1p3Import(Long projectId, List<String> years, List<SimT1p3> data, ImportEnum importEnum);

    void textImport(Long projectId, List<String> years, List<LanguageText> data, ImportEnum importEnum);
}
