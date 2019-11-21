package com.wondersgroup.cloud.paas.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenlong
 * 时间工具类
 */
public class DateUtils {
    private static int zero = 0;

    private static int one = 1;

    private static int two = 2;

    private static int three = 3;

    private static int four = 4;

    public static boolean compareLongTime(long firstTime,long secondTime){
        return firstTime > secondTime;
    }

    /**
     * 获取月份中某一天的时间
     *
     * @param year  年
     * @param month 月
     * @param arg   日时分秒（依次）
     * @return
     */
    public static Date getDateOfMonth(int year, int month, int... arg) {
        Calendar calendar = Calendar.getInstance();
        if (arg.length == zero) {
            calendar.set(year, month - one, zero, zero, zero, zero);
        } else if (arg.length == one) {
            calendar.set(year, month - one, arg[0], zero, zero, zero);
        } else if (arg.length == two) {
            calendar.set(year, month - one, arg[0], arg[1], zero, zero);
        } else if (arg.length == three) {
            calendar.set(year, month - one, arg[0], arg[1], arg[2], zero);
        } else if (arg.length == four) {
            calendar.set(year, month - one, arg[0], arg[1], arg[2], arg[3]);
        }

        return calendar.getTime();
    }

    /**
     * 根据天数推算时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateByDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentDay = calendar.get(Calendar.DATE);
        if (day > 0) {
            calendar.set(Calendar.DATE, currentDay + day);
        } else {
            calendar.set(Calendar.DATE, currentDay - Math.abs(day));
        }
        return calendar.getTime();
    }

    /**
     * 时间戳转化时间
     *
     * @param timestamp 已处理过的时间戳
     * @param pattern   yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long timestamp, String pattern) throws ParseException {
        DateFormat dt = new SimpleDateFormat(pattern);
        return dt.parse(dt.format(new Date(timestamp)));
    }

    /**
     * 时间戳转化时间
     *
     * @param timestamp 已处理过的时间戳
     * @param pattern
     * @return
     */
    public static String longToDateStr(long timestamp, String pattern) {
        DateFormat dt = new SimpleDateFormat(pattern);
        return dt.format(new Date(timestamp));
    }

    /**
     * 字符串转化时间
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Date parseStringToDate(String str, String pattern) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getDateOfMonth(2019, 2, 0, 23, 59, 59));

    }
}
