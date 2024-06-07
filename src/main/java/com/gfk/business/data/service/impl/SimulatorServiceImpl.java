package com.gfk.business.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gfk.business.data.controller.form.OptimizerForm;
import com.gfk.business.data.controller.form.SimulatorCalcForm;
import com.gfk.business.data.controller.form.SimulatorForm;
import com.gfk.business.data.mapper.SimT1p1Mapper;
import com.gfk.business.data.mapper.SimT1p2Mapper;
import com.gfk.business.data.mapper.SimT1p3Mapper;
import com.gfk.business.data.mapper.TimeFrameMapper;
import com.gfk.business.data.model.SimT1p1;
import com.gfk.business.data.model.SimT1p2;
import com.gfk.business.data.model.SimT1p3;
import com.gfk.business.data.model.TimeFrame;
import com.gfk.business.data.model.bo.OptimizerDataCacheBO;
import com.gfk.business.data.model.bo.OptimizerDataContainerBO;
import com.gfk.business.data.model.vo.*;
import com.gfk.business.data.service.SimulatorService;
import com.gfk.common.cache.CacheRepository;
import com.gfk.common.enums.SimulatorLabelEnum;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.ga.OptimizerFitnessBuilder;
import com.gfk.common.ga.OptimizerSolutionConverter;
import com.gfk.common.ga.engine.GAEngine;
import com.gfk.common.ga.engine.core.EngineInvokeOptions;
import com.gfk.common.ga.engine.utils.MinMaxScaler;
import com.gfk.common.key.CacheConstants;
import com.gfk.common.util.CalcUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Simulator 服务层实现
 *
 * @author wzl
 * @version 1.0 2023/08/29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorServiceImpl implements SimulatorService {

    private final TimeFrameMapper timeFrameMapper;

    private final SimT1p1Mapper simT1p1Mapper;

    private final SimT1p2Mapper simT1p2Mapper;

    private final SimT1p3Mapper simT1p3Mapper;

    private final CacheRepository cacheRepository;

    @Override
    public List<TreeVO> getTimeframe(Long projectId, String product) {
        if (null == projectId) {
            throw new BusinessException("项目ID不得为空");
        }
        List<TimeFrame> timeframeList = timeFrameMapper.selectList(Wrappers.<TimeFrame>lambdaQuery().eq(TimeFrame::getProjectId, projectId));
        Map<String, List<String>> typeHierarchy = this.getTypeHierarchy(projectId, product);
        if (CollUtil.isEmpty(timeframeList)) {
            return Collections.emptyList();
        }
        return timeframeList.stream().collect(Collectors.groupingBy(TimeFrame::getFrame1)).entrySet().stream().map(type1Entry -> {
            TreeVO type1VO = new TreeVO();
            type1VO.setName(type1Entry.getKey());
            List<TreeVO> type1Child = type1Entry.getValue().stream().collect(Collectors.groupingBy(TimeFrame::getFrame2)).entrySet().stream().map(type2Entry -> {
                TreeVO type2VO = new TreeVO();
                type2VO.setName(type2Entry.getKey());
                List<String> strings = typeHierarchy.get(type1VO.getName() + type2VO.getName());
                if (CollUtil.isNotEmpty(strings)) {
                    type2VO.setChild(strings.stream().map(type -> {
                        TreeVO type3VO = new TreeVO();
                        type3VO.setName(type);
                        return type3VO;
                    }).sorted(Comparator.comparing(TreeVO::getName)).collect(Collectors.toList()));
                }
                return type2VO;
            }).sorted(Comparator.comparing(TreeVO::getName)).collect(Collectors.toList());
            type1VO.setChild(type1Child);
            return type1VO;
        }).sorted(Comparator.comparing(vo -> vo.getChild().size())).collect(Collectors.toList());
    }

    @Override
    public SimulatorT1p1VO getT1p1(SimulatorForm query) {
        this.validForm(query);
        List<SimT1p1> curves = Optional.ofNullable(
                simT1p1Mapper.selectList(Wrappers.<SimT1p1>lambdaQuery()
                        .eq(SimT1p1::getProjectId, query.getProjectId())
                        .eq(SimT1p1::getSeries, query.getProduct())
                        .eq(StrUtil.isNotBlank(query.getType1()), SimT1p1::getType1, query.getType1())
                        .eq(SimT1p1::getFrame1, query.getFrame1())
                        .eq(SimT1p1::getFrame2, query.getFrame2()))).orElseGet(Collections::emptyList);
        List<SimulatorT1p1VO.SimulatorPointVO> resultList = new ArrayList<>((curves.size() + 1) / 10);
        Set<String> typeMapper = new HashSet<>();
        Set<String> aspectMapper = new HashSet<>();
        int counter = 1;
        for (SimT1p1 curve : curves) {
            if (curve.getIsImportant() || counter % 10 == 0) {
                resultList.add(SimulatorT1p1VO.SimulatorPointVO.of(curve));
                typeMapper.add(curve.getType1());
                aspectMapper.add(curve.getAspect());
            }
            ++counter;
        }
        resultList = resultList.stream().filter(r -> Objects.nonNull(r.getX()))
                .sorted(Comparator.comparing(SimulatorT1p1VO.SimulatorPointVO::getType1)
                        .thenComparing(SimulatorT1p1VO.SimulatorPointVO::getX))
                .collect(Collectors.toList());
        SimulatorT1p1VO result = new SimulatorT1p1VO();
        result.setList(resultList);
        result.setTypeMap(typeMapper);
        result.setAspectMap(aspectMapper);
        return result;
    }

    @Override
    public List<SimulatorT1p2VO> getT1p2(SimulatorForm query) {
        this.validForm(query);
        if (StrUtil.isBlank(query.getType1())) {
            throw new BusinessException("Type 为空");
        }
        return Optional.ofNullable(simT1p2Mapper.selectList(Wrappers.<SimT1p2>lambdaQuery()
                        .eq(SimT1p2::getProjectId, query.getProjectId())
                        .eq(SimT1p2::getSeries, query.getProduct())
                        .eq(SimT1p2::getType1, query.getType1())
                        .eq(SimT1p2::getFrame1, query.getFrame1())
                        .eq(SimT1p2::getFrame2, query.getFrame2()))).orElseGet(Collections::emptyList)
                .stream().map(SimulatorT1p2VO::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<SimulatorT1p3VO> getT1p3(SimulatorForm query) {
        this.validForm(query);
        List<SimT1p3> entityList = Optional.ofNullable(simT1p3Mapper.selectList(Wrappers.<SimT1p3>lambdaQuery()
                .eq(SimT1p3::getProjectId, query.getProjectId())
                .eq(SimT1p3::getSeries, query.getProduct())
                .eq(StrUtil.isNotBlank(query.getType1()), SimT1p3::getType1, query.getType1())
                .eq(SimT1p3::getFrame1, query.getFrame1())
                .eq(SimT1p3::getFrame2, query.getFrame2()))).orElseGet(Collections::emptyList);
        BigDecimal maxRoiCurrent = null;
        BigDecimal maxRoiMinimal = null;
        BigDecimal maxRoiOptimal = null;
        BigDecimal maxRoiGap = null;
        List<SimulatorT1p3VO> result = new ArrayList<>(entityList.size());
        for (SimT1p3 entity : entityList) {
            SimulatorT1p3VO vo = SimulatorT1p3VO.of(entity);
            result.add(vo);
            maxRoiCurrent = this.getMax(maxRoiCurrent, entity.getRoiCurrent());
            maxRoiMinimal = this.getMax(maxRoiMinimal, entity.getRoiMinimal());
            maxRoiOptimal = this.getMax(maxRoiOptimal, entity.getRoiOptimal());
            maxRoiGap = this.getMax(maxRoiGap, entity.getRoiGapNum());
        }
        for (SimulatorT1p3VO vo : result) {
            vo.setRoiCurrentPct(vo.getRoiCurrent().divide(maxRoiCurrent, 2, RoundingMode.HALF_UP));
            vo.setRoiMinimalPct(vo.getRoiMinimal().divide(maxRoiMinimal, 2, RoundingMode.HALF_UP));
            vo.setRoiOptimalPct(vo.getRoiOptimal().divide(maxRoiOptimal, 2, RoundingMode.HALF_UP));
            vo.setRoiGapPct(vo.getRoiGapNum().divide(maxRoiGap, 2, RoundingMode.HALF_UP));
        }
        return result;
    }

    @Override
    public SimulatorListVO getList(SimulatorForm query) {
        this.validForm(query);
        List<SimT1p2> entities = Optional.ofNullable(simT1p2Mapper.selectList(Wrappers.<SimT1p2>lambdaQuery()
                .eq(SimT1p2::getProjectId, query.getProjectId())
                .eq(SimT1p2::getSeries, query.getProduct())
                .eq(SimT1p2::getFrame1, query.getFrame1())
                .eq(SimT1p2::getFrame2, query.getFrame2())
                .eq(SimT1p2::getLabel, SimulatorLabelEnum.CURRENT.label))).orElseGet(Collections::emptyList);

        return this.entityToListResult(entities);
    }

    @Override
    public SimulatorResultVO simulate(SimulatorCalcForm query) {
        this.validForm(query);
        if (CollUtil.isEmpty(query.getItems())) {
            throw new BusinessException("计算参数为空");
        }
        List<SimT1p2> calResult = new ArrayList<>();
        for (SimulatorCalcForm.CalcItem item : query.getItems()) {
            if (StrUtil.isBlank(item.getType1())) {
                throw new BusinessException("传入参数Type1为空");
            }
            if (null == item.getChange()) {
                item.setChange(BigDecimal.ZERO);
            }
            if (null == item.getSpendCurrent()) {
                item.setSpendCurrent(BigDecimal.ZERO);
            }
            BigDecimal changedSpending = item.getSpendCurrent();
            Long changedRevenue = item.getRevenueCurrent().longValue();
            if (BigDecimal.ZERO.compareTo(item.getChange()) != 0) {
                changedSpending = item.getSpendCurrent().multiply(
                                BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP)
                                        .add(item.getChange().divide(CalcUtils.PERCENT, 2, RoundingMode.HALF_UP)))
                        .setScale(2, RoundingMode.HALF_UP);

                BigDecimal dataRange = new BigDecimal("2000.00");
                List<SimT1p1> rangeSample = Optional.ofNullable(simT1p1Mapper.selectList(Wrappers.<SimT1p1>lambdaQuery()
                                .eq(SimT1p1::getProjectId, query.getProjectId())
                                .eq(SimT1p1::getSeries, query.getProduct())
                                .eq(SimT1p1::getType1, item.getType1())
                                .eq(SimT1p1::getFrame1, query.getFrame1())
                                .eq(SimT1p1::getFrame2, query.getFrame2()).last("limit 2")))
                        .orElseGet(Collections::emptyList).stream().filter(Objects::nonNull).collect(Collectors.toList());
                if (2 == rangeSample.size()) {
                    SimT1p1 pre = rangeSample.get(0);
                    SimT1p1 last = rangeSample.get(1);
                    if (null != pre.getSpending() && null != last.getSpending()) {
                        dataRange = pre.getSpending().subtract(last.getSpending()).abs();
                    }
                }
                BigDecimal max = changedSpending.add(dataRange);
                BigDecimal min = changedSpending.subtract(dataRange);
                List<SimT1p1> curves = Optional.ofNullable(
                                simT1p1Mapper.selectList(Wrappers.<SimT1p1>lambdaQuery()
                                        .eq(SimT1p1::getProjectId, query.getProjectId())
                                        .eq(SimT1p1::getSeries, query.getProduct())
                                        .eq(SimT1p1::getType1, item.getType1())
                                        .eq(SimT1p1::getFrame1, query.getFrame1())
                                        .eq(SimT1p1::getFrame2, query.getFrame2())
                                        .gt(SimT1p1::getSpending, min)
                                        .lt(SimT1p1::getSpending, max)
                                        .orderByAsc(SimT1p1::getSpending)))
                        .orElseGet(Collections::emptyList);
                OptimizerDataCacheBO cacheBO = new OptimizerDataCacheBO(item.getType1(), curves);
                changedRevenue = cacheBO.calculateRevenue(changedSpending.longValue());
            }
            SimT1p2 simT1p2 = new SimT1p2();
            simT1p2.setType1(item.getType1());
            simT1p2.setLabel(SimulatorLabelEnum.CURRENT.label);
            simT1p2.setSpend(changedSpending.setScale(1, RoundingMode.HALF_UP));
            simT1p2.setRevenue(new BigDecimal(changedRevenue).setScale(1, RoundingMode.HALF_UP));
            simT1p2.setRoi(this.divideOrZero(simT1p2.getRevenue(), simT1p2.getSpend()));
            simT1p2.setFrame1(query.getFrame1());
            simT1p2.setFrame2(query.getFrame2());
            simT1p2.setSeries(query.getProduct());
            calResult.add(simT1p2);
        }
        SimulatorListVO listVO = this.entityToListResult(calResult);
        SimulatorResultVO result = new SimulatorResultVO();
        BeanUtil.copyProperties(listVO, result);
        Map<String, SimulatorCalcForm.CalcItem> calcItemMap = query.getItems().stream()
                .collect(Collectors.toMap(SimulatorCalcForm.CalcItem::getType1, Function.identity(), (oldVal, newVal) -> newVal));
        BigDecimal maxRoiChange = BigDecimal.ZERO;
        List<SimulatorResultItemVO> resultItems = new ArrayList<>();
        BigDecimal spendingTotalOrigin = BigDecimal.ZERO;
        BigDecimal revenueTotalOrigin = BigDecimal.ZERO;
        for (SimulatorListItemVO item : listVO.getItems()) {
            SimulatorResultItemVO resultItem = new SimulatorResultItemVO();
            BeanUtil.copyProperties(item, resultItem);
            SimulatorCalcForm.CalcItem calcItem = calcItemMap.get(item.getType1());
            if (null != calcItem) {
                resultItem.setSpendingChange(calcItem.getChange());
                if (BigDecimal.ZERO.compareTo(calcItem.getChange()) != 0) {
                    resultItem.setRevenueChange(CalcUtils.growth(resultItem.getRevenueCurrent(), calcItem.getRevenueCurrent()));
                    resultItem.setRoiChange(CalcUtils.growth(resultItem.getRoiCurrent(), calcItem.getRoiCurrent()));
                } else {
                    resultItem.setRevenueChange(BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP));
                    resultItem.setRoiChange(resultItem.getRevenueChange());
                }
                if (resultItem.getRoiChange().abs().compareTo(maxRoiChange) > 0) {
                    maxRoiChange = resultItem.getRoiChange().abs();
                }
                // 顺便求总一下原来的
                spendingTotalOrigin = spendingTotalOrigin.add(calcItem.getSpendCurrent());
                revenueTotalOrigin = revenueTotalOrigin.add(calcItem.getRevenueCurrent());
            }
            resultItems.add(resultItem);
        }
        for (SimulatorResultItemVO resultItem : resultItems) {
            resultItem.setRoiChangePct(this.divideOrZero(resultItem.getRoiChange().abs(), maxRoiChange).abs());
        }
        result.setItems(resultItems);
        result.setSpendingTotalChange(CalcUtils.growth(result.getSpendingTotal(), spendingTotalOrigin));
        result.setRevenueTotalChange(CalcUtils.growth(result.getRevenueTotal(), revenueTotalOrigin));
        BigDecimal roiOrigin = this.divideOrZero(revenueTotalOrigin, spendingTotalOrigin);
        result.setRoiTotalChange(CalcUtils.growth(listVO.getRoiTotal(), roiOrigin));
        result.setRoiTotalChangePct(BigDecimal.ZERO);
        return result;
    }

    @Override
    public SimulatorResultVO optimization(OptimizerForm query) {
        SimulatorForm form = new SimulatorForm();
        BeanUtil.copyProperties(query, form);
        this.validForm(form);
        if (null != query.getTarget() && query.getTarget() < 0) {
            throw new BusinessException("目标金额需大于0");
        }
        List<OptimizerForm.TypeLimit> typeLimits = query.getTypeLimits();
        List<OptimizerDataCacheBO> dataCaches = new ArrayList<>();
        // 数据检索
        for (OptimizerForm.TypeLimit typeLimit : typeLimits) {
            if (null == typeLimit.getType1()) {
                throw new BusinessException("存在type1为空的参数");
            }
            if (null == typeLimit.getMin() && null == typeLimit.getMax()) {
                throw new BusinessException(typeLimit.getType1() + "限制条件为空");
            }
            List<SimT1p1> curves = Optional.ofNullable(
                            simT1p1Mapper.selectList(Wrappers.<SimT1p1>lambdaQuery()
                                    .eq(SimT1p1::getProjectId, query.getProjectId())
                                    .eq(SimT1p1::getSeries, query.getProduct())
                                    .eq(SimT1p1::getType1, typeLimit.getType1())
                                    .eq(SimT1p1::getFrame1, query.getFrame1())
                                    .eq(SimT1p1::getFrame2, query.getFrame2())
                                    .gt(null != typeLimit.getMin(), SimT1p1::getSpending, typeLimit.getMin())
                                    .lt(null != typeLimit.getMax(), SimT1p1::getSpending, typeLimit.getMax())
                                    .orderByAsc(SimT1p1::getSpending)))
                    .orElseGet(Collections::emptyList);
            if (CollUtil.isNotEmpty(curves)) {
                OptimizerDataCacheBO cacheBO = new OptimizerDataCacheBO(typeLimit.getType1(), curves);
                dataCaches.add(cacheBO);
            }
        }
        // 开始遗传算法计算
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        OptimizerSolutionConverter optimizerSolutionConverter = new OptimizerSolutionConverter();
        OptimizerDataContainerBO container = new OptimizerDataContainerBO(dataCaches, query.getTarget());
        optimizerSolutionConverter.setDataContainer(container);
        OptimizerFitnessBuilder fitnessBuilder = new OptimizerFitnessBuilder();
        EngineInvokeOptions options = new EngineInvokeOptions();
        options.setFitnessBuilder(fitnessBuilder);
        options.setEnableMaxLimits(true);
        options.setMaxLimits(2000);
//        if (null != query.getTarget()) {
//            options.setEnableSteadyFitness(true);
//            options.setSteadyFitnessValue(query.getTarget());
//        }

        MinMaxScaler minMaxScaler = new MinMaxScaler(100, false);
        GAEngine gaEngine = new GAEngine(500, executorService);

        OptimizerSolutionConverter.Demo1Solution solution = (OptimizerSolutionConverter.Demo1Solution) gaEngine.generate(
                optimizerSolutionConverter
                , minMaxScaler
                , options);
        List<OptimizerSolutionConverter.SolutionEntity> entities = solution.getEntities();
        List<SimT1p2> calResult = entities.stream().map(entity -> {
            SimT1p2 simT1p2 = new SimT1p2();
            simT1p2.setType1(entity.getName());
            simT1p2.setLabel(SimulatorLabelEnum.CURRENT.label);
            simT1p2.setSpend(new BigDecimal(entity.getSpending()).setScale(2, RoundingMode.HALF_UP));
            simT1p2.setRevenue(new BigDecimal(entity.getRevenue()).setScale(2, RoundingMode.HALF_UP));
            simT1p2.setRoi(this.divideOrZero(simT1p2.getRevenue(), simT1p2.getSpend()));
            simT1p2.setFrame1(query.getFrame1());
            simT1p2.setFrame2(query.getFrame2());
            simT1p2.setSeries(query.getProduct());
            return simT1p2;
        }).collect(Collectors.toList());
        SimulatorListVO listVO = this.entityToListResult(calResult);
        SimulatorResultVO result = new SimulatorResultVO();
        BeanUtil.copyProperties(listVO, result);
        Map<String, OptimizerForm.TypeLimit> calcItemMap = query.getTypeLimits().stream()
                .collect(Collectors.toMap(OptimizerForm.TypeLimit::getType1, Function.identity(), (oldVal, newVal) -> newVal));
        BigDecimal maxRoiChange = BigDecimal.ZERO;
        List<SimulatorResultItemVO> resultItems = new ArrayList<>();
        BigDecimal spendingTotalOrigin = BigDecimal.ZERO;
        BigDecimal revenueTotalOrigin = BigDecimal.ZERO;
        for (SimulatorListItemVO item : listVO.getItems()) {
            SimulatorResultItemVO resultItem = new SimulatorResultItemVO();
            BeanUtil.copyProperties(item, resultItem);
            OptimizerForm.TypeLimit calcItem = calcItemMap.get(item.getType1());
            if (null != calcItem) {
                BigDecimal spendCurrentOrigin = calcItem.getSpendCurrent();
                BigDecimal spendCurrentNew = item.getSpendCurrent();
                resultItem.setSpendingChange(spendCurrentNew.divide(spendCurrentOrigin, 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE).multiply(CalcUtils.PERCENT));
                resultItem.setRevenueChange(CalcUtils.growth(resultItem.getRevenueCurrent(), calcItem.getRevenueCurrent()));
                resultItem.setRoiChange(CalcUtils.growth(resultItem.getRoiCurrent(), calcItem.getRoiCurrent()));
                if (null != resultItem.getRoiChange() &&
                        resultItem.getRoiChange().abs().compareTo(maxRoiChange) > 0) {
                    maxRoiChange = resultItem.getRoiChange().abs();
                }
                // 顺便求总一下原来的
                spendingTotalOrigin = spendingTotalOrigin.add(calcItem.getSpendCurrent());
                revenueTotalOrigin = revenueTotalOrigin.add(calcItem.getRevenueCurrent());
            }
            resultItems.add(resultItem);
        }
        for (SimulatorResultItemVO resultItem : resultItems) {
            if (null != resultItem.getRoiChange()) {
                resultItem.setRoiChangePct(this.divideOrZero(resultItem.getRoiChange().abs(), maxRoiChange).abs());
            } else {
                resultItem.setRoiChangePct(BigDecimal.ZERO);
            }

        }
        result.setItems(resultItems);
        result.setSpendingTotalChange(CalcUtils.growth(result.getSpendingTotal(), spendingTotalOrigin));
        result.setRevenueTotalChange(CalcUtils.growth(result.getRevenueTotal(), revenueTotalOrigin));
        BigDecimal roiOrigin = this.divideOrZero(revenueTotalOrigin, spendingTotalOrigin);
        result.setRoiTotalChange(CalcUtils.growth(listVO.getRoiTotal(), roiOrigin));
        result.setRoiTotalChangePct(BigDecimal.ZERO);
        return result;
    }

    private void validForm(SimulatorForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("projectId为空");
        }
        if (StrUtil.isBlank(query.getProduct())) {
            throw new BusinessException("product为空");
        }
        if (StrUtil.isBlank(query.getFrame1())) {
            throw new BusinessException("TimeFrame1为空");
        }
        if (StrUtil.isBlank(query.getFrame2())) {
            throw new BusinessException("TimeFrame2为空");
        }
    }

    private BigDecimal getMax(BigDecimal max, BigDecimal current) {
        if (null == max || null == current) {
            max = current;
            return max;
        }
        if (current.compareTo(max) > 0) {
            max = current;
        }
        return max;
    }

    private BigDecimal divideOrZero(BigDecimal divide, BigDecimal divisor) {
        divide = divide.setScale(2, RoundingMode.HALF_UP);
        divisor = divisor.setScale(2, RoundingMode.HALF_UP);
        return BigDecimal.ZERO.compareTo(divisor) != 0
                ? divide.divide(divisor, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    private SimulatorListVO entityToListResult(List<SimT1p2> entities) {
        BigDecimal spendingMax = BigDecimal.ZERO;
        BigDecimal revenueMax = BigDecimal.ZERO;
        BigDecimal roiMax = BigDecimal.ZERO;
        BigDecimal spendingTotal = BigDecimal.ZERO;
        BigDecimal revenueTotal = BigDecimal.ZERO;
        BigDecimal hundred = new BigDecimal("100.00");
        List<SimulatorListItemVO> items = new ArrayList<>();
        // 数据转换
        for (SimT1p2 entity : entities) {
            BigDecimal spend = null != entity.getSpend() ? entity.getSpend() : BigDecimal.ZERO;
            BigDecimal revenue = null != entity.getRevenue() ? entity.getRevenue() : BigDecimal.ZERO;
            BigDecimal roi = null != entity.getRoi() ? entity.getRoi() : BigDecimal.ZERO;
            if (spend.compareTo(spendingMax) > 0) {
                spendingMax = spend;
            }
            if (revenue.compareTo(revenueMax) > 0) {
                revenueMax = revenue;
            }
            if (roi.compareTo(roiMax) > 0) {
                roiMax = roi;
            }
            SimulatorListItemVO itemVO = new SimulatorListItemVO();
            itemVO.setType1(entity.getType1());
            itemVO.setSpendCurrent(spend);
            itemVO.setRevenueCurrent(revenue);
            itemVO.setRoiCurrent(roi);
            items.add(itemVO);
            spendingTotal = spendingTotal.add(spend);
            revenueTotal = revenueTotal.add(revenue);
        }

        SimulatorListVO result = new SimulatorListVO();
        result.setSpendingTotal(spendingTotal);
        result.setRevenueTotal(revenueTotal);
        BigDecimal roiTotal = this.divideOrZero(revenueTotal, spendingTotal);
        result.setRoiTotal(roiTotal);
        result.setRoiTotalPct(this.divideOrZero(roiTotal, roiMax));

        BigDecimal spendMaxShare = this.divideOrZero(spendingMax, spendingTotal);
        BigDecimal revenueMaxShare = this.divideOrZero(revenueMax, revenueTotal);
        // 结果加工
        for (SimulatorListItemVO item : items) {
            BigDecimal spendShare = this.divideOrZero(item.getSpendCurrent(), spendingTotal);
            item.setSpendingShare(spendShare.multiply(hundred));
            BigDecimal revenueShare = this.divideOrZero(item.getRevenueCurrent(), revenueTotal);
            item.setRevenueShare(revenueShare.multiply(hundred));
            item.setSpendingSharePct(this.divideOrZero(spendShare, spendMaxShare));
            item.setRevenueSharePct(this.divideOrZero(revenueShare, revenueMaxShare));
            item.setRoiSharePct(this.divideOrZero(item.getRoiCurrent(), roiMax));
        }
        result.setItems(items);
        return result;
    }


    @SuppressWarnings("unchecked")
    private Map<String, List<String>> getTypeHierarchy(Long projectId, String product) {
        Map<String, List<String>> result = new HashMap<>();
        try {
            Object o = cacheRepository.get(CacheConstants.SIMULATOR_FILTER_TYPE_HIERARCHY + projectId);
            List<SimT1p1> types = null;
            if (null != o) {
                types = (List<SimT1p1>) o;
            } else {
                log.info("未找到type-hierarchy缓存");
                List<SimT1p1> dbList = simT1p1Mapper.getTypeHierarchy(projectId);
                if (null == dbList) {
                    dbList = new ArrayList<>();
                }
                cacheRepository.set(CacheConstants.SIMULATOR_FILTER_TYPE_HIERARCHY + projectId, dbList, 2, TimeUnit.HOURS);
                types = dbList;
            }
            Map<String, List<SimT1p1>> typesMap = types.stream()
                    .filter(simT1p1 -> null == product || product.equals(simT1p1.getSeries()))
                    .collect(Collectors.groupingBy(simT1p1 -> simT1p1.getFrame1() + simT1p1.getFrame2()));
            typesMap.forEach((key, value) -> {
                result.put(key, value.stream().map(SimT1p1::getType1).collect(Collectors.toList()));
            });
            return result;
        } catch (Exception e) {
            log.error("获取timeframe-type层级关系失败", e);
        }
        return result;
    }
}
