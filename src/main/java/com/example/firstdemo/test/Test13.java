package com.example.firstdemo.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test13 {
    public static void main(String[] args) {
//        List<String> list=new ArrayList<>(6);
        List<String> list= Arrays.asList("","","","","","");
        System.out.println("list.size():"+list.size()+"|"+JSON.toJSONString(list));
        list.set(5,"555");
        list.set(4,"444");
        list.set(3,"333");
        list.set(2,"222");
        list.set(1,"111");
        list.set(0,"000");
//        list.add(5,"555");
//        list.add(4,"444");
//        list.add(3,"333");
//        list.add(2,"222");
//        list.add(1,"111");
//        list.add(0,"000");
        System.out.println("list.size():"+list.size()+"|"+JSON.toJSONString(list));
        String [] strings=new String[6];
        strings[5]="555";
        strings[4]="444";
        strings[3]="3";
        strings[2]="2";
        strings[1]="1";
        strings[0]="0";
        System.out.println("strings.length:"+strings.length+"|"+JSON.toJSONString(strings));
    }
}
