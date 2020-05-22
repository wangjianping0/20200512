package com.example.firstdemo.crypt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class AESDemo {

    private static final Logger logger = LoggerFactory.getLogger(AESDemo.class);

    private static final String PASSWORD = "testPassWord";

    public static void main(String[] args) throws Exception {
        //请求参数map
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("openId", "testOpenId");
        paramMap.put("userName", "testUserName");
//        String content = JsonUtil.getGson().toJson(paramMap);
        String content = JSON.toJSONString(paramMap);
        StringBuilder request = new StringBuilder();
        request.append("content=")
                .append(URLEncoder.encode(AESUtil.encrypt(content, PASSWORD), "UTF-8"))
                .append("&").append("sign=")
                .append(URLEncoder.encode(SignatureUtil.getSignature(content, PASSWORD), "UTF-8"));
        System.out.println("请求参数为:" + request.toString());
        Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(request.toString());
        String requestContent = split.get("content");
        String decryptReqContent = AESUtil.decrypt(decodeIfNecessary(requestContent), PASSWORD);
        Preconditions.checkArgument(decryptReqContent.equals(content));
        String requestSign = split.get("sign");
        Preconditions.checkArgument(SignatureUtil.getSignature(decodeIfNecessary(decryptReqContent),PASSWORD).equals(requestSign));
    }

    private static String decodeIfNecessary(String content) {
        try {
            if (!Strings.isNullOrEmpty(content) && content.indexOf("%") > 0) {
                return URLDecoder.decode(content, "UTF-8");
            }
            return content;
        } catch (Exception e) {
            logger.error("urlDecode失败", e);
            return content;
        }
    }
}
