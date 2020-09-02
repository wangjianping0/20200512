package com.example.firstdemo.demo.encrypt;


import com.alibaba.fastjson.JSON;
import com.example.firstdemo.util.JsonSerializer;
import com.example.firstdemo.util.TestDemoMain;
//import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class TestEncrypt {
    public static String encryptRequest(String fileName) throws Exception{
        Properties properties=new Properties();
        properties.load(TestEncrypt.class.getResourceAsStream("config.properties"));
        //获取合作方公钥
        String channelPublicKey=properties.getProperty("channel_public_key");
        //获取我方私钥
        String privateKey=properties.getProperty("private_key");
        File file=new File(fileName);
        StringBuffer lineTxt=new StringBuffer();
        String lineTxtTemp=new String();

        try{
            String encoding="UTF-8";
            if(file.isFile()&&file.exists()){
                InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                while((lineTxtTemp=bufferedReader.readLine())!=null){
                    lineTxt.append(lineTxtTemp);
                }
                inputStreamReader.close();
                System.out.println(lineTxt.toString());
            }
        }catch (Exception e){
            System.out.println("读取文件错误");
            e.printStackTrace();
        }
        Map<String,Object> bizMap= JsonSerializer.deserializeMap(lineTxt.toString());
        String request= TestDemoMain.buildRequest(bizMap,getPrivateKey(privateKey),getPubKey(channelPublicKey));
        return request;
    }

    /***
     * 实例化公钥
     * @param keyContent
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey(String keyContent) throws Exception {
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
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
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
        return kf.generatePrivate(keySpec);
    }

}
