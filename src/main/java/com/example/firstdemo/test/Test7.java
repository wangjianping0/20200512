package com.example.firstdemo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

public class Test7 {
    public static void main(String[] args) throws Exception{
        File file=new File("src/main/resources/static/doc/项目新建及配置.doc");
        InputStream inputStream=new FileInputStream(file);
        int count=inputStream.read();
        System.out.println("-----"+count);

    }
}
