package amp.demo.utils;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

/**
 * @author devzl[zliangchn@126.com]
 * @version V1.0
 * @apiNote 日期时间工具类
 * @date 2020/04/02 18:16 星期四
 */
public class DateTimeUtil {
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DEFAULT_DATE_NO_SECOND_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
    private static final DateTimeFormatter DEFAULT_ALL_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_DIAGONAL_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd' 'HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER_OF_DASH = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_FORMATTER_OF_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter DATE_FORMATTER_OF_DIAGONAL = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_FORMATTER_OF_CHINESE = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    private static final DateTimeFormatter TIME_FORMATTER_OF_COLON = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER_OF_CHINESE = DateTimeFormatter.ofPattern("HH时mm分ss秒");
    private static final DateTimeFormatter TIME_FORMATTER_OF_SECOND_TIME = DateTimeFormatter.ofPattern("HH:mm");
    /**
     * 计算模式 对年份进行加减
     */
    public static final String CALC_YEAR = "y";
    /**
     * 计算模式 对月份进行加减
     */
    public static final String CALC_MONTH = "M";
    /**
     * 计算模式 对天数进行加减
     */
    public static final String CALC_DAY = "d";
    /**
     * 计算模式 对小时进行加减
     */
    public static final String CALC_HOUR = "H";
    /**
     * 计算模式 对分钟进行加减
     */
    public static final String CALC_MINUTES = "m";
    /**
     * 计算模式 对秒数进行加减
     */
    public static final String CALC_SECONDS = "s";

    /**
     * 输出格式 1: yyyy-MM-dd HH:mm
     */
    public static final int ONE = 1;
    /**
     * 输出格式 2: yyyy-MM-dd HH:mm:ss
     */
    public static final int TWO = 2;
    /**
     * 输出格式 3: yyyy-MM-ddTHH:mm:ss
     */
    public static final int THREE = 3;
    public static final int FOUR = 4;
    /**
     * 输出格式 5: HH:mm
     */
    public static final int FIVE = 5;

    /**
     * 开始年限
     */
    private static final int FIRST_YEAR = 1900;
    /**
     * 二月
     */
    private static final int FEB = 2;
    private static final int ZERO = 0;
    private static final int TWENTY_NINE = 29;
    private static final int THIRTY = 30;
    private static final int THIRTY_ONE = 31;
    private static final int THIRTY_TWO = 32;

    /**
     * 根据传入的日期、计算模式、相差数，计算新的日期，计算模式不正确则返回null
     *
     * <pre>
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_YEAR, 2) == 20211231
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_YEAR, -2) == 20171231
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_MONTH, 2) == 20200229
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_MONTH, -2) == 20191031
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_DAY, 2) == 20200102
     *     DateTimeUtil.calculateDate("20191231", DateTimeUtil.CALC_DAY, -2) == 20191229
     *     DateTimeUtil.calculateDate("20191231", "a", 2) == null
     *     DateTimeUtil.calculateDate("20191231", "a", -2) == null
     * </pre>
     *
     * @param currentDay     当前日期 yyyyMMdd格式
     * @param model          计算模式 见 {@link #CALC_YEAR}、{@link #CALC_MONTH}、{@link #CALC_DAY}
     * @param intervalNumber 相差数，正数为相加，负数则相减
     *
     * @return String 计算后的新的日期 yyyyMMdd格式
     */
    public static String calculateDate(String currentDay, String model, int intervalNumber) {
        switch (model) {
            case CALC_YEAR:
                return LocalDate.parse(currentDay, DEFAULT_DATE_FORMATTER).plusYears(intervalNumber).format(DEFAULT_DATE_FORMATTER);
            case CALC_MONTH:
                return LocalDate.parse(currentDay, DEFAULT_DATE_FORMATTER).plusMonths(intervalNumber).format(DEFAULT_DATE_FORMATTER);
            case CALC_DAY:
                return LocalDate.parse(currentDay, DEFAULT_DATE_FORMATTER).plusDays(intervalNumber).format(DEFAULT_DATE_FORMATTER);
            default:
                return null;
        }
    }

    /**
     * 根据传入的日期时间、计算模式、相差数，计算新的日期时间，计算模式不正确则返回null<br/>
     *
     * <pre>
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_HOUR, 5) == 20200101045955
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_HOUR, -5) == 20191231185955
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_MINUTES, 5) == 20200101000455
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_MINUTES, -5) == 20191231235455
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_SECONDS, 5) == 20200101000000
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_SECONDS, -5) == 20191231235950
     *     DateTimeUtil.calculateDate("20191231235959", "a", 5) == null
     *     DateTimeUtil.calculateDate("20191231235959", "a", -5) == null
     * </pre>
     *
     * @param currentDateTime 当前日期时间 yyyyMMddHHmmss格式
     * @param model           计算模式 见 {@link #CALC_HOUR}、{@link #CALC_MINUTES}、{@link #CALC_SECONDS}
     * @param intervalNumber  相差数，正数为相加，负数则相减
     *
     * @return String 计算后的新的日期时间 yyyyMMddHHmmss格式
     */
    public static String calculateDateTime(String currentDateTime, String model, int intervalNumber) {
        switch (model) {
            case CALC_HOUR:
                return LocalDateTime.parse(currentDateTime, DEFAULT_DATE_TIME_FORMATTER).plusHours(intervalNumber).format(DEFAULT_DATE_TIME_FORMATTER);
            case CALC_MINUTES:
                return LocalDateTime.parse(currentDateTime, DEFAULT_DATE_TIME_FORMATTER).plusMinutes(intervalNumber).format(DEFAULT_DATE_TIME_FORMATTER);
            case CALC_SECONDS:
                return LocalDateTime.parse(currentDateTime, DEFAULT_DATE_TIME_FORMATTER).plusSeconds(intervalNumber).format(DEFAULT_DATE_TIME_FORMATTER);
            default:
                return null;
        }
    }

    /**
     * 根据传入的时间、计算模式、相差数，计算新的时间，计算模式不正确则返回null<br/>
     *
     * <pre>
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_HOUR, 5) == 20200101045955
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_HOUR, -5) == 20191231185955
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_MINUTES, 5) == 20200101000455
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_MINUTES, -5) == 20191231235455
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_SECONDS, 5) == 20200101000000
     *     DateTimeUtil.calculateDate("20191231235959", DateTimeUtil.CALC_SECONDS, -5) == 20191231235950
     *     DateTimeUtil.calculateDate("20191231235959", "a", 5) == null
     *     DateTimeUtil.calculateDate("20191231235959", "a", -5) == null
     * </pre>
     *
     * @param currentTime     当前时间 HHmmss格式
     * @param model           计算模式 见 {@link #CALC_HOUR}、{@link #CALC_MINUTES}、{@link #CALC_SECONDS}
     * @param intervalNumber  相差数，正数为相加，负数则相减
     *
     * @return String 计算后的新的时间 HHmmss格式
     */
    public static String calculateTime(String currentTime, String model, int intervalNumber) {
        switch (model) {
            case CALC_HOUR:
                return LocalTime.parse(currentTime, DEFAULT_TIME_FORMATTER).plusHours(intervalNumber).format(DEFAULT_TIME_FORMATTER);
            case CALC_MINUTES:
                return LocalTime.parse(currentTime, DEFAULT_TIME_FORMATTER).plusMinutes(intervalNumber).format(DEFAULT_TIME_FORMATTER);
            case CALC_SECONDS:
                return LocalTime.parse(currentTime, DEFAULT_TIME_FORMATTER).plusSeconds(intervalNumber).format(DEFAULT_TIME_FORMATTER);
            default:
                return null;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * <pre>
     *     DateTimeUtil.calculateDifferDay("20191230", "20200102") == 3
     *     DateTimeUtil.calculateDifferDay("20200102", "20191230") == -3
     * </pre>
     * <p>
     * 该方法在开始日期大于结束日期时返回负数，开始日期小于结束日期时返回正数
     *
     * @param beginDate 开始日期 yyyyMMdd格式
     * @param endDate   结束日期 yyyyMMdd格式
     *
     * @return 间隔天数
     */
    public static Long calculateDifferDay(String beginDate, String endDate) {
        return ChronoUnit.DAYS.between(LocalDate.parse(beginDate, DEFAULT_DATE_FORMATTER), LocalDate.parse(endDate, DEFAULT_DATE_FORMATTER));
    }

    /**
     * 格式化日期时间<br/>
     *
     * <pre>
     *     DateTimeUtil.dateTimeFormat("20200509110305", DateTimeUtil.ONE) == 2020-05-09 11:03
     *     DateTimeUtil.dateTimeFormat("20200509110305", DateTimeUtil.TWO) == 2020-05-09 11:03:05
     *     DateTimeUtil.dateTimeFormat("20200509110305", DateTimeUtil.THREE) == 2020-05-09T11:03:05
     *     DateTimeUtil.dateTimeFormat("20200509110305", DateTimeUtil.FOUR) == 2020/05/09 11:03:05
     *     DateTimeUtil.dateTimeFormat("20200509110305", 0) == 20200509110305
     *     DateTimeUtil.dateTimeFormat("20200509110305", -1) == 20200509110305
     * </pre>
     * <p>
     * dateTime 为空则返回StringUtils.EMPTY
     *
     * @param dateTime 日期时间
     * @param type     格式类型
     *
     * @return 格式化后的日期时间
     */
    public static String dateTimeFormat(String dateTime, int type) {
        if (JudgeUtils.isBlank(dateTime)) {
            return StringUtils.EMPTY;
        }
        switch (type) {
            case ONE:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(DEFAULT_DATE_NO_SECOND_TIME);
            case TWO:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(DEFAULT_ALL_TIME);
            case THREE:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(DATE_TIME_FORMATTER);
            case FOUR:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(DATE_TIME_DIAGONAL_FORMATTER);
            case FIVE:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(TIME_FORMATTER_OF_SECOND_TIME);
            default:
                return DateTimeUtils.parseLocalDateTime(dateTime).format(DEFAULT_DATE_TIME_FORMATTER);
        }
    }

    /**
     * 格式化日期<br/>
     *
     * <pre>
     *     DateTimeUtil.formatDate("20200514", DateTimeUtil.ONE) == 2020-05-14
     *     DateTimeUtil.formatDate("20200514", DateTimeUtil.TWO) == 2020.05.14
     *     DateTimeUtil.formatDate("20200514", DateTimeUtil.THREE) == 2020/05/14
     *     DateTimeUtil.formatDate("20200514", DateTimeUtil.FOUR) == 2020年05月14日
     *     DateTimeUtil.formatDate("20200514", 0) == 20200514
     *     DateTimeUtil.formatDate("20200514", -1) == 20200514
     * </pre>
     * <p>
     * date 为空则返回StringUtils.EMPTY
     *
     * @param date 日期
     * @param type 格式类型
     *
     * @return 格式化后的日期
     */
    public static String formatDate(String date, int type) {
        if (JudgeUtils.isBlank(date)) {
            return StringUtils.EMPTY;
        }
        switch (type) {
            case ONE:
                return DateTimeUtils.parseLocalDate(date).format(DATE_FORMATTER_OF_DASH);
            case TWO:
                return DateTimeUtils.parseLocalDate(date).format(DATE_FORMATTER_OF_DOT);
            case THREE:
                return DateTimeUtils.parseLocalDate(date).format(DATE_FORMATTER_OF_DIAGONAL);
            case FOUR:
                return DateTimeUtils.parseLocalDate(date).format(DATE_FORMATTER_OF_CHINESE);
            default:
                return date;
        }
    }

    /**
     * 格式化时间<br/>
     *
     * <pre>
     *     DateTimeUtil.formatDate("165435", DateTimeUtil.ONE) == 16:54:35
     *     DateTimeUtil.formatDate("165435", DateTimeUtil.TWO) == 16时54分35秒
     *     DateTimeUtil.formatDate("165435", 0) == 165435
     *     DateTimeUtil.formatDate("165435", -1) == 165435
     * </pre>
     * <p>
     * time 为空则返回StringUtils.EMPTY
     *
     * @param time 时间
     * @param type 格式类型
     *
     * @return 格式化后的时间
     */
    public static String formatTime(String time, int type) {
        if (JudgeUtils.isBlank(time)) {
            return StringUtils.EMPTY;
        }
        switch (type) {
            case ONE:
                return DateTimeUtils.parseLocalTime(time).format(TIME_FORMATTER_OF_COLON);
            case TWO:
                return DateTimeUtils.parseLocalTime(time).format(TIME_FORMATTER_OF_CHINESE);
            default:
                return time;
        }
    }

    /**
     * 获取当月第一天 或 最后一天
     *
     * @param datetime yyyyMMdd
     * @param flag     1为第一天 2为最后一天
     *
     * @return String
     */
    public static String getMonthFirstOrLastDay(String datetime, Integer flag) {
        switch (flag){
            case 1:
                //第一天
                return LocalDate.parse(datetime, DateTimeFormatter.BASIC_ISO_DATE).with(TemporalAdjusters.firstDayOfMonth()).format(DEFAULT_DATE_FORMATTER);
            case 2:
                //最后一天
                return LocalDate.parse(datetime, DateTimeFormatter.BASIC_ISO_DATE).with(TemporalAdjusters.lastDayOfMonth()).format(DEFAULT_DATE_FORMATTER);
            default:
                return null;
        }
    }

    /**
     * 日期合法性检查
     *
     * @param date 日期 yyyyMMdd
     *
     * @return Boolean true:合法 false:不合法
     */
    public static Boolean dateLegalityCheck(String date) {
        if (JudgeUtils.isBlank(date) || ! NumberUtil.isNumberString(date)) {
            return false;
        }

        int year = Integer.parseInt(StringUtils.left(date, 4));
        int month = Integer.parseInt(StringUtils.substring(date, 4, 6));
        int day = Integer.parseInt(StringUtils.substring(date, 6, 8));

        List<Integer> bigMonthList = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
        List<Integer> smallMonthList = Arrays.asList(4, 6, 9, 11);

        if (year <= FIRST_YEAR) {
            return false;
        }

        if (month == FEB) {
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
            // 闰年2月
            if (isLeapYear) {
                if (day > ZERO && day < THIRTY) {
                    return true;
                }
            } else {
                // 平年二月
                if (day > ZERO && day < TWENTY_NINE) {
                    return true;
                }
            }
        }
        // 大月
        if (bigMonthList.contains(month)) {
            return day > ZERO && day < THIRTY_TWO;
        }
        // 小月
        else if (smallMonthList.contains(month)) {
            return day > ZERO && day < THIRTY_ONE;
        } else {
            // 非法
            return false;
        }
    }

}
