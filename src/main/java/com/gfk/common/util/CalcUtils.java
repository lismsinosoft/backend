package com.gfk.common.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_UP;

/**
 * 封装一些简单的指标计算方法
 *
 * @author : wzl
 * @version 1.01 2023/02/26
 **/
@Slf4j
public class CalcUtils {

    public static final BigDecimal PERCENT = new BigDecimal("100.00");
    public static final BigDecimal ZERO = new BigDecimal("0.00");

    private CalcUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * 计算同比数据（默认保留两位小数）
     *
     * @param thisYear 当年数值
     * @param lastYear 去年数值
     * @return 同比增长多少(百分比数值)
     */
    public static BigDecimal growth(BigDecimal thisYear, BigDecimal lastYear) {
        return growth(thisYear, lastYear, 2);
    }

    /**
     * 计算同比数据
     *
     * @param thisYear 当年数值
     * @param lastYear 去年数值
     * @param limit    保留的小数位数，-1为默认
     * @return 同比增长多少(百分比数值)
     */
    public static BigDecimal growth(BigDecimal thisYear, BigDecimal lastYear, int limit) {
        if (null == thisYear || null == lastYear) {
            return null;
        }
        if (0 == ZERO.compareTo(lastYear)) {
            return null;
        }
        int calcLimit = limit > 0 ? limit + 1 : 1;
        thisYear = resetCalcScale(thisYear, calcLimit);
        lastYear = resetCalcScale(lastYear, calcLimit);
        BigDecimal result = thisYear
                .divide(lastYear, RoundingMode.HALF_UP)
                .multiply(PERCENT)
                .subtract(PERCENT);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 计算百分比占比数据（默认保留两位小数）
     *
     * @param actual 实际数值
     * @param total  总额数据
     * @return 计算结果(百分比数值)
     */
    public static BigDecimal percentage(BigDecimal actual, BigDecimal total) {
        return percentage(actual, total, 2);
    }

    /**
     * 计算百分比占比数据
     *
     * @param actual 实际数值
     * @param total  总额数据
     * @param limit  保留的小数位数，-1为默认
     * @return 计算结果(百分比数值)
     */
    public static BigDecimal percentage(BigDecimal actual, BigDecimal total, int limit) {
        if (null == actual || null == total) {
            return null;
        }
        if (0 == ZERO.compareTo(total)) {
            return null;
        }
        BigDecimal result = actual.divide(total, HALF_UP).multiply(PERCENT);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 计算差额
     *
     * @param actual 实际值（被减数）
     * @param target 指标
     * @param limit  保留的小数位数，-1为默认
     * @return 计算结果
     */
    public static BigDecimal gap(BigDecimal actual, BigDecimal target, int limit) {
        if (null == actual || null == target) {
            return null;
        }
        BigDecimal result = actual.subtract(target);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 计算差额
     *
     * @param actual 实际值（被减数）
     * @param target 指标
     * @return 计算结果
     */
    public static BigDecimal gap(BigDecimal actual, BigDecimal target) {
        return gap(actual, target, 2);
    }

    /**
     * 计算Run Rate
     * 公式 actual/(target/mthTotal*mtdWorking)
     *
     * @param actual     实际销售
     * @param target     指标
     * @param mthTotal   当前月总工作日天数
     * @param mtdWorking 当前月1日到查询日期的工作日天数
     * @param limit      保留的小数位数，-1为默认
     * @return Run Rate(百分比数值)
     */
    public static BigDecimal runRate(BigDecimal actual, BigDecimal target, BigDecimal mthTotal, BigDecimal mtdWorking, int limit) {
        if (actual == null || null == target) {
            return null;
        }
        if (0 == ZERO.compareTo(target)) {
            return null;
        }
        if (0 == ZERO.compareTo(mthTotal)) {
            return null;
        }
        if (0 == ZERO.compareTo(mtdWorking)) {
            return null;
        }
        BigDecimal result = actual.divide(target.divide(mthTotal, HALF_UP).multiply(mtdWorking), HALF_UP);
        //百分数，乘100%
        result = result.multiply(PERCENT);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 计算Run Rate
     * 公式：achievement * dateFactor
     *
     * @param achievement 达成率
     * @param dateFactor  日期系数（mthTotal/mtdWorking）
     * @param limit       保留的小数位数，-1为默认
     * @return Run Rate(百分比数值)
     */
    public static BigDecimal runRate(BigDecimal achievement, BigDecimal dateFactor, int limit) {
        if (null == achievement || null == dateFactor) {
            return null;
        }
        BigDecimal result = achievement.multiply(dateFactor);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 计算Run Rate
     * 公式 actual/(target/mthTotal*mtdWorking)
     *
     * @param actual     当年数值
     * @param target     去年数值
     * @param mthTotal   当前月总工作日天数
     * @param mtdWorking 当前月1日到查询日期的工作日天数
     * @return Run Rate(百分比数值)
     */
    public static BigDecimal runRate(BigDecimal actual, BigDecimal target, BigDecimal mthTotal, BigDecimal mtdWorking) {
        return runRate(actual, target, mthTotal, mtdWorking, 2);
    }

    /**
     * 计算Run Rate
     * 公式：achievement * dateFactor
     *
     * @param achievement 达成率
     * @param dateFactor  日期系数（mthTotal/mtdWorking）
     * @return Run Rate(百分比数值)
     */
    public static BigDecimal runRate(BigDecimal achievement, BigDecimal dateFactor) {
        return runRate(achievement, dateFactor, 2);
    }

    /**
     * 返回非null的BigDecimal值（进行小数位截取）
     *
     * @param value 可能为空指针的BigDecimal对象
     * @param limit 保留的小数位数，-1为默认
     * @return 如果为null则返回0，否则返回当前值
     */
    public static BigDecimal safeValue(BigDecimal value, int limit) {
        value = null == value ? BigDecimal.ZERO : value;
        if (limit > 0) {
            value = value.setScale(limit, HALF_UP);
        }
        return value;
    }

    /**
     * 返回非null的BigDecimal值
     *
     * @param value 可能为空指针的BigDecimal对象
     * @return 如果为null则返回0，否则返回当前值
     */
    public static BigDecimal safeValue(BigDecimal value) {
        return safeValue(value, -1);
    }

    /**
     * 计算线性回归
     *
     * @param x     x坐标参数
     * @param b     线性回归系数
     * @param a     线性回归常数项
     * @param limit 保留的小数位数，-1为默认
     * @return 线性回归y值
     */
    public static BigDecimal linear(BigDecimal x, BigDecimal a, BigDecimal b, int limit) {
        if (null == x || null == a || null == b) {
            return null;
        }
        BigDecimal y = b.multiply(x).add(a);
        if (limit > 0) {
            y = y.setScale(limit, HALF_UP);
        }
        return y;
    }

    /**
     * 计算线性回归
     *
     * @param x x坐标参数
     * @param a 线性回归系数
     * @param b 线性回归常数项
     * @return 线性回归y值
     */
    public static BigDecimal linear(BigDecimal x, BigDecimal a, BigDecimal b) {
        return linear(x, a, b, 2);
    }

    /**
     * 安全除法 避免出现zero及null
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param limit    保留的小数位数，-1为默认
     * @return 如果为null则返回0，否则返回当前值
     */
    public static BigDecimal safeDivide(BigDecimal dividend, BigDecimal divisor, int limit) {
        if (null == dividend || null == divisor) {
            return null;
        }
        if (0 == ZERO.compareTo(divisor)) {
            return null;
        }
        BigDecimal result = dividend.divide(divisor, HALF_UP);
        if (limit >= 0) {
            result = result.setScale(limit, HALF_UP);
        }
        return result;
    }

    /**
     * 安全除法 避免出现zero及null 无保留小数位
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 如果为null则返回0，否则返回当前值
     */
    public static BigDecimal safeDivide(BigDecimal dividend, BigDecimal divisor) {
        return safeDivide(dividend, divisor, -1);
    }

    /**
     * 通过字符串生成BigDecimal对象
     *
     * @param valueStr 字符串
     * @param limit    保留的小数位数，-1为默认
     * @return 如果解析失败返回null，解析成功返回对应的BigDecimal对象
     */
    public static BigDecimal parseIgnoreException(String valueStr, int limit) {
        if (null == valueStr) {
            return null;
        }
        try {
            BigDecimal value = new BigDecimal(valueStr);
            if (limit > 0) {
                value = value.setScale(limit, HALF_UP);
            }
            return value;
        } catch (Exception e) {
            log.warn("字符串json解析失败", e);
        }
        return null;
    }

    /**
     * 通过字符串生成BigDecimal对象
     *
     * @param valueStr 字符串
     * @return 如果解析失败返回null，解析成功返回对应的BigDecimal对象
     */
    public static BigDecimal parseIgnoreException(String valueStr) {
        return parseIgnoreException(valueStr, -1);
    }

    public static BigDecimal getHundredNumber(BigDecimal value) {
        if (null == value) {
            return null;
        }
        return value.multiply(PERCENT);
    }

    public static BigDecimal getHundredNumber(BigDecimal value, int limit) {
        if (null == value) {
            return null;
        }
        return value.multiply(PERCENT).setScale(limit, RoundingMode.HALF_DOWN);
    }

    private static BigDecimal resetCalcScale(BigDecimal num, int calcScale) {
        if (null == num) {
            return null;
        }
        int scale = num.scale();
        return scale > calcScale ? num : num.setScale(calcScale, RoundingMode.HALF_UP);
    }
}
