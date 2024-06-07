package com.gfk.common.util;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    public static String FORMAT_NORMAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_NORMAL_DATE = "yyyy-MM-dd";
    public static String FORMAT_NORMAL_TIME = "HH:mm:ss";
    public static String FORMAT_HHMM_TIME = "HH:mm";
    public static String FORMAT_FILE_DATE_TIME = "yyyyMMdd_HHmmssSSS";


    public static Timestamp nowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date nowDate() {
        return new Date();
    }

    /**
     *
     * @param time
     * @param formatString
     * @return  秒 s
     */
    public static long getTimestampNumber(String time,String formatString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date date = format.parse(time);
        return date.getTime()/1000;
    }

    public static Timestamp getTimestamp(String time,String formatString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        Date date = format.parse(time);
        return new Timestamp(date.getTime());
    }

    public static String getDataTime(String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(new Date());
    }

    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getYear() {
        return getYear(new Date());
    }

    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    public static String getMonth() {
        return getMonth(new Date());
    }

    public static String getNormalDateString() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE);
        return format.format(new Date());
    }

    public static Date getNormalDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_NORMAL_DATE);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(getNormalDateString(), pos);
        return date;

    }

    public static String getNormalDateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE);
        return format.format(date);
    }

    public static Date getNormalDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE);
        return DateUtil.parse(str);
    }

    /**
     *
     * @param date
     * @param field 单位 Calendar.HOUR ...
     * @param amount    变化量
     * @return
     */
    public static String getNextDateTime(Date date,int field,int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE_TIME);
        return format.format(date);
    }

    /**
     *
     * @param date
     * @param field 单位 Calendar.HOUR ...
     * @param amount    变化量
     * @return
     */
    public static Date getDateNextDateTime(Date date,int field,int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        date = cal.getTime();
        return date;
    }

    /**
     * 获取当天的截止日期 XX:XX 23:59:59
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = cal.getTime();
        return date;
    }
    /**
     * 获取当天的开始日期 XX:XX 00:00:00
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();
        return date;
    }
    /**
     * 设置某天的特定时分
     * @param baseDate
     * @param hourMinuteDate
     * @return
     */
    public static Date setSpecificHourMinuteOfDay(Date baseDate,Date hourMinuteDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(hourMinuteDate);
        cal.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
        baseDate = cal.getTime();
        return baseDate;
    }

    public static String getNormalDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE_TIME);
        return format.format(date);
    }
    public static String getNormalDateTime() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_DATE_TIME);
        return format.format(new Date());
    }

    public static String getNormalTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_NORMAL_TIME);
        return format.format(date);
    }

    public static String getNormalHHMMTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_HHMM_TIME);
        return format.format(date);
    }

    /**
     * 获取日期文件名
     */
    public static final String getDateTimeFileName()
    {
        return DateFormatUtils.format(nowDate(), FORMAT_FILE_DATE_TIME);
    }

    public static void main(String[] args) {
        System.out.println(TimeUtils.getNextDateTime(new Date(),Calendar.HOUR,1));
    }

}
