package cn.donut.ordermq.worker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 〈时间工具类〉
 *
 * @author LiYuAn
 * @create 2018/5/17
 * @since 1.0.0
 */
public class DateUtil {

    public static String datePattern = "yyyy-MM-dd";

    public static String timePattern = "yyyy-MM-dd HH:mm";

    public static String dateMoth = "yyyy-MM";

    public static String timePattern2 = "yyyy-MM-dd HH:mm:ss";

    public static String timeForAlarm = "yyyy.MM.dd HH:mm:ss";

    public static String timePattern3 = "yyyy年MM月dd日HH时";

    public static String timePattern4 = "yyyy年MM月dd日HH时mm分";

    public static String timePattern5 = "yyyy 年 MM 月 dd 日";

    ///////////////////////////////////////////////////////////////////////////////////

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINITE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINITE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;

    public static final String STD_DATE_PATTERN = "yyyy-MM-dd";
    public static final String STD_TIME_PATTERN = "HH:mm:ss";
    public static final String STD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String RFC822_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final String W3C_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    //@formatter:off
    private static final String[] RFC822_PATTENRS = {
            "EEE, dd MMM yy HH:mm:ss z",
            "EEE, dd MMM yy HH:mm z",
            "dd MMM yy HH:mm:ss z",
            "dd MMM yy HH:mm z",
    };

    private static final String[] W3CDATETIME_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSz",
            "yyyy-MM-dd't'HH:mm:ss.SSSz",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd't'HH:mm:ss.SSS'z'",
            "yyyy-MM-dd'T'HH:mm:ssz",
            "yyyy-MM-dd't'HH:mm:ssz",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd't'HH:mm:ss'z'",
            "yyyy-MM-dd'T'HH:mmz",
            "yyyy-MM'T'HH:mmz",
            "yyyy'T'HH:mmz",
            "yyyy-MM-dd't'HH:mmz",
            "yyyy-MM-dd'T'HH:mm'Z'",
            "yyyy-MM-dd't'HH:mm'z'",
            "yyyy-MM-dd",
            "yyyy-MM",
            "yyyy",
    };

    private static final String[] STD_PATTERNS = {
            "yyyy-MM-dd HH:mm:ss,SSS",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd",
            "yyyy/MM/dd HH:mm:ss,SSS",
            "yyyy/MM/dd HH:mm:ss.SSS",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm",
            "yyyy/MM/dd",
            "yyyyMMddHHmmss",
            "yyyyMMdd",
            "hh:mm:ss,SSS",
            "hh:mm:ss.SSS",
            "hh:mm:ss",
    };
    //@formatter:on

    /**
     * Return default date format (yyyy/MM/dd)
     *
     * @return the date format will be show
     */
    public static String getDatePattern() {
        return DateUtil.datePattern;
    }

    /**
     * @param aDate
     * @return
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(DateUtil.datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * @param aMask
     * @param strDate
     * @return Date
     * @throws ParseException
     * @see java.text.SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * yyyy/MM/dd HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return DateUtil.getDateTime(DateUtil.timePattern, theTime);
    }

    public static Date getDateNow(Date theTime) {
        Date date = null;
        try {
            date = DateUtil.convertStringToDate(DateUtil.getDateTime(DateUtil.datePattern, theTime));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return date;
    }

    /**
     * This method returns the current date in the format: yyyy/MM/dd
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(DateUtil.convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            // log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a current datetimestamp
     * in the format you specify on input
     *
     * @param
     * @param
     * @return a formatted string representation of the now
     * @see java.text.SimpleDateFormat
     * by woo 2009-06-11
     */

    public static final String getDateTimeStamp() {

        String returnValue = "";

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        // int intHour=calCurrent.get(Calendar.HOUR);
        int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
        int intMin = calCurrent.get(Calendar.MINUTE);
        int intSec = calCurrent.get(Calendar.SECOND);

        if (intDay == 0) {
            // log.error("aDate is null!");
        } else {
            // df = new SimpleDateFormat(aMask);
            returnValue = String.format("%1$04d", intYear) + String.format("%1$02d", intMonth) +
                    String.format("%1$02d", intDay) + String.format("%1$02d", intHour) +
                    String.format("%1$02d", intMin) + String.format("%1$02d", intSec);

        }
        return (returnValue);
    }

    /**
     * @param aDate
     * @return
     */
    public static final String convertDateToString(Date aDate) {
        return DateUtil.getDateTime(DateUtil.datePattern, aDate);
    }

    /**
     * @param aDate
     * @return
     */
    public static final String convertDateToStringForAlarm(Date aDate) {
        return DateUtil.getDateTime(DateUtil.timeForAlarm, aDate);
    }

    /**
     * @param aDate
     * @return
     */
    public static final String convertTimeToString(Date aDate) {
        return DateUtil.getDateTime(DateUtil.timePattern2, aDate);
    }

    public static final String convertTimeToStringForYu(Date aDate) {
        return DateUtil.getDateTime(DateUtil.timePattern3, aDate);
    }

    public static final String convertTimeToStringForFen(Date aDate) {


        return DateUtil.getDateTime(DateUtil.timePattern4, aDate);
    }

    public static final String convertTimeToStringForDay(Date aDate) {
        return DateUtil.getDateTime(DateUtil.timePattern5, aDate);
    }

    /**
     * @param strDate (format yyyy/MM/dd)
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDateMoth(String strDate) throws ParseException {
        Date aDate = null;

        try {

            aDate = DateUtil.convertStringToDate(DateUtil.dateMoth, strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }
        return aDate;
    }

    /**
     * @param strDate (format yyyy/MM/dd)
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate) throws ParseException {
        Date aDate = null;

        try {
            aDate = DateUtil.convertStringToDate(DateUtil.datePattern, strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }

        return aDate;
    }

    public static Date convertStringToDateTime(String strDate) throws ParseException {
        Date aDate = null;

        try {

            aDate = DateUtil.convertStringToDate(DateUtil.timePattern, strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());

        }

        return aDate;
    }

    public static String weekday(String date) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance();

        Date da = null;
        try {
            da = dateFormat.parse(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        calendar.setTime(da);
        int num = calendar.get(Calendar.DAY_OF_WEEK);

        if (num == 1) {
            num = 7;
        } else {
            num = num - 1;
        }

        return num + "";

    }

    public static boolean isMorning() {

        boolean isMorning = false;

        Calendar cal = new GregorianCalendar();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if ((hour >= 6) && (hour < 18)) {
            isMorning = true;
        }

        return isMorning;

    }

    public static boolean isMonthEnd() {
        boolean yes = false;

        Calendar cal = new GregorianCalendar();
        if (cal.get(Calendar.DATE) == cal.getActualMaximum(Calendar.DATE)) {
            yes = true;
        }

        return yes;
    }

    public static boolean isBeforeToday(Date date) {
        Date today = new Date();

        return date.before(today);
    }

    public static Date getTodayEnd() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.datePattern);

        String todayAsString = df.format(today);

        todayAsString += " 23:59:59";

        return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", todayAsString);
    }

    public static Date DateBefAft(int days, String format) {
        //
        if ((format == null) || "".equals(format)) {
            format = "yyyy-MM-dd";
        }
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        now.add(Calendar.DAY_OF_YEAR, -days);
        Date d = null;
        try {
            d = DateUtil.convertStringToDate(formatter.format(now.getTime()));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return d;
    }

    /**
     * 字符串转换为日期型
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        if ((dateString == null) || "".equals(dateString)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.timePattern);
        return df.parse(dateString, new ParsePosition(0));
    }

    public static final String getYear() {

        String returnValue = "";

        Calendar calCurrent = Calendar.getInstance();

        int intYear = calCurrent.get(Calendar.YEAR);

        returnValue = String.valueOf(intYear);

        return returnValue;
    }

    /**
     * 获得当天之后的N天日期
     *
     * @return
     */
    public static List<String> getNextDays(int num) {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.datePattern);
        for (int i = 1; i < num; i++) {
            list.add(sdf.format(DateUtil.getAfterDate(i)));
        }
        return list;
    }

    /**
     * 获得当天之前的N天日期
     *
     * @return
     */
    public static List<String> getBeforeDays(int num) {
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.datePattern);
        for (int i = 1; i < num; i++) {
            list.add(sdf.format(DateUtil.getBeforeDate(i)));
        }
        return list;
    }

    /**
     * 获取当前日期n天后的日期
     *
     * @param n :返回当天后的第N天
     * @return 返回的日期
     */
    public static Date getAfterDate(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, n);
        return c.getTime();
    }

    /**
     * 获取当前日期n天钱的日期
     *
     * @param n :返回当天后的第N天
     * @return 返回的日期
     */
    public static Date getBeforeDate(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -n);
        return c.getTime();
    }

    /**
     * 计算两个日期相差几天
     *
     * @param n :返回当天后的第N天
     * @return 天数
     * @throws ParseException
     */
    public static long getBeforeDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.datePattern);
        Date d1 = sdf.parse(startDate);
        Date d2 = sdf.parse(endDate);
        long daysBetween = ((d2.getTime() - d1.getTime()) + 1000000) / (3600 * 24 * 1000);
        return daysBetween;
    }

    /**
     * 获得当前月份的第一天和最后一天。
     * <p>
     * 数组中第一个存的是第一天，第二个存的是最后一天
     *
     * @return
     */
    public static String[] getMonthStartEnd(Calendar today) {
        String[] dateStr = new String[2];
        int month = today.get(Calendar.MONTH) + 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) ||
                (month == 12)) {
            today.set(Calendar.DAY_OF_MONTH, 1);
            dateStr[0] = format.format(today.getTime()) + " 00:00:00";
            Calendar lastDay = DateUtil.getJumpDateSimple(today, 30, true);
            dateStr[1] = format.format(lastDay.getTime()) + " 00:00:00";
            return dateStr;
        } else if (month == 2) {
            today.set(Calendar.DAY_OF_MONTH, 1);
            dateStr[0] = format.format(today.getTime()) + " 00:00:00";
            if (DateUtil.isProYear(today.get(Calendar.YEAR))) {
                Calendar lastDay = DateUtil.getJumpDateSimple(today, 28, true);
                dateStr[1] = format.format(lastDay.getTime()) + " 00:00:00";
            } else {
                Calendar lastDay = DateUtil.getJumpDateSimple(today, 27, true);
                dateStr[1] = format.format(lastDay.getTime()) + " 00:00:00";
            }
            return dateStr;
        } else {
            today.set(Calendar.DAY_OF_MONTH, 1);
            dateStr[0] = format.format(today.getTime()) + " 00:00:00";
            Calendar lastDay = DateUtil.getJumpDateSimple(today, 29, true);
            dateStr[1] = format.format(lastDay.getTime()) + " 00:00:00";
            return dateStr;
        }
    }

    /**
     * 获得特定日期多少天前（后）的日前
     *
     * @param oldD [Calendar] 特定日期
     * @param days [int] 天数
     * @param b    [boolean][false前][true后] notice: days<=365
     */
    public static java.util.Calendar getJumpDateSimple(java.util.Calendar oldD, int days, boolean b) {
        if (days > 365) {
            return null;
        }
        // int nowYear = oldD.getYear();
        int nowYear = oldD.get(Calendar.YEAR);
        // Calendar ca = Calendar.getInstance();
        // ca.setTime(oldD);
        Calendar ca = oldD;
        int nowDay = ca.get(Calendar.DAY_OF_YEAR);
        int newYear = 0;
        int newDay = 0;

        if (b) { // 向后加
            newDay = nowDay + days;
            newYear = nowYear;
            if (DateUtil.isProYear(nowYear)) {
                if (newDay > 366) {
                    newDay = newDay - 366;
                    newYear = nowYear + 1;
                }
            } else {
                if (newDay > 365) {
                    newDay = newDay - 365;
                    newYear = nowYear + 1;
                }
            }
        } else { // 向前减
            newDay = nowDay - days;
            newYear = nowYear;
            if (DateUtil.isProYear(nowYear)) {
                if (newDay < 0) {
                    newDay = newDay + 366;
                    newYear = nowYear - 1;
                }
            } else {
                if (newDay < 0) {
                    newDay = newDay + 365;
                    newYear = nowYear - 1;
                }
            }
        }

        java.util.Calendar newDate = DateUtil.getDateByday(newYear, newDay);
        return newDate;
    }

    /**
     * 判别是否是润年
     *
     * @param year [int] 输入的年份
     * @return boolean [ture润年][false非润年]
     */
    public static boolean isProYear(int year) {
        boolean isproyear = false;
        if (((year % 400) == 0) | (((year % 100) != 0) && ((year % 4) == 0))) {
            isproyear = true;
        } else {
            isproyear = false;
        }
        return isproyear;
    }

    /**
     * 根据特定年中的第多少天，获得新日期
     *
     * @param year [int] 输入特定年份
     * @param day  [int] 输入的第多少天
     * @return Calendar
     * @author : 胡长城 Date: 2002-01-14
     */
    public static java.util.Calendar getDateByday(int year, int day) {

        if (day > 366) {
            return null;
        }

        boolean isproyear = DateUtil.isProYear(year);
        int dayofmonth = 0;
        int i = day;
        int month = 0;

        if (isproyear) {
            // 润年情况
            if (i <= 31) {
                dayofmonth = day;
                month = 1;
            } else if ((i > 31) && (i <= 60)) {
                dayofmonth = day - 31;
                month = 2;
            } else if ((i > 60) && (i <= 91)) {
                dayofmonth = day - 60;
                month = 3;
            } else if ((i > 91) && (i <= 121)) {
                dayofmonth = day - 91;
                month = 4;
            } else if ((i > 121) && (i <= 152)) {
                dayofmonth = day - 121;
                month = 5;
            } else if ((i > 152) && (i <= 182)) {
                dayofmonth = day - 152;
                month = 6;
            } else if ((i > 182) && (i <= 213)) {
                dayofmonth = day - 182;
                month = 7;
            } else if ((i > 213) && (i <= 244)) {
                dayofmonth = day - 213;
                month = 8;
            } else if ((i > 244) && (i <= 274)) {
                dayofmonth = day - 244;
                month = 9;
            } else if ((i > 274) && (i <= 305)) {
                dayofmonth = day - 274;
                month = 10;
            } else if ((i > 305) && (i <= 335)) {
                dayofmonth = day - 305;
                month = 11;
            } else if ((i > 335) && (i <= 366)) {
                dayofmonth = day - 335;
                month = 12;
            }
        } else {
            // 非润年情况
            if (i <= 31) {
                dayofmonth = day;
                month = 1;
            } else if ((i > 31) && (i <= 59)) {
                dayofmonth = day - 31;
                month = 2;
            } else if ((i > 59) && (i <= 90)) {
                dayofmonth = day - 59;
                month = 3;
            } else if ((i > 90) && (i <= 120)) {
                dayofmonth = day - 90;
                month = 4;
            } else if ((i > 120) && (i <= 151)) {
                dayofmonth = day - 120;
                month = 5;
            } else if ((i > 151) && (i <= 181)) {
                dayofmonth = day - 151;
                month = 6;
            } else if ((i > 181) && (i <= 212)) {
                dayofmonth = day - 181;
                month = 7;
            } else if ((i > 212) && (i <= 243)) {
                dayofmonth = day - 212;
                month = 8;
            } else if ((i > 243) && (i <= 273)) {
                dayofmonth = day - 243;
                month = 9;
            } else if ((i > 273) && (i <= 304)) {
                dayofmonth = day - 273;
                month = 10;
            } else if ((i > 304) && (i <= 334)) {
                dayofmonth = day - 304;
                month = 11;
            } else if ((i > 334) && (i <= 365)) {
                dayofmonth = day - 334;
                month = 12;
            }
        }
        java.util.Calendar ca = java.util.Calendar.getInstance();

        // 设定时间从当前的0时0分1秒开始
        // 所有的月份均是0 based:0-11
        ca.set(year, month - 1, dayofmonth, 0, 0, 1);
        return ca;
    }

    // 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return DateUtil.numToUpper(year) + "年" + DateUtil.monthToUppder(month) + "月" + DateUtil.dayToUppder(day) + "日";
    }

    // 将数字转化为大写
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(int month) {
        if (month < 10) {
            return DateUtil.numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + DateUtil.numToUpper(month - 10);
        }
    }

    // 日转化为大写
    public static String dayToUppder(int day) {
        if (day < 20) {
            return DateUtil.monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return DateUtil.numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return DateUtil.numToUpper(Integer.parseInt(str[0] + "")) + "十" +
                        DateUtil.numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    /**
     * 在给定日期上增减一段时间
     *
     * @param dayAmount 天数
     * @param date      给定日期
     * @return 增减后的日期
     */
    public static Date addDay2Date(int dayAmount, Date date) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, dayAmount);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    ////////////////////////////////////////////////////////////////

    /**
     * 用指定的格式格式化当前时间.
     */
    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 用指定的格式格式化指定时间.
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 用尝试多种格式解析日期时间.
     *
     * @param date 时间字符串
     * @return 如果无法解析，那么返回 {@code null}
     */
    public static Date parse(String date) {
        Date d = parse(date, STD_PATTERNS);
        if (d == null) {
            d = parseRFC822Date(date);
        }
        if (d == null) {
            d = parseW3CDateTime(date);
        }
        if (d == null) {
            try {
                d = DateFormat.getInstance().parse(date);
            } catch (ParseException e) {
                d = null;
            }
        }
        return d;
    }

    /**
     * 用指定的格式解析日期时间.
     *
     * @param date    时间字符串
     * @param pattern see {@link java.text.SimpleDateFormat}
     * @return 如果无法解析，那么返回 {@code null}
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);

        try {
            ParsePosition pp = new ParsePosition(0);
            Date d = df.parse(date, pp);
            if (d != null && pp.getIndex() == date.length()) {
                return d;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 用指定的格式解析日期时间.
     *
     * @param date     时间字符串
     * @param patterns 多个模式，see {@link java.text.SimpleDateFormat}
     * @return 如果无法解析，那么返回 {@code null}
     */
    public static Date parse(String date, String[] patterns) {
        if (date == null || date.length() == 0) {
            return null;
        }

        date = date.trim();
        for (String pattern : patterns) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            try {
                ParsePosition pp = new ParsePosition(0);
                Date d = df.parse(date, pp);
                if (d != null && pp.getIndex() == date.length()) {
                    return d;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Date parseRFC822Date(String date) {
        int ipos = date.indexOf(" UT");
        if (ipos > -1) {
            String pre = date.substring(0, ipos);
            String post = date.substring(ipos + 3);
            date = pre + " GMT" + post;
        }
        return parse(date, RFC822_PATTENRS);
    }

    public static Date parseW3CDateTime(String date) {
        // if sDate has time on it, it injects 'GTM' before de TZ displacement
        // to allow the SimpleDateFormat parser to parse it properly
        int tIndex = date.indexOf("T");
        if (tIndex > -1) {
            if (date.endsWith("Z")) {
                date = date.substring(0, date.length() - 1) + "+00:00";
            }
            int tzdIndex = date.indexOf("+", tIndex);
            if (tzdIndex == -1) {
                tzdIndex = date.indexOf("-", tIndex);
            }
            if (tzdIndex > -1) {
                String pre = date.substring(0, tzdIndex);
                int secFraction = pre.indexOf(",");
                if (secFraction > -1) {
                    pre = pre.substring(0, secFraction);
                }
                String post = date.substring(tzdIndex);
                date = pre + "GMT" + post;
            }
        } else {
            date += "T00:00GMT";
        }
        return parse(date, W3CDATETIME_PATTERNS);
    }

    public static String formatRFC822(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(RFC822_DATETIME_PATTERN);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }

    public static String formatW3CDateTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(W3C_DATETIME_PATTERN);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }


}
