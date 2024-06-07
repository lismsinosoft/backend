package com.gfk.business.system.data.service;

import com.gfk.business.system.data.model.*;

import java.util.List;

/**
 * 数据导入 service
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
public interface DataImportService {
    /**
     * 导入SalesDate数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveSalesDate(List<SalesDateImport> importList);

    /**
     * 清空Sales Date导入表数据
     */
    void truncateSalesDateImport();

    /**
     * 导入Media数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveMedia(List<MediaImport> importList);

    /**
     * 清空Media导入表数据
     */
    void truncateMediaImport();

    /**
     * 导入Media Channel数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveMediaChannel(List<MediaChannelImport> importList);

    /**
     * 清空Media Channel导入表数据
     */
    void truncateMediaChannelImport();

    /**
     * 导入HL数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveHl(List<HlImport> importList);

    /**
     * 清空HL导入表数据
     */
    void truncateHlImport();

    /**
     * 导入Curve数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveCurve(List<CurveImport> importList);

    /**
     * 导入Curve Important 数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    int saveCurveImportant(List<CurveImportantImport> importList);

    /**
     * 清空Curve导入表
     * 包括Curve和CurveImportant
     */
    void truncateCurveImportAll();
}
