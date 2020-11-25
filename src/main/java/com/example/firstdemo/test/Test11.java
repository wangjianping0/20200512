package com.example.firstdemo.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test11 {
    public static void main(String[] args) {
        Date date=new Date();
        date.setMonth(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String str=sdf.format(date);
        System.out.println(getHbDate(str));
        System.out.println(getTbDate(str));
    }
    /**
     *
     * @Description: 获取上个月环比日期
     * @param
     * @return String
     * @throws
     * @author
     * @createtime
     */
    public static String getHbDate(String currentDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String monthFormat;
        try {
            date = sdf.parse(currentDate + "-" + "01");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        int month = c.get(Calendar.MONTH) + 1;
        if(month<=9) {
            monthFormat="0"+month;
        }else {
            monthFormat=String.valueOf(month);
        }
        String hbDate = c.get(Calendar.YEAR) + "-"
                + monthFormat+"-01";
        return hbDate;
    }
    /**
     *
     * @Description: 获取去年同比日期
     * @param
     * @return String
     * @throws
     * @author
     * @createtime
     */
    public static String getTbDate(String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String monthFormat;
        try {
            date = sdf.parse(currentDate + "-" + "01");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        int month = c.get(Calendar.MONTH) + 1;
        if(month<=9) {
            monthFormat="0"+month;
        }else {
            monthFormat=String.valueOf(month);
        }
        String tbDate = c.get(Calendar.YEAR) + "-"
                + monthFormat+"-01";
        return tbDate;
    }
}
