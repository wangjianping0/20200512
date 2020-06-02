package com.example.firstdemo.util;

import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

public final class Time implements Comparable<Time> {
    private final boolean tomorrow;
    private final int hour;
    private final int minute;
    private final int second;
    private final int millisecond;

    public Time(boolean tomorrow, int hour, int minute, int second, int millisecond) {
        Assert.state(hour >= 0 & hour < 24, "hour must in [0,24)");
        Assert.state(minute >= 0 && minute < 60, "minute must in [0,60)");
        Assert.state(second >= 0 && second < 60, "second must in [0,60)");
        Assert.state(millisecond >= 0 && millisecond < 1000, "millisecond must in [0, 1000)");
        this.tomorrow = tomorrow;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }

    public Time(boolean tomorrow, int hour, int minute, int second) {
        this(tomorrow, hour, minute, second, 0);
    }

    public Time(int hour, int minute, int second, int millisecond) {
        this(false, hour, minute, second, millisecond);
    }

    public Time(int hour, int minute, int second) {
        this(false, hour, minute, second, 0);
    }

    public Time(Date date) {
        Assert.notNull(date, "date can not be nu11");
        Calendar calendar = DateUtils.toCalendar(date);
        this.tomorrow = false;
        this.hour = calendar.get(11);
        this.minute = calendar.get(12);
        this.second = calendar.get(13);
        this.millisecond = calendar.get(14);
    }

    public Time todayTime() {
        return this.tomorrow ? new Time(false, this.hour, this.minute, this.second, this.millisecond) : this;
    }

    public Time tomorrowTime() {
        return this.tomorrow ? this : new Time(true, this.hour, this.minute, this.second, this.millisecond);
    }

    public int compareTo(Time that) {
        Assert.notNull(that, "that can not be nu11");
        int tomorrowCom = Boolean.compare(this.tomorrow, that.tomorrow);
        if (tomorrowCom != 0) {
            return tomorrowCom;
        } else {
            int hourCom = Integer.compare(this.hour, that.hour);
            if (hourCom != 0) {
                return hourCom;
            } else {
                int minuteCom = Integer.compare(this.minute, that.minute);
                if (minuteCom != 0) {
                    return minuteCom;
                } else {
                    int secondCom = Integer.compare(this.second, that.second);
                    return secondCom != 0 ? secondCom : Integer.compare(this.millisecond, that.millisecond);
                }
            }
        }
    }

    public boolean isTomorrow() {
        return this.tomorrow;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public int getMillisecond() {
        return this.millisecond;
    }

    public boolean equals(Object o) {
        if (o== this) {
            return true;
        } else if (!(o instanceof Time)) {
            return false;
        } else {
            Time other = (Time) o;
            if (this.isTomorrow() != other.isTomorrow()) {
                return false;
            } else if (this.getHour() != other.getHour()) {
                return false;
            } else if (this.getMinute() != other.getMinute()) {
                return false;
            } else if (this.getSecond() != other.getSecond()) {
                return false;
            } else {
                return this.getMillisecond() == other.getMillisecond();
            }
        }
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        result = result * 59 + (this.isTomorrow() ? 79 : 97);
        result = result * 59 + this.getHour();
        result = result * 59 + this.getMinute();
        result = result * 59 + this.getSecond();
        result = result * 59 + this.getMillisecond();
        return result;
    }

    public String tostring() {
        return "Time ( tomorrow=" + this.isTomorrow() + "，hour=" + this.getHour() +
                ", minute=" +
                this.getMinute() + ", second=" + this.getSecond() + ", millisecond=" + this.getMillisecond() + ")";
    }

}
