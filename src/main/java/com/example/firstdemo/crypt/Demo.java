package com.example.firstdemo.crypt;

import org.apache.commons.codec.Charsets;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

public class Demo {
    /**
     * 发送请求(Q-C), 加密签名栗子
     * <p>
     *    秘钥使用规范 (统一用自己的私钥解密， 私钥签名，对方公钥验签)
     * </p>
     *
     * @throws Exception
     */
    public void sendRequest() throws Exception {
        // 1、 双方准备秘钥对、交换公钥
        String qunarPriKeyStr = "pri-key";
        PrivateKey qunarPriKey = RSAUtil.initPrivateKeyByContent(qunarPriKeyStr);

        String qunarPubKeyStr = "pubx-key";
        PublicKey qunarPublicKey = RSAUtil.initPublicKeyByContent(qunarPubKeyStr);

        String thirdPubKeyStr = "third-pub-key";
        PublicKey thirdPubKey = RSAUtil.initPublicKeyByContent(thirdPubKeyStr);

        // 2、 请求参数转换为json
        String contentJson = "{\"detail_code\":\"\",\"cl_result\":\"1\"}";

        // 2.1 请求内容加密,第三方用自己私钥解密
        String reqContent = RSAUtil.encrypt(contentJson, "UTF-8", thirdPubKey);

        // 2.2 请求内容签名
        String sign = RSAUtil.signWithMD5(contentJson, "UTF-8", qunarPriKey);

        // 3. 发送请求, 用content & sign作为key；
        // httpClient.doPost("url", "content=" + reqContent + "&sign=" + sign, "UTF_8");
    }

    /**
     *  处理请求，解密、验签
     *  <p>
     *    秘钥使用规范 (统一用自己的私钥解密， 私钥签名，对方公钥验签)
     * </p>
     *
     * @throws Exception
     */
    public void processRequest() throws Exception {
        // 1、初始化秘钥
        String thirdPriKeyStr = "third-pri-key";
        PrivateKey thirdPriKey = RSAUtil.initPrivateKeyByContent(thirdPriKeyStr);

        String qunarPubKeyStr = "pubx-key";
        PublicKey qunarPublicKey = RSAUtil.initPublicKeyByContent(qunarPubKeyStr);

        // 收到请求串
        String reqQueryStr = "content=xx&sign=xxx..";
        Map<String, String> response = null; // reqQueryStr.parse

        // 2、 解密、验签处理

        // 2.1 解密
        String contentJson = RSAUtil.decrypt(response.get("content"), "UTF-8", thirdPriKey);
        // 2.2 验签
        RSAUtil.verifyWithMD5(contentJson, response.get("sign"), Charsets.UTF_8.name(), qunarPublicKey);

        // 3. 数据合法, 做业务
        // "{\"detail_code\":\"\",\"cl_result\":\"1\"}"
    }
}
