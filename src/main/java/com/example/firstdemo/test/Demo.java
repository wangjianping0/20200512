package com.example.firstdemo.test;

import com.alibaba.fastjson.JSON;
import com.example.firstdemo.demo.decrypt.TestDecrypt;
import com.example.firstdemo.demo.encrypt.TestEncrypt;

import java.util.Map;

public class Demo {
    public static void main(String[] args) throws Exception {

        String fileName="src/main/java/com/example/firstdemo/demo/file/test.txt";
        String json= TestEncrypt.encryptRequest(fileName);
        System.out.println("请求报文:"+json);
        System.out.println("发送请求...");
        System.out.println("****************************************************************");
        System.out.println("接受请求...");
        System.out.println("响应报文:"+json);
        Map<String,Object> params= TestDecrypt.decryptRequest(json);
        System.out.println("解密后数据:"+ JSON.toJSONString(params));

    }
}
