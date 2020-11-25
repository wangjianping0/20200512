package com.example.firstdemo.test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test12 {
    public static void main(String[] args) {
//        String str="abcde";
//        if(str.contains("a")){
//            System.out.println("----a----");
//        }else if(str.contains("b")){
//            System.out.println("----b----");
//        }
//        String st=null;
////        BigDecimal bigDecimal=new BigDecimal(st);
////        System.out.println(bigDecimal.doubleValue());
//        double num1 =  49.99;
//        int num2 = (int)(num1 *100);
//        System.out.println(num1+"-----"+num2);
//        System.out.println("---"+new DecimalFormat("0").format(num1));
//        System.out.println("-----"+new DecimalFormat("#.00").format(num1));
//
//        System.out.println("----bigdecimal:"+new BigDecimal(num1).intValue());
//        System.out.println("----"+(long)Math.ceil(num1/10));
//        System.out.println(Integer.parseInt(new java.text.DecimalFormat("0").format(Math.ceil(num1/10))));
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.MONTH,-1);
            date.setYear(calendar.get(Calendar.YEAR));
            date.setMonth(calendar.get(Calendar.MONTH));
            System.out.println("now:"+sdf.format(date));
        }

//        calendar.add(Calendar.MONTH,-5);
//        date.setYear(calendar.get(Calendar.YEAR));
//        date.setMonth(calendar.get(Calendar.MONTH));
//        System.out.println("last:"+sdf.format(date));

    }
}
