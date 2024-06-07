package com.gfk.business.system.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.gfk.business.system.data.mapper.*;
import com.gfk.business.system.data.model.*;
import com.gfk.business.system.data.service.DataImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据导入 service 服务层实现
 *
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {
    private final SalesDateImportMapper salesDateImportMapper;

    private final MediaImportMapper mediaImportMapper;

    private final MediaChannelImportMapper mediaChannelImportMapper;

    private final HlImportMapper hlImportMapper;

    private final CurveImportMapper curveImportMapper;

    /**
     * 导入SalesDate数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    @Override
    public int saveSalesDate(List<SalesDateImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return salesDateImportMapper.saveBatch(importList);
    }

    /**
     * 清空Sales Date导入表数据
     */
    @Override
    public void truncateSalesDateImport() {
        salesDateImportMapper.truncateImport();
    }

    /**
     * 导入Media数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    @Override
    public int saveMedia(List<MediaImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return mediaImportMapper.saveBatch(importList);
    }

    /**
     * 清空Media导入表数据
     */
    @Override
    public void truncateMediaImport() {
        mediaImportMapper.truncateImport();
    }

    /**
     * 导入Media Channel数据
     *
     * @param importList 待保存数据
     * @return 成功条数
     */
    @Override
    public int saveMediaChannel(List<MediaChannelImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return mediaChannelImportMapper.saveBatch(importList);
    }

    /**
     * 清空Media Channel导入表数据
     */
    @Override
    public void truncateMediaChannelImport() {
        mediaChannelImportMapper.truncateImport();
    }

    @Override
    public int saveHl(List<HlImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return hlImportMapper.saveBatch(importList);
    }

    @Override
    public void truncateHlImport() {
        hlImportMapper.truncateImport();
    }

    @Override
    public int saveCurve(List<CurveImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return curveImportMapper.saveBatch(importList);
    }

    @Override
    public int saveCurveImportant(List<CurveImportantImport> importList) {
        if (CollUtil.isEmpty(importList)) {
            return 0;
        }
        return curveImportMapper.saveImportantBatch(importList);
    }

    /**
     * 清空Curve导入表
     * 包括Curve和CurveImportant
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void truncateCurveImportAll() {
        curveImportMapper.truncateImport();
        curveImportMapper.truncateImportantImport();
    }

}
