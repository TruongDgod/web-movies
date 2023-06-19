/*******************************************************************************
 * Class        AppUtil
 * Created date 2021/11/11
 * Lasted date
 * Author       VIDAL
 * Change log   2021/11/11 VIDAL Create New
 ******************************************************************************/
package com.bepay.application.utils;

import com.bepay.application.constant.AppConst;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author vidal
 * @return
 */
public class DateUtil {

    /**
     * convertDatetoString
     *
     * @param date
     * @param formate
     * @return String
     */
    public static String convertDatetoString(Date date, String formate) {
        String dateString = "";
        try {
            if (date == null) {
                String m = "";
            } else {
                dateString = new SimpleDateFormat(formate).format(date);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        return dateString;
    }

    /**
     * convertStringToDate
     *
     * @param date
     * @return Date
     */
    public static Date convertStringToDate(String date) throws ParseException {
        String startDateString = date;
        DateFormat df = new SimpleDateFormat(AppConst.DATE_FORMAT_DDMMYYYY);
        Date startDate = null;
        try {
            startDate = df.parse(startDateString);
            String newDateString = df.format(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParseException("Parse Exception", 88);
        }
        return startDate;
    }

    /**
     * convertStringToDate
     *
     * @param date
     * @return String
     * @author thanhsang1999
     */
    public static String dateToStringVN(Date date) {
        return new SimpleDateFormat("'Ngày' dd 'Tháng' MM 'Năm' yyyy").format(date);
    }

    /**
     * Convert String By Format Date
     *
     * @param dateParam {@link String} str date
     * @param format    {@link String} format date
     * @return {@link Date}
     * @author thanhsang1999
     */
    public static Date convertStringToDate(String dateParam, String format) {
        String startDateString = dateParam;
        DateFormat df = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = df.parse(startDateString);

        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * addDays
     *
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDays(Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * getDateDiff
     *
     * @param oldDate
     * @param newDate
     * @param timeUnit
     * @return long
     */
    public static long getDateDiff(Date oldDate, Date newDate, TimeUnit timeUnit) {
        long diffInMillies = newDate.getTime() - oldDate.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * convertStringToTime
     *
     * @param time
     * @return Time
     */
    public static Time convertStringToTime(String time) {
        Time ppstime = null;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        java.util.Date date;
        try {
            date = (java.util.Date) format.parse(time);
            ppstime = new Time(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ppstime;
    }

    /**
     * getTimeDiff
     *
     * @param dateOne
     * @param currentDate
     * @return String
     */
    public static String getTimeDiff(Date dateOne, Date currentDate) {
        String timeDifference = "";
        long timeDiff = Math.abs(dateOne.getTime() - currentDate.getTime());
        long dayDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
        long hoursDiff = TimeUnit.MILLISECONDS.toHours(timeDiff);
        long minDiff = TimeUnit.MILLISECONDS.toMinutes(timeDiff);

        if (dayDiff == 1) {
            timeDifference = "Yesterday";

        } else if (dayDiff > 1) {
            timeDifference = dayDiff + " Days";

        } else if (hoursDiff > 11) {
            timeDifference = "Today";

        } else if (hoursDiff < 12 && hoursDiff > 0) {
            timeDifference = hoursDiff + " Hours";

        } else {
            timeDifference = minDiff + " Mins";

        }
        return timeDifference;
    }

    /**
     * getDateByMonthAndYear
     *
     * @param month
     * @param year
     * @param type
     * @return Date
     */
    public static Date getDateByMonthAndYear(int month, int year, int type) {
        // GregorianCalendar gc = null;
        Date date = null;
        LocalDate localDate = null;
        YearMonth yearMonth = YearMonth.of(year, month);
        if (type == 0) {
            // gc = new GregorianCalendar(year, month, 1);
            localDate = yearMonth.atDay(1);
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else if (type == 1) {
            localDate = yearMonth.atEndOfMonth();
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return date;
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDateToDate(LocalDate localDate, String time) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.parse(time));
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }


    public static Date now() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertLocalDateToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getNextMonth(Date date) {
        return localDateTimeToDate(dateToLocalDateTime(date).plusMonths(1));
    }

    public static Date getPrevMonth(Date date) {
        return localDateTimeToDate(dateToLocalDateTime(date).minusMonths(1));
    }

    public static Date getNext31Days(Date date) {
        return localDateTimeToDate(dateToLocalDateTime(date).plusDays(31));
    }

    public static Date addSeconds(Date date, int seconds) {
        if (date == null) date = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ldt = ldt.plusSeconds(seconds);
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minusSeconds(Date date, int seconds) {
        return addSeconds(date, -seconds);
    }

    public static void main(String[] args) {
        Date d = new Date();
        System.out.println(minusSeconds(d, 3600));
    }
}
