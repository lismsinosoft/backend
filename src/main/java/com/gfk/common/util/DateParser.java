package com.gfk.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 用来处理首页环图显示日期的工具类
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class DateParser {

    public static String monthDate(Date date){
        int day = DateUtil.dayOfMonth(date);
        DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
        return df.format(date) + " " + day + "th";
    }

    public static String monthDate(String dateStr) {
        DateTime parseDate = DateUtil.parse(dateStr, "yyyy-MM-dd");
        return monthDate(parseDate);
    }

    public static String quarterDate(Date date){
        DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
        DateTime start = DateUtil.beginOfQuarter(date);
        if(DateUtil.isSameMonth(date, start)){
            return df.format(start);
        }
        return df.format(start) + "-" + df.format(date);
    }

    public static String quarterDate(String dateStr){
        DateTime parseDate = DateUtil.parse(dateStr, "yyyy-MM-dd");
        return quarterDate(parseDate);
    }

    public static String yearDate(Date date){
        DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
        DateTime start = DateUtil.beginOfYear(date);
        if(DateUtil.isSameMonth(date, start)){
            return df.format(start);
        }
        return df.format(start) + "-" + df.format(date);
    }

    public static String yearDate(String dateStr){
        DateTime parseDate = DateUtil.parse(dateStr, "yyyy-MM-dd");
        return yearDate(parseDate);
    }
}
