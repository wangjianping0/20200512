package com.example.firstdemo.util;

import java.util.Date;

public final class TimeSpan {
    public static final long MillisecondsPerSecond = 1000L;

    public static final long MillisecondsPerMinute = 60000L;
    public static final long MillisecondsPerHour = 3600000L;
    public static final long MillisecondsPerDay = 86400000L;
    private static final double SecondsPerMillisecond = 0.001D;
    private static final double MinutesPerMillisecond = 1.66666666666667E-5D;
    private static final double HoursPerMillisecond = 2.77777777777776E-7D;
    private static final double DaysPerMilisecond = 1.1574074074074074E-8D;
    private final long milliseconds;

    public TimeSpan(Date timeLeft, Date timeRight) {
        this.milliseconds = timeLeft.getTime() - timeRight.getTime();
    }

    public TimeSpan(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public TimeSpan(int hours, int minutes, int seconds) {
        this(0, hours, minutes, seconds, 0);
    }

    public TimeSpan(int days, int hours, int minutes, int seconds) {
        this(days, hours, minutes, seconds, 0);
    }

    public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds) {
        this.milliseconds = (long) days * 86400000L + (long) hours * 3600000L + (long) minutes * 60000L + (long)
                seconds * 1000L + (long) milliseconds;
    }

    public int getDays() {
        return (int) (this.milliseconds / 86400000L);
    }

    public int getHours() {
        return (int) (this.milliseconds / 3600000L % 24L);
    }

    public int getMinutes() {
        return (int) (this.milliseconds / 60000L % 60L);
    }

    public int getSeconds() {
        return (int) (this.milliseconds / 1000L % 60L);
    }

    public int getMilliseconds() {
        return (int) (this.milliseconds % 1000L);
    }

    public double getTotalDays() {
        return (double) this.milliseconds * 1.1574074074074074E-8D;
    }

    public double getTotalHours() {
        return (double) this.milliseconds * 2.7777777777777776E-7D;
    }

    public double getTotalMinutes() {
        return (double) this.milliseconds * 1.666666666666667E-5D;
    }

    public double getTotalSeconds() {
        return (double) this.milliseconds * 0.001D;
    }

    public double getTotalMilliseconds() {
        return (double) this.milliseconds;
    }

}
