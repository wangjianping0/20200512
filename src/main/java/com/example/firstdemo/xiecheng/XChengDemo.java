package com.example.firstdemo.xiecheng;

import com.google.common.base.Splitter;
import org.apache.commons.codec.Charsets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Properties;

public class XChengDemo {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/java/com/example/firstdemo/xiecheng/config.properties"));
            //公钥
            String public_key = properties.getProperty("public_key");
            //私钥
            String private_key = properties.getProperty("private_key");
            //三方公钥
            String third_public_key = properties.getProperty("third_public_key");
            //三方私钥
            String third_private_key = properties.getProperty("third_private_key");
            /****************************************加密********************************************/
            String fileName = "src/main/java/com/example/firstdemo/demo/file/test.txt";
            File file = new File(fileName);
            StringBuffer lineTxt = new StringBuffer();
            String lineTxtTemp = new String();

            try {
                String encoding = Charsets.UTF_8.name();
                if (file.isFile() && file.exists()) {
                    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while ((lineTxtTemp = bufferedReader.readLine()) != null) {
                        lineTxt.append(lineTxtTemp);
                    }
                    inputStreamReader.close();
                    System.out.println(lineTxt.toString());
                }
            } catch (Exception e) {
                System.out.println("读取文件错误");
                e.printStackTrace();
            }
            String request = lineTxt.toString();
            PrivateKey privateKey = XChengRSAUtil.initPrivateKeyByContent(private_key);
            PublicKey thirdPublicKey = XChengRSAUtil.initPublicKeyByContent(third_public_key);
            //请求内容加密,第三方用自己私钥解密
            String reqContent = XChengRSAUtil.encrypt(request, Charsets.UTF_8.name(), thirdPublicKey);
            //请求内容签名
            String sign = XChengRSAUtil.signWithMD5(request, Charsets.UTF_8.name(), privateKey);
//        reqContent= URLEncoder.encode(reqContent,Charsets.UTF_8.name());
//        sign= URLEncoder.encode(sign,Charsets.UTF_8.name());
            String response = "content=" + reqContent + "&sign=" + sign;
            System.out.println("加密后的数据:"+response);
            /*****************************************解密*******************************************/
            PrivateKey thirdPrivateKey = XChengRSAUtil.initPrivateKeyByContent(third_private_key);
            PublicKey publicKey = XChengRSAUtil.initPublicKeyByContent(public_key);
            /*Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=").split(response.toString());
            String requestContent = split.get("content");
            String requestSign = split.get("sign");*/
            String requestContent = reqContent;
            String requestSign = sign;
            //解密
            String contentJson = XChengRSAUtil.decrypt(requestContent, Charsets.UTF_8.name(), thirdPrivateKey);
            //验签
            if (!XChengRSAUtil.verifyWithMD5(contentJson, requestSign, Charsets.UTF_8.name(), publicKey)) {
                System.out.println("验签失败!");
                return;
            }
            //
            System.out.println("解密后的数据:" + contentJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
