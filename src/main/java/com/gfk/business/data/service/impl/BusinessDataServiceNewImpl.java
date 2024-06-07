package com.gfk.business.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.TypeActForm;
import com.gfk.business.data.controller.form.TypeForm;
import com.gfk.business.data.controller.form.YearForm;
import com.gfk.business.data.mapper.*;
import com.gfk.business.data.model.*;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.BusinessDataServiceNew;
import com.gfk.common.enums.CurveImportantEnum;
import com.gfk.common.enums.ImportEnum;
import com.gfk.common.enums.T2p1ColorEnum;
import com.gfk.common.enums.T5p4FieldsEnum;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.util.CalcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 业务数据 服务层实现
 *
 * @author wzl
 * @version 1.0 2023/03/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessDataServiceNewImpl implements BusinessDataServiceNew {

    private final BigDecimal HUNDRED = new BigDecimal("100.00");

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

    /**
     * 查询Spending图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public T1p1VO t1p1Chart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T1P1.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<T1p1> boList = Optional.ofNullable(t1p1Mapper.selectList(Wrappers.<T1p1>lambdaQuery()
                .eq(T1p1::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T1p1::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T1p1VO result = new T1p1VO();
        Map<String, List<T1p1>> nameMap = boList.stream().collect(Collectors.groupingBy(T1p1::getType1));
        List<T1p1VO.T1p1ChartData> chartDataList = nameMap.values().stream().map(t1p1s -> {
            T1p1VO.T1p1ChartData chartData = new T1p1VO.T1p1ChartData();
            t1p1s.forEach(chartData::addOneYearData);
            return chartData;
        }).sorted(Comparator.comparing(vo -> vo.getName().toLowerCase())).collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        return result;
    }

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public T1p2VO t1p2Chart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 查询数据
        List<T1p2> boList = Optional.ofNullable(t1p2Mapper.selectList(Wrappers.<T1p2>lambdaQuery()
                .eq(T1p2::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T1p2::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        Map<String, List<T1p2>> monthCounter = new HashMap<>();
        for (T1p2 t1p2 : boList) {
            String month = DateUtil.format(t1p2.getPeriod(), "yyyy-MM");
            List<T1p2> monthData = monthCounter.computeIfAbsent(month, key -> new ArrayList<>());
            monthData.add(t1p2);
        }
        List<T1p2VO.T1p2ChartVO> chartList = boList.stream().map(t1p2 -> {
            T1p2VO.T1p2ChartVO chartVO = new T1p2VO.T1p2ChartVO();
            chartVO.setDate(t1p2.getPeriod());
            chartVO.setUnit(t1p2.getUnits());
            chartVO.setPrice(t1p2.getPrice());
            List<T1p2> monthData = monthCounter.get(DateUtil.format(t1p2.getPeriod(), "yyyy-MM"));
            chartVO.setYear(monthData.size() > 1
                    ? DateUtil.format(t1p2.getPeriod(), "yyyy-MM-dd")
                    : DateUtil.format(t1p2.getPeriod(), "yyyy-MM"));
            return chartVO;
        }).sorted(Comparator.comparing(T1p2VO.T1p2ChartVO::getDate)).collect(Collectors.toList());
        T1p2VO result = new T1p2VO();
        result.setChartData(chartList);
        return result;
    }

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public T1p3VO t1p3Chart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T1P3.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<T1p3> boList = Optional.ofNullable(t1p3Mapper.selectList(Wrappers.<T1p3>lambdaQuery()
                .eq(T1p3::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T1p3::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T1p3VO result = new T1p3VO();
        Map<String, List<T1p3>> nameMap = boList.stream().collect(Collectors.groupingBy(T1p3::getOverall));
        List<T1p3VO.T1p3ChartData> chartDataList = nameMap.values().stream().map(t1p1s -> {
            T1p3VO.T1p3ChartData chartData = new T1p3VO.T1p3ChartData();
            t1p1s.forEach(chartData::addOneYearData);
            return chartData;
        }).collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        return result;
    }

    /**
     * 查询Investment Detail图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public T2VO t2Chart(YearForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> t2p1Years = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T2P1.tableNo));

        List<ProjectYears> t2p2Years = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T2P2.tableNo));
        if (CollUtil.isEmpty(t2p1Years) || CollUtil.isEmpty(t2p2Years)) {
            throw new BusinessException("年份数据有误");
        }
        if (null == query.getYear()) {
            Integer maxYear = t2p1Years.stream().map(ProjectYears::getYear).max(Comparator.comparing(Function.identity())).orElse(0);
            query.setYear(maxYear);
        }
        List<Integer> years = Arrays.asList(query.getYear() - 1, query.getYear());
        T2VO result = new T2VO();
        // 查询t2p1数据
        List<T2p1> p1BoList = Optional.ofNullable(t2p1Mapper.selectList(Wrappers.<T2p1>lambdaQuery()
                .eq(T2p1::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T2p1::getSeries, query.getProduct())
                .in(T2p1::getYear, years))).orElseGet(Collections::emptyList);
        Map<Integer, List<T2p1>> p1YearMap = p1BoList.stream().collect(Collectors.groupingBy(T2p1::getYear));
        p1YearMap.forEach((key, value) -> {
            List<T2VO.T2P1DataVO> t2p1List = value.stream().map(T2VO.T2P1DataVO::of).sorted(Comparator.comparing(T2VO.T2P1DataVO::getName)).collect(Collectors.toList());
            for (int i = 0; i < t2p1List.size(); i++) {
                T2VO.T2P1DataVO t2P1DataVO = t2p1List.get(i);
                T2p1ColorEnum colorEnum = T2p1ColorEnum.getByNameAndSort(t2P1DataVO.getName(), i);
                t2P1DataVO.setColor(colorEnum.color);
            }
            result.setDynamicT2p1Data(key, t2p1List);
        });
        // 查询t2p2数据
        List<T2p2> p2BoList = Optional.ofNullable(t2p2Mapper.selectList(Wrappers.<T2p2>lambdaQuery()
                .eq(T2p2::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T2p2::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        Map<String, List<T2p2>> nameMap = p2BoList.stream().collect(Collectors.groupingBy(t2p2 -> t2p2.getType1() + "&&&" + t2p2.getType2()));
        List<T2VO.T2P2DataVO> chartDataList = nameMap.values().stream().map(t1p1s -> {
            T2VO.T2P2DataVO chartData = new T2VO.T2P2DataVO();
            t1p1s.forEach(chartData::addOneYearData);
            return chartData;
        }).collect(Collectors.toList());
        result.setDataList(chartDataList);
        // yearMap
        List<Integer> yearList = t2p1Years.stream().map(ProjectYears::getYear).collect(Collectors.toList());
        yearList.addAll(t2p2Years.stream().map(ProjectYears::getYear).collect(Collectors.toList()));
        result.setYearMap(yearList.stream().distinct().sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<T3p1VO> t3p1(YearForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        if (null == query.getYear()) {
            Integer defaultYear = Optional.ofNullable(projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                            .eq(ProjectYears::getProjectId, query.getProjectId())
                            .eq(ProjectYears::getTableNo, ImportEnum.T3P1.tableNo)))
                    .orElseGet(Collections::emptyList)
                    .stream().map(ProjectYears::getYear).max(Comparator.comparing(Function.identity())).orElse(0);
            query.setYear(defaultYear);
        }
        List<Integer> years = Arrays.asList(query.getYear() - 1, query.getYear());
        List<T3p1> t3p1List = Optional.ofNullable(
                        t3p1Mapper.selectList(Wrappers.<T3p1>lambdaQuery()
                                .eq(T3p1::getProjectId, query.getProjectId())
                                .eq(StrUtil.isNotBlank(query.getProduct()), T3p1::getSeries, query.getProduct())
                                .in(T3p1::getYear, years)))
                .orElseGet(Collections::emptyList);
        List<T3p1VO> result = t3p1List.stream().map(t3p1 -> {
            T3p1VO vo = new T3p1VO();
            vo.setName(t3p1.getPlatform());
            vo.setRoi(t3p1.getRoi());
            vo.setSalesContribution(t3p1.getDriven());
            vo.setSpending(t3p1.getSpending());
            vo.setIsCurrent(Objects.equals(t3p1.getYear(), query.getYear()));
            return vo;
        }).collect(Collectors.toList());
        BigDecimal maxSpending = BigDecimal.ZERO;
        for (T3p1VO vo : result) {
            if (null != vo.getSpending() && vo.getSpending().compareTo(maxSpending) > 0) {
                maxSpending = vo.getSpending();
            }
        }
        for (T3p1VO vo : result) {
            BigDecimal spendingPct = CalcUtils.percentage(vo.getSpending(), maxSpending);
            if (null != spendingPct) {
                vo.setSize(spendingPct.max(BigDecimal.TEN));
            }
        }
        result.sort(Comparator.comparing(vo -> vo.getName().toLowerCase()));
        return result;
    }

    @Override
    public T4p1VO t4p1Chart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T4P1.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<T4p1> boList = Optional.ofNullable(t4p1Mapper.selectList(Wrappers.<T4p1>lambdaQuery()
                .eq(T4p1::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T4p1::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T4p1VO result = new T4p1VO();
        Map<String, List<T4p1>> nameMap = boList.stream().collect(Collectors.groupingBy(T4p1::getType2));
        List<T4p1VO.T4p1ChartData> chartDataList = nameMap.values().stream().map(t4p1s -> {
            T4p1VO.T4p1ChartData chartData = new T4p1VO.T4p1ChartData();
            t4p1s.forEach(chartData::addOneYearData);
            return chartData;
        }).collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        List<String> types = t4p3Mapper.distinctTypes(query.getProjectId(), query.getProduct());
        result.setType2(types);
        return result;
    }

    @Override
    public T4p2VO t4p2Chart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T4P2.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<T4p2> boList = Optional.ofNullable(t4p2Mapper.selectList(Wrappers.<T4p2>lambdaQuery()
                .eq(T4p2::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T4p2::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T4p2VO result = new T4p2VO();
        Map<String, List<T4p2>> nameMap = boList.stream().collect(Collectors.groupingBy(t4p2 -> t4p2.getType1() + "-" + t4p2.getType2()));
        List<T4p2VO.T4p2ChartData> chartDataList = nameMap.values().stream().map(t4p2s -> {
            T4p2VO.T4p2ChartData chartData = new T4p2VO.T4p2ChartData();
            t4p2s.forEach(chartData::addOneYearData);
            return chartData;
        }).sorted(Comparator.comparing(T4p2VO.T4p2ChartData::getName)).collect(Collectors.toList());
        List<String> actOn = boList.stream().map(T4p2::getActOn).distinct().collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setActOnMap(actOn);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public T4p3VO t4p3Chart(TypeForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T4P3.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        String type2Display = "";
        if (StrUtil.isBlank(query.getType2())) {
            T4p2 name = this.getDefaultT4p2(query);
            query.setType2(name.getType2());
            type2Display = name.getType1() + "-" + name.getType2();
        }
        // 查询数据
        List<T4p3> boList = Optional.ofNullable(t4p3Mapper.selectList(Wrappers.<T4p3>lambdaQuery()
                .eq(T4p3::getProjectId, query.getProjectId())
                .eq(T4p3::getType2, query.getType2())
                .eq(StrUtil.isNotBlank(query.getProduct()), T4p3::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T4p3VO result = new T4p3VO();
        Map<String, List<T4p3>> nameMap = boList.stream().collect(Collectors.groupingBy(T4p3::getType3));
        List<T4p3VO.T4p3ChartData> chartDataList = nameMap.values().stream().map(t4p3s -> {
            T4p3VO.T4p3ChartData chartData = new T4p3VO.T4p3ChartData();
            t4p3s.forEach(chartData::addOneYearData);
            return chartData;
        }).collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setType2(type2Display);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public T4p4VO t4p4Chart(TypeForm query) {
        // 获取业务年份
        List<ProjectYears> projectYears = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T4P4.tableNo));
        if (CollUtil.isEmpty(projectYears)) {
            throw new BusinessException("年份数据有误");
        }
        String type2Display = "";
        if (StrUtil.isBlank(query.getType2())) {
            T4p2 name = this.getDefaultT4p2(query);
            query.setType2(name.getType2());
            type2Display = name.getType1() + "-" + name.getType2();
        }
        // 查询数据
        List<T4p4> boList = Optional.ofNullable(t4p4Mapper.selectList(Wrappers.<T4p4>lambdaQuery()
                .eq(T4p4::getProjectId, query.getProjectId())
                .eq(T4p4::getType2, query.getType2())
                .eq(StrUtil.isNotBlank(query.getProduct()), T4p4::getSeries, query.getProduct()))).orElseGet(Collections::emptyList);
        T4p4VO result = new T4p4VO();
        Map<String, List<T4p4>> nameMap = boList.stream().collect(Collectors.groupingBy(t4p4 -> t4p4.getType2() + "-" + t4p4.getType3()));
        List<T4p4VO.T4p4ChartData> chartDataList = nameMap.values().stream()
                .filter(l -> l.stream().anyMatch(t4p4 -> Objects.nonNull(t4p4.getRoi())))
                .map(t4p4s -> {
                    T4p4VO.T4p4ChartData chartData = new T4p4VO.T4p4ChartData();
                    t4p4s.forEach(chartData::addOneYearData);
                    return chartData;
                })
                .sorted(Comparator.comparing(T4p4VO.T4p4ChartData::getName))
                .collect(Collectors.toList());
        List<String> actOn = boList.stream().map(T4p4::getActOn).distinct().collect(Collectors.toList());
        result.setChartData(chartDataList);
        result.setType2(type2Display);
        result.setActOnMap(actOn);
        result.setYearMap(projectYears.stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public T5p1VO t5p1Chart(TypeActForm query) {
        // 获取业务年份
        List<ProjectYears> t5p1Years = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T5P1.tableNo));

        if (CollUtil.isEmpty(t5p1Years)) {
            throw new BusinessException("年份数据有误");
        }
        T5p1VO result = new T5p1VO();
        // 查询t5p1数据
        List<T5p1> boList = Optional.ofNullable(t5p1Mapper.selectList(Wrappers.<T5p1>lambdaQuery()
                .eq(T5p1::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T5p1::getSeries, query.getProduct())
                .eq(T5p1::getType1, query.getType1())
                .eq(T5p1::getType2, query.getType2())
                .eq(T5p1::getActOn, query.getActOn()))).orElseGet(Collections::emptyList);
        List<T5p1VO.T5p1ChartData> chartData = boList.stream().collect(Collectors.groupingBy(T5p1::getName)).values().stream().map(t5p1s -> {
            T5p1VO.T5p1ChartData t5p1VO = new T5p1VO.T5p1ChartData();
            t5p1s.forEach(t5p1VO::addOneYearData);
            return t5p1VO;
        }).sorted(Comparator.comparing(T5p1VO.T5p1ChartData::getSort)).collect(Collectors.toList());
        result.setChartData(chartData);
        // yearMap
        List<Integer> yearList = t5p1Years.stream().map(ProjectYears::getYear).collect(Collectors.toList());
        yearList.addAll(t5p1Years.stream().map(ProjectYears::getYear).collect(Collectors.toList()));
        result.setYearMap(yearList.stream().distinct().sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public T5p2VO t5p2Chart(TypeActForm query) {
        // 获取业务年份
        List<ProjectYears> t5p2Years = projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                .eq(ProjectYears::getProjectId, query.getProjectId())
                .eq(ProjectYears::getTableNo, ImportEnum.T5P2.tableNo));

        if (CollUtil.isEmpty(t5p2Years)) {
            throw new BusinessException("年份数据有误");
        }
        T5p2VO result = new T5p2VO();
        T5p2VO.T5p2ChartData t5p2VO = new T5p2VO.T5p2ChartData();
        // 查询t5p2数据
        List<T5p2> boList = Optional.ofNullable(t5p2Mapper.selectList(Wrappers.<T5p2>lambdaQuery()
                .eq(T5p2::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T5p2::getSeries, query.getProduct())
                .eq(T5p2::getType1, query.getType1())
                .eq(T5p2::getType2, query.getType2())
                .eq(T5p2::getActOn, query.getActOn()))).orElseGet(Collections::emptyList);
        boList.forEach(t5p2VO::addOneYearData);
        result.setChartData(Collections.singletonList(t5p2VO));
        // yearMap
        List<Integer> yearList = t5p2Years.stream().map(ProjectYears::getYear).collect(Collectors.toList());
        yearList.addAll(t5p2Years.stream().map(ProjectYears::getYear).collect(Collectors.toList()));
        result.setYearMap(yearList.stream().distinct().sorted().collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<T5p3> t5p3Chart(TypeActForm query) {
        List<T5p3> curves = Optional.ofNullable(
                t5p3Mapper.selectList(Wrappers.<T5p3>lambdaQuery()
                        .eq(T5p3::getProjectId, query.getProjectId())
                        .eq(T5p3::getSeries, query.getProduct())
                        .eq(T5p3::getType1, query.getType1())
                        .eq(T5p3::getType2, query.getType2())
                        .eq(T5p3::getActOn, query.getActOn()))).orElseGet(Collections::emptyList);
        List<T5p3> result = new ArrayList<>((curves.size() + 1) / 10);
        int counter = 1;
        for (T5p3 curve : curves) {
            if (curve.getIsImportant() || counter % 10 == 0) {
                curve.setLabel(CurveImportantEnum.getDisplayName(curve.getLabel()));
                result.add(curve);
            }
            ++counter;
        }
        result = result.stream().filter(r -> Objects.nonNull(r.getX()))
                .sorted(Comparator.comparing(T5p3::getX))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public T5p4VO t5p4Chart(TypeActForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        List<T5p4> t5p4List = Optional.ofNullable(t5p4Mapper.selectList(Wrappers.<T5p4>lambdaQuery()
                .eq(T5p4::getProjectId, query.getProjectId())
                .eq(StrUtil.isNotBlank(query.getProduct()), T5p4::getSeries, query.getProduct())
                .eq(T5p4::getType1, query.getType1())
                .eq(T5p4::getType2, query.getType2())
                .eq(T5p4::getActOn, query.getActOn()))).orElseGet(Collections::emptyList);
        Map<String, BigDecimal> importantPoints = Optional.ofNullable(t5p3Mapper.selectList(Wrappers.<T5p3>lambdaQuery()
                        .eq(T5p3::getProjectId, query.getProjectId())
                        .eq(StrUtil.isNotBlank(query.getProduct()), T5p3::getSeries, query.getProduct())
                        .eq(T5p3::getType1, query.getType1())
                        .eq(T5p3::getType2, query.getType2())
                        .eq(T5p3::getActOn, query.getActOn())
                        .in(T5p3::getLabel, Arrays.asList(T5p4FieldsEnum.MAX_MARGIN.name, T5p4FieldsEnum.MAX_ROI.name)))).orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(T5p3::getLabel, T5p3::getX, (oldVal, newVal) -> newVal));
        Map<Date, List<T5p4>> periodMap = t5p4List.stream().collect(Collectors.groupingBy(T5p4::getPeriod));
        Set<T5p4FieldsEnum> fieldSet = new HashSet<>();
        List<T5p4VO.T5p4DataVO> dataList = periodMap.entrySet().stream().map(entry -> {
            T5p4VO.T5p4DataVO dataVO = new T5p4VO.T5p4DataVO();
            dataVO.setDate(entry.getKey());
            for (T5p4 t5p4 : entry.getValue()) {
                T5p4FieldsEnum t5p4FieldsEnum = T5p4FieldsEnum.getByName(t5p4.getMetrics());
                if (null != t5p4FieldsEnum) {
                    dataVO.setDynamicT2p1Data(t5p4FieldsEnum, t5p4.getValue());
                    fieldSet.add(t5p4FieldsEnum);
                }
            }
            BigDecimal maxRoiValue = importantPoints.get(T5p4FieldsEnum.MAX_ROI.name);
            if (null != maxRoiValue) {
                fieldSet.add(T5p4FieldsEnum.MAX_ROI);
                dataVO.setMaxRoi(maxRoiValue);
            }
            BigDecimal maxMarginValue = importantPoints.get(T5p4FieldsEnum.MAX_MARGIN.name);
            if (null != maxMarginValue) {
                fieldSet.add(T5p4FieldsEnum.MAX_MARGIN);
                dataVO.setMaxMargin(maxMarginValue);
            }
            return dataVO;
        }).sorted(Comparator.comparing(T5p4VO.T5p4DataVO::getDate)).collect(Collectors.toList());
        T5p4VO result = new T5p4VO();
        if (CollUtil.isNotEmpty(t5p4List)) {
            T5p4 next = t5p4List.iterator().next();
            result.setTitle(next.getVarnm());
        }
        result.setDataList(dataList);
        result.setDynamicField(fieldSet.stream().map(T5p4VO.DynamicFields::new).collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<String> getProduct(Long projectId) {
        return Optional.ofNullable(
                        projectSeriesMapper.selectList(Wrappers.<ProjectSeries>lambdaQuery()
                                .eq(ProjectSeries::getProjectId, projectId)))
                .orElseGet(Collections::emptyList)
                .stream().map(ProjectSeries::getName).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getT2p1Years(Long projectId) {
        if (null == projectId) {
            throw new BusinessException("项目ID不得为空");
        }
        return Optional.ofNullable(projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                        .eq(ProjectYears::getProjectId, projectId)
                        .eq(ProjectYears::getTableNo, ImportEnum.T2P1.tableNo)))
                .orElseGet(Collections::emptyList)
                .stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList());
    }

    @Override
    public List<Integer> getT3p1Years(Long projectId) {
        if (null == projectId) {
            throw new BusinessException("项目ID不得为空");
        }
        return Optional.ofNullable(projectYearsMapper.selectList(Wrappers.<ProjectYears>lambdaQuery()
                        .eq(ProjectYears::getProjectId, projectId)
                        .eq(ProjectYears::getTableNo, ImportEnum.T3P1.tableNo)))
                .orElseGet(Collections::emptyList)
                .stream().map(ProjectYears::getYear).sorted().collect(Collectors.toList());
    }

    @Override
    public List<TreeVO> getT5Filter(Long projectId, String product) {
        if (null == projectId) {
            throw new BusinessException("项目ID不得为空");
        }
        List<T5p1> t5p1List = t5p1Mapper.queryFilter(projectId, product);
        if (CollUtil.isEmpty(t5p1List)) {
            return Collections.emptyList();
        }
        return t5p1List.stream().collect(Collectors.groupingBy(T5p1::getType1)).entrySet().stream().map(type1Entry -> {
            TreeVO type1VO = new TreeVO();
            type1VO.setName(type1Entry.getKey());
            List<TreeVO> type1Child = type1Entry.getValue().stream().collect(Collectors.groupingBy(T5p1::getType2)).entrySet().stream().map(type2Entry -> {
                TreeVO type2VO = new TreeVO();
                type2VO.setName(type2Entry.getKey());
                List<TreeVO> type2Child = type2Entry.getValue().stream().collect(Collectors.groupingBy(T5p1::getActOn)).keySet().stream().map(t5p1s -> {
                    TreeVO actOnVO = new TreeVO();
                    actOnVO.setName(t5p1s);
                    return actOnVO;
                }).sorted(TreeVO::compareTo).collect(Collectors.toList());
                type2VO.setChild(type2Child);
                return type2VO;
            }).sorted(TreeVO::compareTo).collect(Collectors.toList());
            type1VO.setChild(type1Child);
            return type1VO;
        }).sorted(TreeVO::compareTo).collect(Collectors.toList());
    }


    private T4p2 getDefaultT4p2(TypeForm query) {
        return Optional.ofNullable(t4p2Mapper.selectList(Wrappers.<T4p2>lambdaQuery()
                        .eq(T4p2::getProjectId, query.getProjectId())
                        .eq(StrUtil.isNotBlank(query.getProduct()), T4p2::getSeries, query.getProduct()))).orElseGet(Collections::emptyList)
                .stream().min(Comparator.comparing(t4p2 -> t4p2.getType1() + t4p2.getType2())).orElseGet(() -> {
                    T4p2 t4p2 = new T4p2();
                    t4p2.setType1("");
                    t4p2.setType2("");
                    return t4p2;
                });
    }
}
