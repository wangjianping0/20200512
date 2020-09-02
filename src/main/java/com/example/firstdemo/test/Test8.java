package com.example.firstdemo.test;

import java.util.Date;

public class Test8 {
    public static void main(String[] args) {
        String str="2020.8.26";
        String[] strs=str.split("\\.");
        Date date=new Date();
        int year=date.getYear()+1900;
        int month=date.getMonth()+1;
        //拆箱
        System.out.println(year+"=="+ Integer.parseInt(strs[0])+":"+(year== Integer.parseInt(strs[0])));
        System.out.println(month+"=="+Integer.parseInt(strs[1])+":"+(month==Integer.parseInt(strs[1])));

        System.out.println(date.getTime());
        int c=2020;
        int d =2020;
        System.out.println(c+"=="+d+":"+(c==d));
    }
}
