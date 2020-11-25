package com.example.firstdemo.test;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test14 {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(getStartAndEndMonthDate(6)));
    }
    @Deprecated
    public static List<String> getStartAndEndMonthDate(int n){
        List<String> result=new ArrayList<>();
        Date dNow=new Date();
        Date date=new Date();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String str="";
        for(int i=1;i<n+1;i++){
            calendar.setTime(dNow);
            calendar.add(Calendar.MONTH,-i);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            date=calendar.getTime();
            str=sdf.format(date);
            result.add(str);
        }
        return result;
    }
}
