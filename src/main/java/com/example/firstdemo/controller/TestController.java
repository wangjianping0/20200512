package com.example.firstdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.firstdemo.test.TestJDBCUtil;
import com.example.firstdemo.test.TestSign;
import com.example.firstdemo.util.FileUtils;
import com.example.firstdemo.util.JsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    TestJDBCUtil testJDBCUtil;
    @GetMapping (value = "/test" )
    public String test(){
        return "this is a test page.";
    }
    @PostMapping(value = "/getAllTeacher")
    @TestSign
    public List test2(Map map){
        System.out.println(JsonSerializer.serialize(map));
        return testJDBCUtil.selectAllTest();
    }
    @GetMapping(value = "/test3")
    public JSONObject test3(HttpServletRequest request){
        try {
            String result = FileUtils.readRaw(request.getInputStream());  //result就是raw中的数据
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=new JSONObject();
        return jsonObject;
    }
    @PostMapping(value = "/test4")
    @TestSign
    public Map<String,Object> test4(@RequestParam Map<String,Object> map){
        System.out.println(JsonSerializer.serialize(map));
        return map;
    }

}
