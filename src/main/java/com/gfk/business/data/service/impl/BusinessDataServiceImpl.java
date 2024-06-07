package com.gfk.business.data.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.controller.form.GroupForm;
import com.gfk.business.data.controller.form.ProductNameForm;
import com.gfk.business.data.mapper.*;
import com.gfk.business.data.mapper.query.*;
import com.gfk.business.data.model.Curve;
import com.gfk.business.data.model.bo.*;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.BusinessDataService;
import com.gfk.common.enums.CurveImportantEnum;
import com.gfk.common.enums.EffectEnum;
import com.gfk.common.enums.PlatformTypeEnum;
import com.gfk.common.enums.SalesDateTotalStrategyEnum;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.util.CalcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class BusinessDataServiceImpl implements BusinessDataService {

    private final SalesDateMapper salesDateMapper;

    private final MediaMapper mediaMapper;

    private final MediaChannelMapper mediaChannelMapper;

    private final HlMapper hlMapper;

    private final GroupRelationMapper groupRelationMapper;

    private final CurveMapper curveMapper;

    /**
     * 查询SalesDate图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public SalesDateChartVO salesDateChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        SalesDateQuery dbQuery = new SalesDateQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        // 获取业务年份
        try {
            String year = salesDateMapper.selectCurrentYear();
            if (StrUtil.isBlank(year)) {
                throw new BusinessException("年份数据有误");
            }
            DateTime last = DateUtil.parse(year, "yyyy");
            DateTime first = DateUtil.offset(last, DateField.YEAR, -1);
            dbQuery.setFirstYear(first.toString("yyyy"));
            dbQuery.setLastYear(last.toString("yyyy"));
        } catch (Exception e) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<SalesDateChartBO> boList = Optional.ofNullable(salesDateMapper.selectChartData(dbQuery)).orElseGet(Collections::emptyList);
        SalesDateChartVO result = new SalesDateChartVO();
        result.setChartData(boList);
        // 计算年度总和
        Map<String, SalesDateChartBO> yearTotal = new HashMap<>(2);
        Map<String, List<SalesDateChartBO>> salesYearList = boList.stream().collect(Collectors.groupingBy(SalesDateChartBO::getYear));
        for (Map.Entry<String, List<SalesDateChartBO>> entry : salesYearList.entrySet()) {
            String key = entry.getKey();
            BigDecimal unitTotal = BigDecimal.ZERO;
            BigDecimal valueTotal = BigDecimal.ZERO;
            BigDecimal priceTotal = BigDecimal.ZERO;
            for (SalesDateChartBO chartBO : entry.getValue()) {
                unitTotal = unitTotal.add(chartBO.getUnit());
                valueTotal = valueTotal.add(chartBO.getValue());
                priceTotal = priceTotal.add(chartBO.getPrice());
            }
            SalesDateChartBO chartBO = new SalesDateChartBO();
            chartBO.setYear(key);
            chartBO.setUnit(unitTotal);
            chartBO.setValue(valueTotal);
            chartBO.setPrice(priceTotal);
            yearTotal.put(key, chartBO);
        }
        // 维度转换
        List<SalesDateTotalBO> totalList = new ArrayList<>(3);
        for (SalesDateTotalStrategyEnum strategyEnum : SalesDateTotalStrategyEnum.values()) {
            SalesDateTotalBO bo = new SalesDateTotalBO();
            bo.setName(strategyEnum.name);
            // 找前一年总和
            SalesDateChartBO firstYearBO = yearTotal.computeIfAbsent(dbQuery.getFirstYear(), a -> {
                SalesDateChartBO chartBO = new SalesDateChartBO();
                chartBO.setYear(a);
                chartBO.setUnit(BigDecimal.ZERO);
                chartBO.setValue(BigDecimal.ZERO);
                chartBO.setPrice(BigDecimal.ZERO);
                return chartBO;
            });
            // 找当年总和
            SalesDateChartBO lastYearBO = yearTotal.computeIfAbsent(dbQuery.getLastYear(), a -> {
                SalesDateChartBO chartBO = new SalesDateChartBO();
                chartBO.setYear(a);
                chartBO.setUnit(BigDecimal.ZERO);
                chartBO.setValue(BigDecimal.ZERO);
                chartBO.setPrice(BigDecimal.ZERO);
                return chartBO;
            });
            bo.setFirstYearTotal(strategyEnum.func.apply(firstYearBO));
            bo.setLastYearTotal(strategyEnum.func.apply(lastYearBO));
            bo.setGrowth(CalcUtils.growth(bo.getLastYearTotal(), bo.getFirstYearTotal()));
            totalList.add(bo);
        }
        result.setTotalData(totalList);
        result.setYearFirst(Integer.valueOf(dbQuery.getFirstYear()));
        result.setYearLast(Integer.valueOf(dbQuery.getLastYear()));
        return result;
    }

    /**
     * 查询Spending图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public SpendingChartVO spendingChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        SpendingQuery dbQuery = new SpendingQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        // 获取业务年份
        try {
            String year = mediaMapper.selectCurrentYear();
            if (StrUtil.isBlank(year)) {
                throw new BusinessException("年份数据有误");
            }
            DateTime last = DateUtil.parse(year, "yyyy");
            DateTime first = DateUtil.offset(last, DateField.YEAR, -1);
            dbQuery.setFirstYear(first.toString("yyyy"));
            dbQuery.setLastYear(last.toString("yyyy"));
        } catch (Exception e) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<SpendingChartBO> boList = Optional.ofNullable(mediaMapper.selectSpendingChartData(dbQuery)).orElseGet(Collections::emptyList);
        // 简单计算年度总额
        Map<String, BigDecimal> yearTotal = boList.stream().collect(
                Collectors.groupingBy(
                        SpendingChartBO::getYear,
                        Collectors.reducing(BigDecimal.ZERO, bo -> CalcUtils.safeValue(bo.getValue()), BigDecimal::add)));
        Map<String, List<SpendingChartBO>> platformMap = boList.stream().collect(Collectors.groupingBy(SpendingChartBO::getPlatform));
        SpendingChartVO result = new SpendingChartVO();
        List<SpendingDataVO> dataList = new ArrayList<>(platformMap.size());
        BigDecimal firstTotal = yearTotal.computeIfAbsent(dbQuery.getFirstYear(), a -> BigDecimal.ZERO);
        BigDecimal lastTotal = yearTotal.computeIfAbsent(dbQuery.getLastYear(), a -> BigDecimal.ZERO);
        for (Map.Entry<String, List<SpendingChartBO>> entry : platformMap.entrySet()) {
            SpendingDataVO dataVO = new SpendingDataVO();
            //平台名
            String key = entry.getKey();
            dataVO.setName(key);
            //两年的值
            Map<String, BigDecimal> yearMap = entry.getValue().stream().collect(
                    Collectors.toMap(SpendingChartBO::getYear, SpendingChartBO::getValue, (oldVal, newVal) -> newVal));
            BigDecimal firstYear = yearMap.computeIfAbsent(dbQuery.getFirstYear(), a -> BigDecimal.ZERO);
            BigDecimal lastYear = yearMap.computeIfAbsent(dbQuery.getLastYear(), a -> BigDecimal.ZERO);
            dataVO.setValueFirstYear(firstYear);
            dataVO.setValueLastYear(lastYear);
            dataVO.setGrowth(CalcUtils.growth(lastYear, firstYear));
            dataVO.setPercentageFirstYear(CalcUtils.percentage(firstYear, firstTotal));
            dataVO.setPercentageLastYear(CalcUtils.percentage(lastYear, lastTotal));
            dataList.add(dataVO);
        }
        result.setDataList(dataList);
        result.setFirstTotal(firstTotal);
        result.setLastTotal(lastTotal);
        result.setTotalGrowth(CalcUtils.growth(lastTotal, firstTotal));
        result.setYearFirst(Integer.valueOf(dbQuery.getFirstYear()));
        result.setYearLast(Integer.valueOf(dbQuery.getLastYear()));
        return result;
    }

    /**
     * 查询Considering图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public List<ConsideringChartVO> consideringChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        ConsideringQuery dbQuery = new ConsideringQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        List<ConsideringBO> boList = Optional.ofNullable(mediaChannelMapper.selectConsideringData(dbQuery)).orElseGet(Collections::emptyList);
        List<ConsideringChartVO> currentList = boList.stream().map(bo -> {
            ConsideringChartVO current = new ConsideringChartVO();
            current.setName(bo.getName());
            current.setIsCurrent(true);
            current.setRoi(Optional.ofNullable(bo.getRoiLast()).orElse(BigDecimal.ZERO));
            current.setSalesContribution(Optional.ofNullable(bo.getSalesLast()).orElse(BigDecimal.ZERO));
            current.setSpending(Optional.ofNullable(bo.getSpendingLast()).orElse(BigDecimal.ZERO));
            return current;
        }).collect(Collectors.toList());
        List<ConsideringChartVO> preList = boList.stream().map(bo -> {
            ConsideringChartVO pre = new ConsideringChartVO();
            pre.setName(bo.getName());
            pre.setIsCurrent(false);
            pre.setRoi(Optional.ofNullable(bo.getRoiFirst()).orElse(BigDecimal.ZERO));
            pre.setSalesContribution(Optional.ofNullable(bo.getSalesFirst()).orElse(BigDecimal.ZERO));
            pre.setSpending(Optional.ofNullable(bo.getSpendingFirst()).orElse(BigDecimal.ZERO));
            return pre;
        }).collect(Collectors.toList());
        List<ConsideringChartVO> result = new ArrayList<>(currentList.size() + preList.size());
        result.addAll(currentList);
        result.addAll(preList);
        return result;
    }

    @Override
    public InvestmentDetailChartVO investmentDetailChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        InvestmentDetailQuery dbQuery = new InvestmentDetailQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        List<InvestmentDetailBO> boList = Optional.ofNullable(mediaChannelMapper.selectInvestmentDetailData(dbQuery)).orElseGet(Collections::emptyList);
        Map<String, List<InvestmentDetailBO>> effectMap = boList.stream().collect(Collectors.groupingBy(InvestmentDetailBO::getEffect));
        List<InvestmentDetailBO> directList = effectMap.computeIfAbsent(EffectEnum.DIRECT.name, k -> Collections.emptyList());
        List<InvestmentDetailBO> haloList = effectMap.computeIfAbsent(EffectEnum.HALO.name, k -> Collections.emptyList());
        List<InvestmentDetailDataVO> dataList = new ArrayList<>(boList.size());
        // direct数据需要按照type2求和
        Map<String, InvestmentDetailBO> directMap = directList.stream().collect(Collectors.groupingBy(InvestmentDetailBO::getType2, Collectors.reducing(InvestmentDetailBO.zeroIdentity(), (a, b) -> {
            b.setSalesFirst(CalcUtils.safeValue(b.getSalesFirst()).add(CalcUtils.safeValue(a.getSalesFirst())));
            b.setSalesLast(CalcUtils.safeValue(b.getSalesLast()).add(CalcUtils.safeValue(a.getSalesLast())));
            b.setSalesUsdFirst(CalcUtils.safeValue(b.getSalesUsdFirst()).add(CalcUtils.safeValue(a.getSalesUsdFirst())));
            b.setSalesUsdLast(CalcUtils.safeValue(b.getSalesUsdLast()).add(CalcUtils.safeValue(a.getSalesUsdLast())));
            return b;
        })));
        List<InvestmentDetailDataVO> directVoList = directMap.values().stream().map(direct -> {
            InvestmentDetailDataVO vo = new InvestmentDetailDataVO();
            vo.setType1(this.getType1(direct));
            vo.setType2(direct.getType2());
            vo.setSalesFirst(direct.getSalesFirst());
            vo.setSalesLast(direct.getSalesLast());
            vo.setSalesUsdFirst(direct.getSalesUsdFirst());
            vo.setSalesUsdLast(direct.getSalesUsdLast());
            vo.setGrowth(CalcUtils.growth(vo.getSalesUsdLast(), vo.getSalesUsdFirst()));
            return vo;
        }).collect(Collectors.toList());
        dataList.addAll(directVoList);
        // halo数据直接映射VO
        List<InvestmentDetailDataVO> haloVoList = haloList.stream().map(halo -> {
            InvestmentDetailDataVO vo = new InvestmentDetailDataVO();
            vo.setType2(halo.getType2() + "-" + halo.getLine());
            vo.setType1(this.getType1(halo));
            vo.setSalesFirst(halo.getSalesFirst());
            vo.setSalesLast(halo.getSalesLast());
            vo.setSalesUsdFirst(halo.getSalesUsdFirst());
            vo.setSalesUsdLast(halo.getSalesUsdLast());
            vo.setGrowth(CalcUtils.growth(vo.getSalesUsdLast(), vo.getSalesUsdFirst()));
            return vo;
        }).collect(Collectors.toList());
        dataList.addAll(haloVoList);
        Map<String, List<InvestmentDetailDataVO>> type1Map = dataList.stream().collect(Collectors.groupingBy(InvestmentDetailDataVO::getType1));
        List<InvestmentDetailPctVO> firstPctList = new ArrayList<>();
        List<InvestmentDetailPctVO> lastPctList = new ArrayList<>();
        type1Map.forEach((name, value) -> {
            InvestmentDetailPctVO firstVO = new InvestmentDetailPctVO();
            firstVO.setName(name);
            firstVO.setValue(value.stream().map(InvestmentDetailDataVO::getSalesUsdFirst)
                    .reduce(BigDecimal.ZERO, (a, b) -> CalcUtils.safeValue(a).add(CalcUtils.safeValue(b))));
            firstPctList.add(firstVO);
            InvestmentDetailPctVO lastVO = new InvestmentDetailPctVO();
            lastVO.setName(name);
            lastVO.setValue(value.stream().map(InvestmentDetailDataVO::getSalesUsdLast)
                    .reduce(BigDecimal.ZERO, (a, b) -> CalcUtils.safeValue(a).add(CalcUtils.safeValue(b))));
            lastPctList.add(lastVO);
        });
        dataList.sort(Comparator.comparing(InvestmentDetailDataVO::getType1).thenComparing(InvestmentDetailDataVO::getType2));
        InvestmentDetailChartVO result = new InvestmentDetailChartVO();
        result.setDataList(dataList);
        result.setFirstPctList(firstPctList);
        result.setLastPctList(lastPctList);
        return result;
    }

    @Override
    public PerformanceAnnuallyChartVO performanceAnnuallyChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        PerformanceAnnuallyQuery dbQuery = new PerformanceAnnuallyQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        // 获取业务年份
        try {
            String year = mediaMapper.selectCurrentYear();
            if (StrUtil.isBlank(year)) {
                throw new BusinessException("年份数据有误");
            }
            DateTime last = DateUtil.parse(year, "yyyy");
            DateTime first = DateUtil.offset(last, DateField.YEAR, -1);
            dbQuery.setFirstYear(first.toString("yyyy"));
            dbQuery.setLastYear(last.toString("yyyy"));
        } catch (Exception e) {
            throw new BusinessException("年份数据有误");
        }
        // 查询数据
        List<PerformanceAnnuallyChartBO> boList = Optional.ofNullable(mediaMapper.selectPerformanceAnnuallyData(dbQuery)).orElseGet(Collections::emptyList);
        // 简单计算年度总额
        Map<String, BigDecimal> yearTotal = boList.stream().collect(
                Collectors.groupingBy(
                        PerformanceAnnuallyChartBO::getYear,
                        Collectors.reducing(BigDecimal.ZERO, bo -> CalcUtils.safeValue(bo.getValue()), BigDecimal::add)));
        Map<String, List<PerformanceAnnuallyChartBO>> groupMap = boList.stream().collect(Collectors.groupingBy(PerformanceAnnuallyChartBO::getName));
        PerformanceAnnuallyChartVO result = new PerformanceAnnuallyChartVO();
        List<PerformanceGroupVO> dataList = new ArrayList<>(groupMap.size());
        BigDecimal firstTotal = yearTotal.computeIfAbsent(dbQuery.getFirstYear(), a -> BigDecimal.ZERO);
        BigDecimal lastTotal = yearTotal.computeIfAbsent(dbQuery.getLastYear(), a -> BigDecimal.ZERO);
        for (Map.Entry<String, List<PerformanceAnnuallyChartBO>> entry : groupMap.entrySet()) {
            PerformanceGroupVO dataVO = new PerformanceGroupVO();
            //平台名
            String key = entry.getKey();
            dataVO.setName(key);
            //两年的值
            Map<String, BigDecimal> yearMap = entry.getValue().stream().collect(
                    Collectors.toMap(PerformanceAnnuallyChartBO::getYear, PerformanceAnnuallyChartBO::getValue, (oldVal, newVal) -> newVal));
            BigDecimal firstYear = yearMap.computeIfAbsent(dbQuery.getFirstYear(), a -> BigDecimal.ZERO);
            BigDecimal lastYear = yearMap.computeIfAbsent(dbQuery.getLastYear(), a -> BigDecimal.ZERO);
            dataVO.setValueFirstYear(firstYear);
            dataVO.setValueLastYear(lastYear);
            dataVO.setGrowth(CalcUtils.growth(lastYear, firstYear));
            dataList.add(dataVO);
        }
        result.setYearFirst(Integer.valueOf(dbQuery.getFirstYear()));
        result.setYearLast(Integer.valueOf(dbQuery.getLastYear()));
        result.setList(dataList);
        result.setTotalGrowth(CalcUtils.growth(lastTotal, firstTotal));
        return result;
    }

    /**
     * 查询Performance 详情图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public PerformanceDetailChartVO performanceDetailChart(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        Integer firstYear = 2021;
        Integer lastYear = 2022;
        PerformanceDetailQuery dbQuery = new PerformanceDetailQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        dbQuery.setYearFirst(firstYear);
        dbQuery.setYearLast(lastYear);
        List<PerformanceDetailBO> boList = Optional.ofNullable(mediaChannelMapper.selectPerformanceDetail(dbQuery)).orElseGet(Collections::emptyList);
        Map<String, List<PerformanceDetailBO>> nameGroups = boList.stream().collect(Collectors.groupingBy(PerformanceDetailBO::getName));
        List<PerformanceDetailDataVO> level1List = nameGroups.entrySet().stream().map(entry -> {
            PerformanceDetailDataVO detailDataVO = new PerformanceDetailDataVO();
            detailDataVO.setName(entry.getKey());
            Map<String, PerformanceDetailBO> effectMap = entry.getValue().stream().collect(Collectors.toMap(PerformanceDetailBO::getEffect, Function.identity(), (oldVal, newVal) -> newVal));
            PerformanceDetailBO directBo = effectMap.computeIfAbsent(EffectEnum.DIRECT.name, k -> new PerformanceDetailBO());
            PerformanceDetailBO haloBo = effectMap.computeIfAbsent(EffectEnum.HALO.name, k -> new PerformanceDetailBO());
            detailDataVO.setRoiFirst(directBo.getRoi());
            detailDataVO.setRoiLast(directBo.getRoiLast());
            detailDataVO.setRoiHaloFirst(haloBo.getRoi());
            detailDataVO.setRoiHaloLast(haloBo.getRoiLast());
            return detailDataVO;
        }).sorted(Comparator.comparing(PerformanceDetailDataVO::getName)
                .thenComparing(PerformanceDetailDataVO::getLevel)).collect(Collectors.toList());
        //TODO level2的逻辑
        PerformanceDetailChartVO result = new PerformanceDetailChartVO();
        result.setYearFirst(firstYear);
        result.setYearLast(lastYear);
        result.setDataList(level1List);
        return result;
    }

    /**
     * 查询Product Group 图表数据
     *
     * @param query 查询条件
     * @return 图表展示数据
     */
    @Override
    public ProductGroupChartVO productGroupChart(GroupForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        if (StrUtil.isBlank(query.getProduct())) {
            throw new BusinessException("产品不得为空");
        }
        if (StrUtil.isBlank(query.getGroup())) {
            throw new BusinessException("Group不得为空");
        }
        Integer firstYear = 2021;
        Integer lastYear = 2022;
        BigDecimal thousand = new BigDecimal("1000.00");
        ProductGroupQuery dbQuery = new ProductGroupQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        dbQuery.setGroup(query.getGroup());
        dbQuery.setFirstYear(firstYear);
        dbQuery.setLastYear(lastYear);
        ProductGroupBO groupBO = Optional.ofNullable(mediaChannelMapper.selectProductGroup(dbQuery)).orElseGet(ProductGroupBO::empty);
        TwoYearsValue groupImpBO = Optional.ofNullable(mediaMapper.selectProductGroupImp(dbQuery)).orElseGet(TwoYearsValue::empty);
        ProductGroupChartVO result = new ProductGroupChartVO();
        result.setProduct(query.getProduct());
        result.setGroup(query.getGroup());
        result.setYearFirst(firstYear);
        result.setYearLast(lastYear);
        //Spending
        ProductGroupDataVO spending = new ProductGroupDataVO();
        spending.setName("Spending");
        spending.setValueFirstYear(groupBO.getSpendingFirst());
        spending.setValueLastYear(groupBO.getSpendingLast());
        spending.setGrowth(CalcUtils.growth(spending.getValueLastYear(), spending.getValueFirstYear()));
        //Impression K
        ProductGroupDataVO impression = new ProductGroupDataVO();
        impression.setName("Impression K");
        impression.setValueFirstYear(groupImpBO.getFirstYear().divide(thousand, RoundingMode.HALF_UP));
        impression.setValueLastYear(groupImpBO.getLastYear().divide(thousand, RoundingMode.HALF_UP));
        impression.setGrowth(CalcUtils.growth(impression.getValueLastYear(), impression.getValueFirstYear()));
        //CPM
        ProductGroupDataVO cpm = new ProductGroupDataVO();
        cpm.setName("CPM");
        cpm.setValueFirstYear(CalcUtils.safeDivide(spending.getValueFirstYear(), impression.getValueFirstYear(), 2));
        cpm.setValueLastYear(CalcUtils.safeDivide(spending.getValueLastYear(), impression.getValueLastYear(), 2));
        cpm.setGrowth(CalcUtils.growth(cpm.getValueLastYear(), cpm.getValueFirstYear()));
        //Sales Contribution K$
        ProductGroupDataVO contribution = new ProductGroupDataVO();
        contribution.setName("Sales Contribution K$");
        contribution.setValueFirstYear(groupBO.getSalesContributionFirst().divide(thousand, RoundingMode.HALF_UP));
        contribution.setValueLastYear(groupBO.getSalesContributionLast().divide(thousand, RoundingMode.HALF_UP));
        contribution.setGrowth(CalcUtils.growth(contribution.getValueLastYear(), contribution.getValueFirstYear()));
        //Effectiveness $/K Imp
        ProductGroupDataVO effectiveness = new ProductGroupDataVO();
        effectiveness.setName("Effectiveness $/K Imp");
        effectiveness.setValueFirstYear(CalcUtils.safeDivide(contribution.getValueFirstYear(), impression.getValueFirstYear(), 2));
        effectiveness.setValueLastYear(CalcUtils.safeDivide(contribution.getValueLastYear(), impression.getValueLastYear(), 2));
        effectiveness.setGrowth(CalcUtils.growth(effectiveness.getValueLastYear(), effectiveness.getValueFirstYear()));

        result.setDataList(Arrays.asList(spending, impression, cpm, contribution, effectiveness));
        return result;
    }

    /**
     * 查询ROI年度对比图表数据
     *
     * @param query 查询条件
     * @return 两年的ROI总和数据对比
     */
    @Override
    public List<RoiAnnuallyTotalVO> roiAnnuallyTotalChart(GroupForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        if (StrUtil.isBlank(query.getProduct())) {
            throw new BusinessException("产品不得为空");
        }
        if (StrUtil.isBlank(query.getGroup())) {
            throw new BusinessException("Group不得为空");
        }
        Integer firstYear = 2021;
        Integer lastYear = 2022;
        ProductGroupQuery dbQuery = new ProductGroupQuery();
        dbQuery.setProjectId(query.getProjectId());
        dbQuery.setProduct(query.getProduct());
        dbQuery.setGroup(query.getGroup());
        dbQuery.setFirstYear(firstYear);
        dbQuery.setLastYear(lastYear);
        TwoYearsValue groupBO = Optional.ofNullable(mediaChannelMapper.selectRoiAnnuallyTotal(dbQuery)).orElseGet(TwoYearsValue::empty);
        return Arrays.asList(new RoiAnnuallyTotalVO(firstYear, groupBO.getFirstYear())
                , new RoiAnnuallyTotalVO(lastYear, groupBO.getLastYear()));
    }

    /**
     * 查询Curve表格数据
     *
     * @param query 查询条件
     * @return curve数据List
     */
    @Override
    public List<CurveVO> curveList(ProductNameForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        if (StrUtil.isBlank(query.getName())) {
            throw new BusinessException("产品Name不得为空");
        }
        List<Curve> curves = Optional.ofNullable(curveMapper.selectList(Wrappers.<Curve>lambdaQuery().eq(Curve::getName, query.getName()))).orElseGet(Collections::emptyList);
        return curves.stream()
                .map(CurveVO::of)
                .filter(vo -> null != vo.getX())
                .sorted(Comparator.comparing(CurveVO::getX))
                .collect(Collectors.toList());
    }

    /**
     * 查询Impression图表数据
     *
     * @param query 查询条件
     * @return Impression图表
     */
    @Override
    public ImpressionDetailChartVO impressionDetailChart(ProductNameForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        if (StrUtil.isBlank(query.getName())) {
            throw new BusinessException("产品Name不得为空");
        }
        String dbName = query.getName().replace("_ret", "");
        final BigDecimal THOUSAND = new BigDecimal("1000.00");
        List<ImpressionBO> impressionBOList = Optional.ofNullable(mediaMapper.selectImpression(query.getProjectId(), dbName)).orElseGet(Collections::emptyList);
        Map<String, Curve> curveMap = Optional.ofNullable(
                        curveMapper.selectList(Wrappers.<Curve>lambdaQuery()
                                .eq(Curve::getProjectId, query.getProjectId())
                                .eq(Curve::getName, query.getName())
                                .eq(Curve::getIsImportant, true)
                                .in(Curve::getImportantLabel, Arrays.asList(CurveImportantEnum.MAX_MARGIN.name, CurveImportantEnum.MAX_ROI.name))))
                .orElseGet(Collections::emptyList)
                .stream().collect(
                        Collectors.toMap(Curve::getImportantLabel,
                                Function.identity(),
                                (oldVal, newVal) -> newVal));
        BigDecimal maxMargin = curveMap.containsKey(CurveImportantEnum.MAX_MARGIN.name)
                ? curveMap.get(CurveImportantEnum.MAX_MARGIN.name).getY()
                : BigDecimal.ZERO;
        BigDecimal maxRoi = curveMap.containsKey(CurveImportantEnum.MAX_ROI.name)
                ? curveMap.get(CurveImportantEnum.MAX_ROI.name).getY()
                : BigDecimal.ZERO;
        List<ImpressionDetailDataVO> dataList = impressionBOList.stream().map(ImpressionDetailDataVO::of).collect(Collectors.toList());
        for (ImpressionDetailDataVO vo : dataList) {
            vo.setMaxMargin(maxMargin);
            vo.setMaxRoi(maxRoi);
            if (null != vo.getImpression()) {
                vo.setImpression(vo.getImpression().divide(THOUSAND, RoundingMode.HALF_UP));
            }
        }
        ImpressionDetailChartVO result = new ImpressionDetailChartVO();
        result.setTitle(query.getName());
        result.setDataList(dataList);
        return result;
    }

    /**
     * SalesDate导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transformSalesDate(String taskId) {
        salesDateMapper.truncateSalesDate();
        return salesDateMapper.transformImport(taskId);
    }

    /**
     * Media导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transformMedia(String taskId) {
        mediaMapper.truncateMedia();
        return mediaMapper.transformImport(taskId);
    }

    /**
     * Media Channel导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transformMediaChannel(String taskId) {
        mediaChannelMapper.truncateMediaChannel();
        return mediaChannelMapper.transformImport(taskId);
    }

    /**
     * HL导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transformHlAndGroupRelation(String taskId) {
        if (null == taskId) {
            log.warn("HL数据处理错误，taskId为空");
            return;
        }
        hlMapper.truncateHl();
        groupRelationMapper.truncateGroupRelation();
        int i = hlMapper.transformImport(taskId);
        log.info("主表数据导入成功，共导入{}条数据", i);
        log.info("开始提取关联关系");
        int groupCount = groupRelationMapper.extractRelation(taskId);
        log.info("关联关系提取成功，共{}条数据", groupCount);
    }

    /**
     * curve导入数据最终处理
     * 中间表数据转换至业务表
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transformCurve(String taskId) {
        if (null == taskId) {
            log.warn("Curve导入数据最终处理");
            return 0;
        }
        curveMapper.truncateCurve();
        int commonCount = curveMapper.transformImport(taskId);
        int importantCount = curveMapper.transformImportantImport(taskId);
        return commonCount + importantCount;
    }

    private String getType1(InvestmentDetailBO bo) {
        if (null == bo || null == bo.getEffect()) {
            return null;
        }
        if (EffectEnum.HALO.name.equals(bo.getEffect())) {
            return PlatformTypeEnum.HALO.type1;
        }
        return PlatformTypeEnum.getType1(bo.getType2().toLowerCase());
    }
}
