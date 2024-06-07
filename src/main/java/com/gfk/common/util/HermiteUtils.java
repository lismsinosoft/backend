package com.gfk.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 二点三次埃尔米特差值计算工具类
 * 二点三次Hermite差值计算公式：
 *
 * @author wzl
 * @version 1.0 2023/9/13
 */
public class HermiteUtils {
    private HermiteUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Hermite差值，自动一阶导数值计算，求插值
     *
     * @param x0 点1
     * @param f0 点1函数值
     * @param x1 点2
     * @param f1 点2函数值
     * @param x  待插值
     * @return 插值结果
     */
    public static BigDecimal calculate(BigDecimal x0, BigDecimal f0,
                                       BigDecimal x1, BigDecimal f1,
                                       BigDecimal x) {
        if (null == x0 || null == f0 || null == x1 || null == f1 || null == x) {
            return null;
        }
        if (x0.compareTo(x1) == 0) {
            return null;
        }
        // 设置小数位
        x0 = x0.setScale(4, RoundingMode.HALF_UP);
        f0 = f0.setScale(4, RoundingMode.HALF_UP);
        x1 = x1.setScale(4, RoundingMode.HALF_UP);
        f1 = f1.setScale(4, RoundingMode.HALF_UP);
        x = x.setScale(4, RoundingMode.HALF_UP);
        BigDecimal f0d = f0.divide(x0, 4, RoundingMode.HALF_UP);
        BigDecimal f1d = f1.divide(x1, 4, RoundingMode.HALF_UP);
        BigDecimal diff1 = x1.subtract(x0);
        BigDecimal diff2 = x.subtract(x0);
        BigDecimal diff3 = x1.subtract(x);
        BigDecimal term1 = BigDecimal.ONE.add(new BigDecimal("2.0000").multiply(diff2.divide(diff1, RoundingMode.HALF_UP)))
                .multiply(diff3.divide(diff1, RoundingMode.HALF_UP).pow(2)).multiply(f0);
        BigDecimal term2 = BigDecimal.ONE.add(new BigDecimal("2.0000").multiply(diff3.divide(diff1, RoundingMode.HALF_UP)))
                .multiply(diff2.divide(diff1, RoundingMode.HALF_UP).pow(2)).multiply(f1);
        BigDecimal term3 = diff2.multiply(diff3.pow(2)).divide(diff1.pow(2), RoundingMode.HALF_UP).multiply(f0d);
        BigDecimal term4 = diff3.multiply(diff2.pow(2)).divide(diff1.pow(2), RoundingMode.HALF_UP).multiply(f1d);
        return term1.add(term2).add(term3).subtract(term4).setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Hermite差值，带具体一阶导数值计算，求插值
     *
     * @param x0  点1
     * @param f0  点1函数值
     * @param f0d 点1一阶导数值
     * @param x1  点2
     * @param f1  点2函数值
     * @param f1d 点2一阶导数值
     * @param x   待插值
     * @return 插值结果
     */
    public static BigDecimal calculate(BigDecimal x0, BigDecimal f0, BigDecimal f0d,
                                       BigDecimal x1, BigDecimal f1, BigDecimal f1d,
                                       BigDecimal x) {
        if (null == x0 || null == f0 || null == x1 || null == f1 || null == x || null == f0d || null == f1d) {
            return null;
        }
        if (x0.compareTo(x1) == 0) {
            return null;
        }
        // 设置小数位
        x0 = x0.setScale(4, RoundingMode.HALF_UP);
        f0 = f0.setScale(4, RoundingMode.HALF_UP);
        x1 = x1.setScale(4, RoundingMode.HALF_UP);
        f1 = f1.setScale(4, RoundingMode.HALF_UP);
        x = x.setScale(4, RoundingMode.HALF_UP);
        BigDecimal diff1 = x1.subtract(x0);
        BigDecimal diff2 = x.subtract(x0);
        BigDecimal diff3 = x1.subtract(x);
        BigDecimal term1 = BigDecimal.ONE.add(new BigDecimal("2.0000").multiply(diff2.divide(diff1, RoundingMode.HALF_UP)))
                .multiply(diff3.divide(diff1, RoundingMode.HALF_UP).pow(2)).multiply(f0);
        BigDecimal term2 = BigDecimal.ONE.add(new BigDecimal("2.0000").multiply(diff3.divide(diff1, RoundingMode.HALF_UP)))
                .multiply(diff2.divide(diff1, RoundingMode.HALF_UP).pow(2)).multiply(f1);
        BigDecimal term3 = diff2.multiply(diff3.pow(2)).divide(diff1.pow(2), RoundingMode.HALF_UP).multiply(f0d);
        BigDecimal term4 = diff3.multiply(diff2.pow(2)).divide(diff1.pow(2), RoundingMode.HALF_UP).multiply(f1d);
        return term1.add(term2).add(term3).subtract(term4).setScale(4, RoundingMode.HALF_UP);
    }
}
