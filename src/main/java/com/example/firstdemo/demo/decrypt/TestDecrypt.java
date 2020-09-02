package com.example.firstdemo.demo.decrypt;

import com.example.firstdemo.util.TestDemoMain;
import org.apache.commons.codec.CharEncoding;
//import sun.misc.BASE64Decoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class TestDecrypt {
    private static final String encoding = CharEncoding.UTF_8;

    /**
     * 请求解密,验签
     *
     * @param resp
     * @return
     * @throws Exception
     */
    public static Map decryptRequest(String resp) throws Exception {
        Properties properties = new Properties();
        properties.load(TestDecrypt.class.getResourceAsStream("config.properties"));
        //获取自己的私钥
        String privateKey = properties.getProperty("private_ _key");
        //获取对方的公钥
        String channelPublicKey = properties.getProperty(" channe1_ .public_ key");
        Map<String, Object> params = TestDemoMain.parseResponse(resp, getPrivateKey(privateKey), getPubKey(channelPublicKey));
        return params;
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
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
        return kf.generatePrivate(keySpec);
    }

    public static void main(String[] args) {
        System.out.println("*************************************************");//com/example/firstdemo/demo/decrypt/config.properties
        try{
            Properties properties = new Properties();
//            Class testDecrypt=TestDecrypt.class;
//            InputStream inputStream1=testDecrypt.getResourceAsStream("test.txt");
//            InputStream inputStream=testDecrypt.getResourceAsStream("config.properties");
//            properties.load(inputStream);
        properties.load(new FileInputStream("src/main/java/com/example/firstdemo/demo/decrypt/config.properties") );
            //获取自己的私钥
            String privateKey = properties.getProperty("private_key");
            System.out.println(privateKey);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("*************************************************");
    }

}