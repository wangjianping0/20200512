package com.example.firstdemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class TestDemoMain {
    /***
     * 实例化公钥
     * @param keyContent
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey(String keyContent) throws Exception {
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyByte);
        return keyFactory.generatePublic(keySpec);
    }

    /***
     * 实例化私钥
     * @param keyContent
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String keyContent) throws Exception {
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
        return kf.generatePrivate(keySpec);
    }

    public static Map<String, Object> parseResponse(String strReq, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        JSONObject json = JSON.parseObject(strReq);
        String params = json.getString("params");
        String aesKey = json.getString("key");
        String sign = json.getString("sign");
        String timestamp = json.getString("timestamp");
        //对AESkey进行RSA解密
        aesKey = decryptRSA(aesKey, privateKey);
        //对params 进行AES解密
        params = decrypt(params, aesKey);

        return JSON.parseObject(params);
    }

    /***
     * 构造苏宁请求合作方报文
     * @param bizMap
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String buildRequest(Map<String, Object> bizMap, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        String params = JSON.toJSONString(bizMap);
        //时间戳
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        //构建签名
        SortedMap<String, String> map = new TreeMap<>();
        map.put("params", params);
        map.put("timestamp", timestamp);
        //待签名明文
        String plain = toSortedString(map);
        //做md5 摘要处理
        String md5 = MD5.digest(plain, StandardCharsets.UTF_8);
        //签名
        String sign = sign(md5, privateKey);
        map.put("sign", sign);

        //生成16位AES密钥
        String aesKey = generateAesKey();
        //对params进行AES加密，并替换进map
        params = encrypt(params, aesKey);
        map.replace("params", params);

        //对AES密钥进RSA加密，并将加密后的AES密钥加入map
        aesKey = encryptRSA(aesKey, publicKey);
        map.put("key", aesKey);
        return JSON.toJSONString(map);
    }

    /***
     *  合作方处理苏宁发起的请求
     * @param strReq
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parseRequest(String strReq, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        JSONObject json = JSON.parseObject(strReq);
        String params = json.getString("params");
        String aesKey = json.getString("key");
        String sign = json.getString("sign");
        String timestamp = json.getString("timestamp");
        //对AESkey进行RSA解密
        aesKey = decryptRSA(aesKey, privateKey);
        //对params 进行AES解密
        params = decrypt(params, aesKey);
        //构建验签明文
        TreeMap<String, String> map = new TreeMap<>();
        map.put(" params", params);

        map.put("timestamp", timestamp);
        String plain = toSortedString(map);
        //做nd5摘要
        String md5 = MD5.digest(plain, StandardCharsets.UTF_8);
        //验签
        boolean passed = verify(sign, md5, publicKey);
        if (!passed) {
            throw new RuntimeException("验签失败");
        }
        return JSON.parseObject(params);
    }

    /***
     * 合作方给苏宁的同步响应报文构建
     * @param bizMap
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String buildResponse(Map<String, Object> bizMap, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        JSONObject json = new JSONObject();
        TreeMap<String, String> map = new TreeMap<>();
        String params = JSON.toJSONString(bizMap);
        map.put("params", params);
        //处理签名
        String plain = toSortedString(map);
        String md5 = MD5.digest(plain, StandardCharsets.UTF_8);
        String sign = sign(md5, privateKey);
        json.put("sign", sign);
        //处理加密
        String aesKey = generateAesKey();
        params = encrypt(params, aesKey);
        json.put("params", params);
        json.put("key", encryptRSA(aesKey, publicKey));
        json.put("encrypt", Boolean.TRUE);
        return json.toJSONString();
    }


    public static String buildCallbackRequest(Map<String, Object> bizMap, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        String params = JSON.toJSONString(bizMap);
        TreeMap<String, String> map = new TreeMap<>();
        map.put("params", params);
        map.put("timestamp", DateTimeFormatter.ofPattern("yyyyMMddHmmss.").format(LocalDateTime.now()));
        map.put("appId", "");//固定,待苏宁分配
        map.put("version", "1.0");// 固定,由具体文档定
        String plain = toSortedString(map);
        // MD5
        String md5 = MD5.digest(plain, StandardCharsets.UTF_8);
        //签名
        String sign = sign(md5, privateKey);
        map.put("sign", sign);
        // AES密钥
        String aesKey = generateAesKey();
        //加密
        map.replace("params", encrypt(params, aesKey));
        map.put("key", encryptRSA(aesKey, publicKey));
        map.put("signType", "");//具体由接口文档定
        map.put("signkeyIndex", "");// 固定，由苏宁分配
        return JSON.toJSONString(map);
    }

    /**
     * 合作方处理苏宁的回调响应
     *
     * @param strResp
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static Map<String, Object> parseCallbackResponse(String strResp, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        JSONObject json = JSON.parseObject(strResp);
        String sign = (String) json.remove("gsSign");
        TreeMap<String, String> map = new TreeMap<>();
        json.forEach((k, v) -> map.put(k, v.toString()));
        String plain = toSortedString(map);
        String md5 = MD5.digest(plain, StandardCharsets.UTF_8);
        boolean verify = verify(sign, md5, publicKey);
        if (!verify) {
            throw new RuntimeException("验签失败");
        }
        String params = json.getString("params");
        Boolean encrypted = json.getBoolean(" encrypt");
        if (encrypted != null & encrypted.booleanValue()) {
            String aesKey = json.getString("key");
            aesKey = decryptRSA(aesKey, privateKey);
            params = decrypt(params, aesKey);

        }
        return JSON.parseObject(params);
    }

    /**
     * RSA签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * 验签
     *
     * @param sign
     * @param md5
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(String sign, String md5, PublicKey publicKey) throws Exception {
        byte[] signBytes = Base64.getDecoder().decode(sign);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey);
        signature.update(md5.getBytes(StandardCharsets.UTF_8));
        return signature.verify(signBytes);
    }

    /**
     * 将需要加签的数据拼接为'='和'&'的拼接形式
     *
     * @param map
     * @return
     */
    private static String toSortedString(SortedMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (true) {

            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey()).append('=').append(entry.getValue());

            if (it.hasNext()) {

                sb.append('&');
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 生成16位不重复的随机数,含数字+大小写
     *
     * @return
     */
    private static String generateAesKey() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            int type = rd.nextInt(3);
            switch (type) {
                case 0:

                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;

            }

        }
        return uid.toString();
    }

    /**
     * AES加密
     *
     * @param content
     * @param strKey
     * @return
     * @throws Exception
     */
    private static String encrypt(String content, String strKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES"); //NOSONAR
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return byte2hex(cipher.doFinal(byteContent));
    }

    /**
     * AES解密
     *
     * @param content
     * @param strKey
     * @return
     * @throws Exception
     */
    private static String decrypt(String content, String strKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(hex2byte(content)));
    }

    /**
     * RSA加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptRSA(String data, PublicKey publicKey) throws Exception {
        //对数据加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return byte2hex(cipher.doFinal(data.getBytes()));
    }

    /**
     * RSA解密
     *
     * @param message
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptRSA(String message, PrivateKey privateKey) throws Exception {
        byte[] bytes = hex2byte(message);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);

        byte[] result = cipher.doFinal(bytes);
        return new String(result);
    }

    /**
     * 16进制String转换byte[]
     *
     * @param str
     * @return
     */
    private static byte[] hex2byte(final String str) {
        if (str == null) {
            return new byte[]{};
        }
        String newStr = str.trim();
        int len = newStr.length();
        if (len <= 0 || len % 2 != 0) {
            return new byte[]{};
        }
        byte[] b = new byte[len / 2];

        try {
            for (int i = 0; i < newStr.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("ex" + newStr.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) { //NOSONAR
            return new byte[]{};
        }
    }

    /**
     * byte[]转16进制String
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        for (byte bi : b) {
            String temp = Integer.toHexString(bi & 0XFF);
            if (temp.length() == 1) {
                hs.append("0");

            }
            hs.append(temp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * MD5工具类
     */
    private static class MD5 {
        private static ThreadLocal threadLocal = new ThreadLocal() {

            protected synchronized Object initialValue() {
                MessageDigest messagedigest = null;
                try {
                    messagedigest = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException var3) {
                }
                return messagedigest;
            }
        };

        public MD5() {
        }

        public static MessageDigest getMessageDigest() {
            return (MessageDigest) threadLocal.get();

        }

        public static String digest(String s, Charset charset) {
            getMessageDigest().update(s.getBytes(charset));
            return bytesToHexString(getMessageDigest().digest());
        }
    }

    public static String bytesToHexString(byte[] bArray) {
        int length = bArray.length;
        StringBuffer sb = new StringBuffer(length);
        String sTemp;
        for (int i = 0; i < length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


}
