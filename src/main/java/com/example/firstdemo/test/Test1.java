package com.example.firstdemo.test;

import java.lang.reflect.Method;

public class Test1 {
    public static void main(String[] args) throws Exception{
        System.out.println("---------------start----------------");
        String[] names={"小明","小红","小花"};
        Class test1=Test1.class;
        Method test=test1.getMethod("test",String.class);
        for (String name:names
             ) {
            test.invoke(test1.newInstance(),name);
        }
        System.out.println("----------------end---------------");
    }
    public void test(String name){
        System.out.println("test:"+name);
    }
}
