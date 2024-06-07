package com.gfk.business.system.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gfk.business.data.mapper.*;
import com.gfk.business.data.model.*;
import com.gfk.business.system.data.service.DataImportServiceV2;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataImportServiceV2Impl implements DataImportServiceV2 {

    private final ProjectYearsMapper projectYearsMapper;

    private final ProjectSeriesMapper projectSeriesMapper;

    private final T1p1Mapper t1p1Mapper;

    private final T1p2Mapper t1p2Mapper;

    private final T1p3Mapper t1p3Mapper;

    private final T2p1Mapper t2p1Mapper;

    private final T2p2Mapper t2p2Mapper;

    private final T3p1Mapper t3p1Mapper;

    private final T4p1Mapper t4p1Mapper;

    private final T4p2Mapper t4p2Mapper;

    private final T4p3Mapper t4p3Mapper;

    private final T4p4Mapper t4p4Mapper;

    private final T5p1Mapper t5p1Mapper;

    private final T5p2Mapper t5p2Mapper;

    private final T5p3Mapper t5p3Mapper;

    private final T5p4Mapper t5p4Mapper;

    private final TimeFrameMapper timeFrameMapper;

    private final SimT1p1Mapper simT1p1Mapper;

    private final SimT1p2Mapper simT1p2Mapper;

    private final SimT1p3Mapper simT1p3Mapper;

    private final LanguageTextMapper languageTextMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void t1p1Import(Long projectId, List<String> years, List<T1p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T1p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T1P1数据，项目ID:" + projectId);
        // 删除原有数据
        t1p1Mapper.delete(Wrappers.<T1p1>lambdaQuery().eq(T1p1::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T1p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T1P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T1p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t1p1Mapper.batchInsert(insertPart);
        }
        log.info("T1P1数据插入完毕");
    }

    @Override
    public void t1p2Import(Long projectId, List<String> years, List<T1p2> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T1p2::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);

        log.info("删除原有T1P2数据，项目ID:" + projectId);
        // 删除原有数据
        t1p2Mapper.delete(Wrappers.<T1p2>lambdaQuery().eq(T1p2::getProjectId, projectId));
        //插入新数据
        List<List<T1p2>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T1P2数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T1p2> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t1p2Mapper.batchInsert(insertPart);
        }
        log.info("T1P2数据插入完毕");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void t1p3Import(Long projectId, List<String> years, List<T1p3> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T1p3::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T1P3数据，项目ID:" + projectId);
        // 删除原有数据
        t1p3Mapper.delete(Wrappers.<T1p3>lambdaQuery().eq(T1p3::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T1p3>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T1P3数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T1p3> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t1p3Mapper.batchInsert(insertPart);
        }
        log.info("T1P3数据插入完毕");
    }

    @Override
    public void t2p1Import(Long projectId, List<String> years, List<T2p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T2p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T2P1数据，项目ID:" + projectId);
        // 删除原有数据
        t2p1Mapper.delete(Wrappers.<T2p1>lambdaQuery().eq(T2p1::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T2p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T2P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T2p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t2p1Mapper.batchInsert(insertPart);
        }
        log.info("T2P1数据插入完毕");
    }

    @Override
    public void t2p2Import(Long projectId, List<String> years, List<T2p2> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T2p2::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T2P2数据，项目ID:" + projectId);
        // 删除原有数据
        t2p2Mapper.delete(Wrappers.<T2p2>lambdaQuery().eq(T2p2::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T2p2>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T2P2数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T2p2> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t2p2Mapper.batchInsert(insertPart);
        }
        log.info("T2P2数据插入完毕");
    }

    @Override
    public void t3p1Import(Long projectId, List<String> years, List<T3p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T3p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T3P1数据，项目ID:" + projectId);
        // 删除原有数据
        t3p1Mapper.delete(Wrappers.<T3p1>lambdaQuery().eq(T3p1::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T3p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T3P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T3p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t3p1Mapper.batchInsert(insertPart);
        }
        log.info("T3P1数据插入完毕");
    }

    @Override
    public void t4p1Import(Long projectId, List<String> years, List<T4p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T4p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T4P1数据，项目ID:" + projectId);
        // 删除原有数据
        t4p1Mapper.delete(Wrappers.<T4p1>lambdaQuery().eq(T4p1::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T4p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T4P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T4p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t4p1Mapper.batchInsert(insertPart);
        }
        log.info("T4P1数据插入完毕");
    }

    @Override
    public void t4p2Import(Long projectId, List<String> years, List<T4p2> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T4p2::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T4P2数据，项目ID:" + projectId);
        // 删除原有数据
        t4p2Mapper.delete(Wrappers.<T4p2>lambdaQuery().eq(T4p2::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T4p2>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T4P2数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T4p2> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t4p2Mapper.batchInsert(insertPart);
        }
        log.info("T4P2数据插入完毕");
    }

    @Override
    public void t4p3Import(Long projectId, List<String> years, List<T4p3> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T4p3::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T4P3数据，项目ID:" + projectId);
        // 删除原有数据
        t4p3Mapper.delete(Wrappers.<T4p3>lambdaQuery().eq(T4p3::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T4p3>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T4P3数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T4p3> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t4p3Mapper.batchInsert(insertPart);
        }
        log.info("T4P3数据插入完毕");
    }

    @Override
    public void t4p4Import(Long projectId, List<String> years, List<T4p4> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T4p4::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T4P4数据，项目ID:" + projectId);
        // 删除原有数据
        t4p4Mapper.delete(Wrappers.<T4p4>lambdaQuery().eq(T4p4::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T4p4>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T4P4数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T4p4> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t4p4Mapper.batchInsert(insertPart);
        }
        log.info("T4P4数据插入完毕");
    }

    @Override
    public void t5p1Import(Long projectId, List<String> years, List<T5p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T5p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T5P1数据，项目ID:" + projectId);
        // 删除原有数据
        t5p1Mapper.delete(Wrappers.<T5p1>lambdaQuery().eq(T5p1::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T5p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T5P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T5p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t5p1Mapper.batchInsert(insertPart);
        }
        log.info("T5P1数据插入完毕");
    }

    @Override
    public void t5p2Import(Long projectId, List<String> years, List<T5p2> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T5p2::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除ProjectYears数据");
        // 删除原有years
        projectYearsMapper.delete(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, projectId)
                .eq(ProjectYears::getTableNo, importEnum.tableNo));
        log.info("删除原有T5P2数据，项目ID:" + projectId);
        // 删除原有数据
        t5p2Mapper.delete(Wrappers.<T5p2>lambdaQuery().eq(T5p2::getProjectId, projectId));
        // 插入年份数据
        List<ProjectYears> projectYearsList = this.projectYears(years, projectId, importEnum.tableNo);
        log.info(String.format("插入年份数据，共%d条", projectYearsList.size()));
        projectYearsMapper.batchInsert(projectYearsList);
        //插入新数据
        List<List<T5p2>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T5P2数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T5p2> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t5p2Mapper.batchInsert(insertPart);
        }
        log.info("T5P2数据插入完毕");
    }

    @Override
    public void t5p3Import(Long projectId, List<String> years, List<T5p3> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T5p3::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        // 删除原有数据
        if (ImportEnum.T5P3D1 == importEnum) {
            log.info("删除原有T5P3数据，项目ID:" + projectId);
            t5p3Mapper.delete(Wrappers.<T5p3>lambdaQuery().eq(T5p3::getProjectId, projectId));
        }
        //插入新数据
        List<List<T5p3>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T5P3数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T5p3> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t5p3Mapper.batchInsert(insertPart);
        }
        log.info("T5P3数据插入完毕");
    }

    @Override
    public void t5p4Import(Long projectId, List<String> years, List<T5p4> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(T5p4::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除原有T5P4数据，项目ID:" + projectId);
        // 删除原有数据
        t5p4Mapper.delete(Wrappers.<T5p4>lambdaQuery().eq(T5p4::getProjectId, projectId));
        //插入新数据
        List<List<T5p4>> partition = Lists.partition(data, 500);
        log.info(String.format("插入T5P4数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<T5p4> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            t5p4Mapper.batchInsert(insertPart);
        }
        log.info("T5P4数据插入完毕");
    }

    @Override
    public void timeFrameImport(Long projectId, List<String> years, List<TimeFrame> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("删除原有timeframe数据");
        // 删除原有数据
        timeFrameMapper.delete(Wrappers.<TimeFrame>lambdaQuery().eq(TimeFrame::getProjectId, projectId));
        //插入新数据
        List<List<TimeFrame>> partition = Lists.partition(data, 500);
        log.info(String.format("插入timeframe数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<TimeFrame> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            timeFrameMapper.batchInsert(insertPart);
        }
        log.info("timeframe数据插入完毕");
    }

    @Override
    public void simT1p1Import(Long projectId, List<String> years, List<SimT1p1> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(SimT1p1::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        // 删除原有数据
        if (ImportEnum.SIM_T1P1D1 == importEnum) {
            log.info("删除原有Simulator T1p1数据，项目ID:" + projectId);
            simT1p1Mapper.delete(Wrappers.<SimT1p1>lambdaQuery().eq(SimT1p1::getProjectId, projectId));
        }
        //插入新数据
        List<List<SimT1p1>> partition = Lists.partition(data, 500);
        log.info(String.format("插入Simulator T1P1数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<SimT1p1> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            simT1p1Mapper.batchInsert(insertPart);
        }
        log.info("Simulator T1P1数据插入完毕");
    }

    @Override
    public void simT1p2Import(Long projectId, List<String> years, List<SimT1p2> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(SimT1p2::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除原有Simulator T1p2数据，项目ID:" + projectId);
        // 删除原有数据
        simT1p2Mapper.delete(Wrappers.<SimT1p2>lambdaQuery().eq(SimT1p2::getProjectId, projectId));
        //插入新数据
        List<List<SimT1p2>> partition = Lists.partition(data, 500);
        log.info(String.format("插入Simulator T1P2数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<SimT1p2> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            simT1p2Mapper.batchInsert(insertPart);
        }
        log.info("Simulator T1P2数据插入完毕");
    }

    @Override
    public void simT1p3Import(Long projectId, List<String> years, List<SimT1p3> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("补充ProjectSeries");
        List<ProjectSeries> seriesList = data.stream().map(SimT1p3::getSeries).distinct().map(name -> {
            ProjectSeries series = new ProjectSeries();
            series.setName(name);
            series.setProjectId(projectId);
            series.setCreateTime(new Date());
            return series;
        }).collect(Collectors.toList());
        projectSeriesMapper.batchInsert(seriesList);
        log.info("删除原有Simulator T1p3数据，项目ID:" + projectId);
        // 删除原有数据
        simT1p3Mapper.delete(Wrappers.<SimT1p3>lambdaQuery().eq(SimT1p3::getProjectId, projectId));
        //插入新数据
        List<List<SimT1p3>> partition = Lists.partition(data, 500);
        log.info(String.format("插入Simulator T1P3数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<SimT1p3> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            simT1p3Mapper.batchInsert(insertPart);
        }
        log.info("Simulator T1P3数据插入完毕");
    }

    @Override
    public void textImport(Long projectId, List<String> years, List<LanguageText> data, ImportEnum importEnum) {
        if (null == projectId) {
            throw new BusinessException("projectID为空");
        }
        if (CollUtil.isEmpty(data)) {
            log.warn("导入数据为空");
            return;
        }
        log.info("删除原有Language Text数据，项目ID:" + projectId);
        // 删除原有数据
        languageTextMapper.delete(Wrappers.<LanguageText>lambdaQuery().eq(LanguageText::getProjectId, projectId));
        //插入新数据
        List<List<LanguageText>> partition = Lists.partition(data, 500);
        log.info(String.format("插入Language Text数据，共%d组，%d条", partition.size(), data.size()));
        int part = 0;
        for (List<LanguageText> insertPart : partition) {
            log.info(String.format("正在插入第[%d/%d]组", ++part, partition.size()));
            languageTextMapper.batchInsert(insertPart);
        }
        log.info("Language Text数据插入完毕");
    }

    private List<ProjectYears> projectYears(List<String> years, Long projectId, int tableNo) {
        return years.stream().map(year -> {
            ProjectYears projectYears = new ProjectYears();
            projectYears.setProjectId(projectId);
            projectYears.setYear(Integer.valueOf(year));
            projectYears.setTableNo(tableNo);
            projectYears.setCreateTime(new Date());
            return projectYears;
        }).collect(Collectors.toList());
    }
}
