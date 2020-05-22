package com.example.firstdemo.test;

import com.google.common.base.Splitter;

import java.io.File;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        System.out.println("************************************************");
        String reqContent="abc";
        String sign="def";
        String response="content=" + reqContent + "&sign=" + sign;
        Iterable<String> it=Splitter.on("&").split(response);
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(response.toString());
        System.out.println("************************************************");
    }
}
