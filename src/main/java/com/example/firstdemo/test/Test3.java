package com.example.firstdemo.test;

import com.alibaba.fastjson.JSON;
import com.example.firstdemo.util.JsonSerializer;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test3 {
    public static void main(String[] args) {
        System.out.println("这是一个测试汉字是否乱码的demo");
        System.out.println("这是一个测试汉字是否乱码的demo");
        System.out.println("这是一个测试汉字是否乱码的demo");
        System.out.println("这是一个测试汉字是否乱码的demo");
        System.out.println("这是一个测试汉字是否乱码的demo");
        /*Map<String,Object> map=new HashMap<>();
        map.put("name","校长");
        map.put("age","123");

        String serialize=JsonSerializer.serialize(map);
        Demo demo1=JsonSerializer.deserialize(serialize,Demo.class);
        System.out.println(JSON.toJSONString(demo1));
        System.out.println("***********************************************");
        Demo demo=new Demo();
        demo.setName("测试");
        System.out.println(demo.equals(Demo.class));

        Class c=Demo.class;
        Field[] declaredFields=c.getDeclaredFields();
        Field[] fields=c.getFields();
        System.out.println("getDeclaredFields():");
        for (Field field:declaredFields ) {
            System.out.println(field.getName());
        }
        System.out.println("--------------------------------------");
        System.out.println("getFields():");
        for (Field field:declaredFields ) {
            System.out.println(field.getName());
        }*/
    }
    @Data
    static
    class Demo{
        private String name;
        private String sex;

    }
}
