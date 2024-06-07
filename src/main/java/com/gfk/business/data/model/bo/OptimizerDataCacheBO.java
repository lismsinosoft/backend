package com.gfk.business.data.model.bo;

import cn.hutool.core.collection.CollUtil;
import com.gfk.business.data.model.SimT1p1;
import com.gfk.common.util.HermiteUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wzl
 * @version 1.0 2023/10/15
 */
@Data
@Slf4j
public class OptimizerDataCacheBO {
    private String name;
    private List<SimT1p1> dataList;
    private BigDecimal minData;
    private BigDecimal maxData;
    private int granularity;
    private ConcurrentHashMap<Long, Long> cachedData;

    public OptimizerDataCacheBO(String type1, List<SimT1p1> dataList) {
        this.name = type1;
        if (CollUtil.isNotEmpty(dataList)) {
            this.dataList = dataList;
            this.minData = dataList.get(0).getSpending();
            this.maxData = dataList.get(dataList.size() - 1).getSpending();
            this.cachedData = new ConcurrentHashMap<>(dataList.stream().collect(Collectors.toMap(
                    simT1p1 -> simT1p1.getSpending().longValue(),
                    simT1p1 -> simT1p1.getRevenue().longValue(),
                    (oldVal, newVal) -> newVal)));
            this.granularity = Math.max(1,
                    maxData.subtract(minData)
                            .divide(new BigDecimal("100000.00"), RoundingMode.HALF_UP)
                            .intValue());
        } else {
            this.dataList = new ArrayList<>(0);
            this.minData = BigDecimal.ZERO;
            this.maxData = BigDecimal.ZERO;
            this.granularity = 1;
            this.cachedData = new ConcurrentHashMap<>(0);
        }
    }

    public Long calculateRevenue(Long spending) {
        if (null == spending) {
            return 0L;
        }
        SimT1p1 data1 = null;
        SimT1p1 data2 = null;
        for (int i = 0; i < dataList.size(); i++) {
            SimT1p1 data = dataList.get(i);
            if (data.getSpending().intValue() > spending) {
                // 如果第一个值就取到了，那取不到另一个值，直接按照线性返回
                if (i == 0) {
                    try {
                        BigDecimal multiply = new BigDecimal(spending)
                                .multiply(data.getRevenue().divide(data.getSpending(), RoundingMode.HALF_UP));
                        return multiply.longValue();
                    } catch (Exception e) {
                        log.warn("计算缓存数据收益失败", e);
                        return 0L;
                    }
                } else {
                    data2 = data;
                    data1 = dataList.get(i - 1);
                    break;
                }
            }
        }
        if (null == data1 || null == data2) {
            return 0L;
        }
        BigDecimal revenue = HermiteUtils.calculate(data1.getSpending(), data1.getRevenue(), data2.getSpending(), data2.getRevenue(), new BigDecimal(spending));
        return null != revenue ? revenue.longValue() : 0L;
    }
}
