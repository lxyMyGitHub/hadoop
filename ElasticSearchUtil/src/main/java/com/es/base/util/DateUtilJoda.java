package com.es.base.util;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 以joda-time为基础构建日期工具类
 *
 * @Author liuhui
 * @Updater zzq
 * @Description:时间操作工具类
 * @CodeReviewer:
 */
public class DateUtilJoda {

    /**
     * The Constant DAYMILLI.
     */
    public static final long DAYMILLI = 24 * 60 * 60 * 1000;

    /**
     * The Constant HOURMILLI.
     */
    public static final long HOURMILLI = 60 * 60 * 1000;

    /**
     * The Constant MINUTEMILLI.
     */
    public static final long MINUTEMILLI = 60 * 1000;

    /**
     * The Constant SECONDMILLI.
     */
    public static final long SECONDMILLI = 1000;

    /**
     * The Constant DAY_MINUTE.
     */
    public static final int DAY_MINUTE = 24 * 60;

    /**
     * 时间格式 "yyyyMMddHHmmss".
     */
    public static final String DATETIME_FMT_SHORT = "yyyyMMddHHmmss";

    /**
     * 时间格式 "yyyy-MM-dd HH:mm:ss".
     */
    public static final String DATETIME_FMT_HYPHEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式 "yyyy.MM.dd-HH:mm:ss"
     */
    public static final String DATETIME_FMT_POINT = "yyyy.MM.dd-HH:mm:ss";

    /**
     * 时间格式 "yyyy-MM-dd".
     */
    public static final String DATE_FMT_HYPHEN = "yyyy-MM-dd";

    /**
     * 时间格式 "yyyyMMdd".
     */
    public static final String DATE_FMT_SHORT = "yyyyMMdd";

    /**
     * 时间格式 "HH:mm:ss".
     */
    public static final String TIME_FMT_COLON = "HH:mm:ss";

    /**
     * 时间格式 "HH:mm:ss".
     */
    public static final String TIME_FMT_SHORT = "HHmmss";

    /**
     * 时间格式 Formatter 格式："yyyyMMddHHmmss".
     */
    public static final DateTimeFormatter DT_DATETIME_FMT_SHORT = DateTimeFormat.forPattern(DATETIME_FMT_SHORT);

    /**
     * 时间格式Formatter 格式："yyyy-MM-dd HH:mm:ss".
     */
    public static final DateTimeFormatter DT_DATETIME_FMT_HYPHEN = DateTimeFormat.forPattern(DATETIME_FMT_HYPHEN);

    /**
     * 时间格式Formatter 格式："yyyy.MM.dd-HH:mm:ss".
     */
    public static final DateTimeFormatter DT_DATETIME_FMT_POINT = DateTimeFormat.forPattern(DATETIME_FMT_POINT);

    /**
     * 时间格式 Formatter 格式："yyyy-MM-dd".
     */
    public static final DateTimeFormatter DT_DATE_FMT_HYPHEN = DateTimeFormat.forPattern(DATE_FMT_HYPHEN);

    /**
     * 时间格式Formatter 格式："yyyyMMdd".
     */
    public static final DateTimeFormatter DT_DATE_FMT_SHORT = DateTimeFormat.forPattern(DATE_FMT_SHORT);

    /**
     * 时间格式Formatter 格式："HHmmss".
     */
    public static final DateTimeFormatter DT_TIME_FMT_SHORT = DateTimeFormat.forPattern(TIME_FMT_SHORT);

    /**
     * 两个时间差值计算类型-年.
     */
    public static final int PERIOD_TYPE_YEAR = 0;

    /**
     * 两个时间差值计算类型-月.
     */
    public static final int PERIOD_TYPE_MONTH = 1;

    /**
     * 两个时间差值计算类型-周.
     */
    public static final int PERIOD_TYPE_WEEK = 2;

    /**
     * 两个时间差值计算类型-日.
     */
    public static final int PERIOD_TYPE_DAY = 3;

    /**
     * 两个时间差值计算类型-时.
     */
    public static final int PERIOD_TYPE_HOUR = 4;

    /**
     * 两个时间差值计算类型-分.
     */
    public static final int PERIOD_TYPE_MINUTE = 5;

    /**
     * 两个时间差值计算类型-秒.
     */
    public static final int PERIOD_TYPE_SECOND = 6;

    /**
     * 转换时间格式，将字符串转换成另一种时间格式的字符串,适用于未知类型的转换
     *
     * @param dateTime the date time
     * @param to       the to
     * @return String
     */
    public static String convertDateTimeTo(String dateTime, String to) {
        DateTimeFormatter fromDT = null;
        if (isDate(dateTime, DATETIME_FMT_SHORT)) {
            fromDT = DT_DATETIME_FMT_SHORT;
        } else if (isDate(dateTime, DATETIME_FMT_HYPHEN)) {
            fromDT = DT_DATETIME_FMT_HYPHEN;
        } else if (isDate(dateTime, DATETIME_FMT_POINT)) {
            fromDT = DT_DATETIME_FMT_POINT;
        } else {
            return "";
        }

        DateTimeFormatter toDT = DateTimeFormat.forPattern(to);
        return convertDateTimeTo(dateTime, fromDT, toDT);
    }

    /**
     * 转换时间格式，将一种时间格式的字符串转换成另一种时间格式的字符串.
     *
     * @param dateTime the date time
     * @param from     the from
     * @param to       the to
     * @return String
     */
    public static String convertDateTimeTo(String dateTime, String from, String to) {
        DateTimeFormatter fromDT = DateTimeFormat.forPattern(from);
        DateTimeFormatter toDT = DateTimeFormat.forPattern(to);
        return convertDateTimeTo(dateTime, fromDT, toDT);
    }

    /**
     * 转换时间格式，将一种时间格式的字符串转换成另一种时间格式的字符串.
     *
     * @param dateTime the date time
     * @param from     the from
     * @param to       the to
     * @return string
     */
    public static String convertDateTimeTo(String dateTime, DateTimeFormatter from, DateTimeFormatter to) {
        return formatDateTime(parseDate(dateTime, from), to);
    }

    /**
     * 用指定格式转换成日期类型.
     *
     * @param dateTime the date time
     * @param format   the format
     * @return LocalDateTime
     */
    public static DateTime parseDate(String dateTime, DateTimeFormatter format) {
        DateTime dt = null;
        try {
            dt = DateTime.parse(dateTime, format);
        } catch (Exception e) {
            return null;
        }
        return dt;
    }

    /**
     * 用指定格式转换日期为字符串.
     *
     * @param datetime the datetime
     * @param format   the format
     * @return string
     */
    public static String formatDateTime(DateTime datetime, DateTimeFormatter format) {
        if (datetime != null) {
            return datetime.toString(format);
        }
        return "";
    }

    /**
     * 判断时间是否是指定时间格式.
     *
     * @param dateTime the date time
     * @param format   the format
     * @return true, if is date
     */
    public static boolean isDate(String dateTime, String format) {
        if (dateTime == null || parseDate(dateTime, DateTimeFormat.forPattern(format)) == null) {
            return false;
        }
        return true;
    }

    /**
     * 计算两个日期的间隔，间隔类型包括（年，月，周，日，时，分，秒） 不区分前后日期大小，返回值为正数.
     * 比确定间隔的函数慢一些
     *
     * @param date1      the date 1
     * @param date2      the date 2
     * @param periodType the period type
     * @return int
     */
    public static int getDateTimePeriod(DateTime date1, DateTime date2, int periodType) {
        switch (periodType) {
            case PERIOD_TYPE_DAY:
                return (int) (Math.abs(date2.getMillis() - date1.getMillis()) / DAYMILLI);
            case PERIOD_TYPE_HOUR:
                return (int) (Math.abs(date2.getMillis() - date1.getMillis()) / HOURMILLI);
            case PERIOD_TYPE_MINUTE:
                return (int) (Math.abs(date2.getMillis() - date1.getMillis()) / MINUTEMILLI);
            case PERIOD_TYPE_SECOND:
                return (int) (Math.abs(date2.getMillis() - date1.getMillis()) / SECONDMILLI);
            case PERIOD_TYPE_YEAR:
                return Math.abs(Years.yearsBetween(date1, date2).getYears());
            case PERIOD_TYPE_MONTH:
                return Math.abs(Months.monthsBetween(date1, date2).getMonths());
            case PERIOD_TYPE_WEEK:
                return Math.abs(Weeks.weeksBetween(date1, date2).getWeeks());
            default:
                throw new IllegalArgumentException("没有此日期差值类型，请检查输入：" + periodType);
        }
    }

    /**
     * 计算两个日期的间隔，间隔类型包括（年，月，周，日，时，分，秒） 不区分前后日期大小，返回值为正数.
     * 效率比较低，尽量不要用string类型日期求时间间隔
     *
     * @param date1      the date yyyyMMddHHmmss
     * @param date2      the date yyyyMMddHHmmss
     * @param periodType the period type
     * @return int
     */
    public static int getDateStringPeriod(String date1, String date2, int periodType) {
        return getDateTimePeriod(parseDate(date1, DT_DATETIME_FMT_SHORT), parseDate(date2, DT_DATETIME_FMT_SHORT), periodType);
    }


    /**
     * 计算两个日期的间隔天数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the days between
     */
    public static int getDaysBetween(DateTime dateTime1, DateTime dateTime2) {
        return (int) ((dateTime2.getMillis() - dateTime1.getMillis()) / DAYMILLI);
    }

    /**
     * 计算两个日期的间隔周数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the weeks between
     */
    public static int getWeeksBetween(DateTime dateTime1, DateTime dateTime2) {
        return Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
    }

    /**
     * 计算两个日期的间隔月数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the months between
     */
    public static int getMonthsBetween(DateTime dateTime1, DateTime dateTime2) {
        return Months.monthsBetween(dateTime1, dateTime2).getMonths();
    }

    /**
     * 计算两个日期的间隔年数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the years between
     */
    public static int getYearsBetween(DateTime dateTime1, DateTime dateTime2) {
        return Years.yearsBetween(dateTime1, dateTime2).getYears();
    }

    /**
     * 计算两个日期的间隔小时数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the hours between
     */
    public static int getHoursBetween(DateTime dateTime1, DateTime dateTime2) {
        return (int) ((dateTime2.getMillis() - dateTime1.getMillis()) / HOURMILLI);
    }

    /**
     * 计算两个日期的间隔分钟数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the minute between
     */
    public static int getMinuteBetween(DateTime dateTime1, DateTime dateTime2) {
        return (int) ((dateTime2.getMillis() - dateTime1.getMillis()) / MINUTEMILLI);
    }

    /**
     * 计算两个日期的间隔秒数 负值代表date1比date2大.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the second between
     */
    public static int getSecondBetween(DateTime dateTime1, DateTime dateTime2) {
        return (int) ((dateTime2.getMillis() - dateTime1.getMillis()) / SECONDMILLI);
    }

    /**
     * 添加指定天数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addDays(DateTime dateTime, int inteval) {
        return dateTime.plusDays(inteval);
    }

    /**
     * 添加指定周数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addWeeks(DateTime dateTime, int inteval) {
        return dateTime.plusWeeks(inteval);
    }

    /**
     * 添加指定月数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addMonths(DateTime dateTime, int inteval) {
        return dateTime.plusMonths(inteval);
    }

    /**
     * 添加指定年数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addYears(DateTime dateTime, int inteval) {
        return dateTime.plusYears(inteval);
    }

    /**
     * 添加指定小时数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addHours(DateTime dateTime, int inteval) {
        return dateTime.plusHours(inteval);
    }

    /**
     * 添加指定分钟数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addMinutes(DateTime dateTime, int inteval) {
        return dateTime.plusMinutes(inteval);
    }

    /**
     * 添加指定秒数，正数为当前日期之后，负数为当前日期之前.
     *
     * @param dateTime the date time
     * @param inteval  the inteval
     * @return DateTime
     */
    public static DateTime addSeconds(DateTime dateTime, int inteval) {
        return dateTime.plusSeconds(inteval);
    }

    /**
     * 获取现在时间,时间格式 "yyyyMMddHHmmss".
     *
     * @return String
     */
    public static String getTimeNowString() {
        return new DateTime().toString(DT_DATETIME_FMT_SHORT);
    }

    /**
     * 获取现在时间，指定时间格式.
     *
     * @param format the format
     * @return String
     */
    public static String getTimeNowString(String format) {
        return new DateTime().toString(format);
    }

    /**
     * 获取DateUtil使用的DateTime对象.
     *
     * @param date the date
     * @return String
     */
    public static DateTime getDateTimeInstance(Date date) {
        return new DateTime(date);
    }

    /**
     * 获取DateUtil使用的DateTime对象.时间为当前时间。
     *
     * @return String
     */
    public static DateTime getDateTimeInstance() {
        return new DateTime();
    }

    /**
     * 获取jdk的Date对象.
     *
     * @param dateTime the date
     * @return String
     */
    public static Date getDateInstance(DateTime dateTime) {
        return dateTime.toDate();
    }

    /**
     * 获取jdk的Date对象..时间为当前时间。
     *
     * @return String
     */
    public static Date getDateInstance() {
        return new Date();
    }

    /**
     * 时间比较，time2是否晚于time1 time2>time1 true ; time1>=time2 false ;
     * 耗时较长，不建议使用
     *
     * @param time1 时间格式 "yyyyMMddHHmmss"
     * @param time2 时间格式 "yyyyMMddHHmmss"
     * @return
     */
    public static boolean timeCompare(String time1, String time2) {
        return parseDate(time2, DT_DATETIME_FMT_SHORT).isAfter(parseDate(time1, DT_DATETIME_FMT_SHORT));
    }

    /**
     * 时间比较，time2是否晚于time1 time2>time1 true ; time1>=time2 false ;
     * 比timeSubtractionCompare快2.7倍左右,比timeCompare快16倍左右
     *
     * @param time1 时间格式 "yyyyMMddHHmmss"
     * @param time2 时间格式 "yyyyMMddHHmmss"
     * @return
     */
    public static boolean timeShortSubtractionCompare(String time1, String time2) {
        return (Long.parseLong(time2) - Long.parseLong(time1) > 0);
    }

    /**
     * 时间比较，time2是否晚于time1 time2>time1 true ; time1>=time2 false ;
     * 比timeShortSubtractionCompare慢好多，建议使用yyyyMMddHHmmss日期比较
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean timeSubtractionCompare(String time1, String time2) {

        char[] chars1 = time1.toCharArray();
        char[] chars2 = time2.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (int i = 0; i < chars1.length; i++) {
            if (".".indexOf(chars1[i]) != -1 || ":".indexOf(chars1[i]) != -1 || "-".indexOf(chars1[i]) != -1
                    || " ".indexOf(chars1[i]) != -1) {
                continue;
            }
            stringBuffer.append(chars1[i]);

        }
        for (int i = 0; i < chars2.length; i++) {
            if (".".indexOf(chars2[i]) != -1 || ":".indexOf(chars2[i]) != -1 || "-".indexOf(chars2[i]) != -1
                    || " ".indexOf(chars1[i]) != -1) {
                continue;
            }
            stringBuffer2.append(chars2[i]);

        }

        return (Long.parseLong(stringBuffer2.toString()) - Long.parseLong(stringBuffer.toString()) > 0);
    }

    /**
     * 获取当天初始时间，时间格式yyyyMMddHHmmss.
     *
     * @param date the date
     * @return the earliest time of day
     */
    public static String getEarliestTimeOfDay(String date) {
        if (StringUtil.isNotEmpty(date) && date.length() >= 8) {
            date = date.substring(0, 8) + "000000";
        }
        return date;
    }

    /**
     * 获取当天最后时间，时间格式yyyyMMddHHmmss.
     *
     * @param date the date
     * @return the earliest time of day
     */
    public static String getLatestTimeOfDay(String date) {
        if (StringUtil.isNotEmpty(date) && date.length() >= 8) {
            date = date.substring(0, 8) + "235959";
        }
        return date;
    }

    /**
     * 按指定格式获取当天初始时间.
     * 比不带格式的耗时较长
     *
     * @param date   the date
     * @param format the format
     * @return the earliest time of day
     */
    public static String getEarliestTimeOfDay(String date, String format) {
        return parseDate(date, DateTimeFormat.forPattern(format)).millisOfDay().withMinimumValue().toString(format);
    }

    /**
     * 按指定格式获取当天初始时间.
     *
     * @param date   the date
     * @param format the format
     * @return the earliest time of day
     */
    public static String getLatestTimeOfDay(String date, String format) {
        return parseDate(date, DateTimeFormat.forPattern(format)).millisOfDay().withMaximumValue().toString(format);
    }
}
