package com.arch.utils;

import com.arch.constants.ArchConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Created by shishoufeng on 2019/12/11.
 * <p>
 * desc : 时间 相关操作 工具类
 */
public final class DateUtils {


    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    private static SimpleDateFormat getDefaultFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(pattern);
        }
        return simpleDateFormat;
    }


    /**
     * Milliseconds to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis  The milliseconds.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the formatted time string
     */
    public static String millis2String(long millis, @NonNull final String pattern) {
        return millis2String(millis, getDateFormat(pattern));
    }

    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * Formatted time string to the milliseconds.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, getDefaultFormat());
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time    The formatted time string.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final String pattern) {
        return string2Millis(time, getDateFormat(pattern));
    }

    /**
     * Formatted time string to the milliseconds.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Formatted time string to the date.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date string2Date(final String time) {
        return string2Date(time, getDefaultFormat());
    }

    /**
     * Formatted time string to the date.
     *
     * @param time    The formatted time string.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final String pattern) {
        return string2Date(time, getDateFormat(pattern));
    }

    /**
     * Formatted time string to the date.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date to the formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param date The date.
     * @return the formatted time string
     */
    public static String date2String(final Date date) {
        return date2String(date, getDefaultFormat());
    }

    /**
     * Date to the formatted time string.
     *
     * @param date    The date.
     * @param pattern The pattern of date format, such as yyyy/MM/dd HH:mm
     * @return the formatted time string
     */
    public static String date2String(final Date date, @NonNull final String pattern) {
        return getDateFormat(pattern).format(date);
    }

    /**
     * Date to the formatted time string.
     *
     * @param date   The date.
     * @param format The format.
     * @return the formatted time string
     */
    public static String date2String(final Date date, @NonNull final DateFormat format) {
        return format.format(date);
    }

    /**
     * Date to the milliseconds.
     *
     * @param date The date.
     * @return the milliseconds
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * Milliseconds to the date.
     *
     * @param millis The milliseconds.
     * @return the date
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }



    /**
     * Return whether it is today.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, getDefaultFormat()));
    }

    /**
     * Return whether it is today.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time, @NonNull final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * Return whether it is today.
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * Return whether it is today.
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + ArchConstants.DataUnit.DAY;
    }

    /**
     * Return whether it is leap year.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, getDefaultFormat()));
    }

    /**
     * Return whether it is leap year.
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time, @NonNull final DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * Return whether it is leap year.
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * Return whether it is leap year.
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * Return whether it is leap year.
     *
     * @param year The year.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }


    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 计算时间差  比如  一天前   一个小时前
     *
     * @param milliseconds
     * @return
     */
    public static String getTimeString(long milliseconds) {
        try {
            StringBuffer result = new StringBuffer();
            long time = System.currentTimeMillis() - (milliseconds * 1000);
            long mill = (long) Math.ceil(time / 1000);//秒前
            long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
            long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
            long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

            if (day - 1 > 0 && day - 1 < 30) {
                result.append(day + "天");
            } else if (day - 1 >= 30) {
                result.append(Math.round((day - 1) / 30) + "个月");
            } else if (hour - 1 > 0) {
                if (hour >= 24) {
                    result.append("1天");
                } else {
                    result.append(hour + "小时");
                }
            } else if (minute - 1 > 0) {
                if (minute == 60) {
                    result.append("1小时");
                } else {
                    result.append(minute + "分钟");
                }
            } else if (mill - 1 > 0) {
                if (mill == 60) {
                    result.append("1分钟");
                } else {
                    result.append(mill + "秒");
                }
            } else {
                result.append("刚刚");
            }
            if (!result.toString().equals("刚刚")) {
                result.append("前");
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }





}
