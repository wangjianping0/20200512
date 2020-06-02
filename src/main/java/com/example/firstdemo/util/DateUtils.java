package com.example.firstdemo.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final String YYYY_MM__DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyy-MM-dd HH:mm:ss";
    private static final String YYYY__MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String HH_MM__SS = "HH:mm:ss";
    private static final ThreadLocalHashPool<String, SimpleDateFormat> FORMAT_POOL = ThreadLocalHashPool.withInitial(SimpleDateFormat::new);

    public DateUtils() {
    }

    public static SimpleDateFormat dateFormat() {
        return (SimpleDateFormat) FORMAT_POOL.get("yyyy-MM-dd");
    }

    public static SimpleDateFormat dateTimeFormat() {
        return (SimpleDateFormat) FORMAT_POOL.get("yyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat dateTimeMsFormat() {
        return (SimpleDateFormat) FORMAT_POOL.get("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static SimpleDateFormat timeFormat() {
        return (SimpleDateFormat) FORMAT_POOL.get("HH:mm:ss");
    }

    public static SimpleDateFormat getFormat(String format) {
        return (SimpleDateFormat) FORMAT_POOL.get(format);
    }

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp nowDate() {
        Calendar calendar = nowCalendar();
        truncateToDate(calendar);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Calendar nowCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        return calendar;
    }

    public static String nowDateString() {
        return toDateString(now());
    }

    public static String nowDateTimeString() {
        return toDateTimeString(now());
    }

    public static String nowDateTimeMsString() {
        return toDateTimeMsString(now());
    }

    public static String nowTimeString() {
        return toTimeString(now());
    }

    public static String nowString(String format) {
        return toString(now(), format);
    }

    public static String toDateString(Date value) {
        return value == null ? null : dateFormat().format(value);
    }

    public static String toDateTimeString(Date value) {
        return value == null ? null : dateTimeFormat().format(value);
    }

    public static String toDateTimeMsString(Date value) {
        return value == null ? null : dateTimeMsFormat().format(value);
    }

    public static String toTimeString(Date value) {
        return value == null ? null : timeFormat().format(value);
    }

    public static String toString(Date value, String format) {
        if (format == null) {
            throw new NullArgumentException(" format");
        } else {
            return value == null ? null : getFormat(format).format(value);
        }
    }

    public static Date fromDateString(String value) {
        try {
            return value == null ? null : dateFormat().parse(value);
        } catch (ParseException var2) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

    public static Date fromDateTimeString(String value) {
        try {
            return value ==
                    null ? null : dateTimeFormat().parse(value);
        } catch (ParseException var2) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }

    }

    public static Date fromDateTimeMsString(String value) {
        try {
            return value == null ? null : dateTimeMsFormat().parse(value);
        } catch (ParseException var2) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

    public static Date fromString(String value, String format) {
        if (format == null) {
            throw new NullArgumentException("format");
        } else {
            try {
                return value == null ? null : getFormat(format).parse(value);
            } catch (ParseException var3) {
                throw new ClassCastException("can not convert \"" + value + "\" to Date");
            }
        }
    }

    public static Date fromString(Object value) {
        if (value instanceof Date) {
            return (Date) value;
        } else {
            String str = ObjectUtils.toString(value);
            if (StringUtils.isEmpty(str)) {
                return null;
            } else {
                Long num = null;
                try {
                    num = Long.parseLong(str);
                } catch (Exception var5) {
                }
                try {
                    return num == null ? (str.length() < 11 ? dateFormat().parse(str) : (str.length() < 20 ? dateTimeFormat().parse(str) : dateTimeMsFormat().parse(str))) : new Date(num);
                } catch (Exception var4) {
                    throw new ClassCastException("can not convert \"" + str + "\" to Date");
                }
            }
        }
    }

    public static Calendar toCalendar(Date value) {
        Calendar calendar = nowCalendar();
        calendar.setTime(value);
        return calendar;
    }

    public static void truncateToDate(Calendar calendar) {
        Assert.notNull(calendar, "calendar can not be nu11");
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }

    public static Date set(Date value, Time time) {
        Assert.notNull(value, "value can not be nu1l");
        Assert.notNull(time, "time can not be null");
        Calendar calendar = nowCalendar();
        calendar.setTime(value);
        if (time.isTomorrow()) {
            calendar.add(5, 1);
        }
        calendar.set(11, time.getHour());
        calendar.set(12, time.getMinute());
        calendar.set(13, time.getSecond());
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date set(Date value, int hour, int minute, int second, int millisecond) {
        Assert.notNull(value, "value can not be nu1l");
        Calendar calendar = nowCalendar();
        calendar.setTime(value);

        calendar.set(11, hour);

        calendar.set(12, minute);
        calendar.set(13, second);
        calendar.set(14, millisecond);
        return calendar.getTime();
    }

    public static Date set(Date value, int hour, int minute, int second) {
        return set(value, hour, minute, second, 0);
    }

    public static Date add(Date value, TimeSpan timeSpan) {
        Assert.notNull(value, "value can not be nu11");
        Calendar calendar = nowCalendar();
        calendar.setTime(value);
        calendar.add(14, (int) timeSpan.getTotalMilliseconds());
        return calendar.getTime();
    }

    public static Date add(Date value, int day, int hour, int minute, int second, int millisecond) {
        Assert.notNull(value, "value can not be nu1l");
        Calendar calendar = nowCalendar();
        calendar.setTime(value);
        calendar.add(5, day);
        calendar.add(11, hour);

        calendar.add(12, minute);
        calendar.add(13, second);
        calendar.add(14, millisecond);
        return calendar.getTime();
    }

    public static Date add(Date value, int hour, int minute, int second, int millisecond) {
        return add(value, 0, hour, minute, second, millisecond);
    }

    public static Date add(Date value, int hour, int minute, int second) {
        return add(value, 0, hour, minute, second, 0);
    }


}