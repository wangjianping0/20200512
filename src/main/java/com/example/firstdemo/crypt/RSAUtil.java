package com.example.firstdemo.crypt;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *  RSA常用
 *
 * @author andang.ye
 */
public class RSAUtil {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 加解密算法
     */
    private static final String ENCRYPT_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHM = "MD5withRSA";

    /**
     * PKCS8文件初始化为PrivateKey.
     *
     * @param keyPath
     * @return
     * @throws Exception
     */
    public static PrivateKey initPrivateKey(String keyPath) throws Exception {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(keyPath));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = bufferedReader.readLine())!=null) {
                if(line.length()==0 || line.charAt(0)=='-'){
                    continue;
                }
                stringBuilder.append(line).append("\r");
            }

            return initPrivateKeyByContent(stringBuilder.toString());
        } finally {
            org.apache.tomcat.util.http.fileupload.IOUtils.closeQuietly(bufferedReader);
        }
    }

    public static PrivateKey initPrivateKeyByContent(String keyContent) throws Exception {
        Preconditions.checkNotNull(keyContent);

//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory kf = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
        return kf.generatePrivate(keySpec);
}

    /**
     * 公钥初始化
     *
     * @param keyPath
     * @return
     */
    public static PublicKey initPublicKey(String keyPath) throws Exception{

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(keyPath));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = bufferedReader.readLine())!=null) {
                if(line.length()==0 || line.charAt(0)=='-'){
                    continue;
                }
                stringBuilder.append(line).append("\r");
            }

            return initPublicKeyByContent(stringBuilder.toString());
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
    }

    /**
     * 公钥初始化
     * @param keyContent 证书内容
     * @return 公钥
     * @throws Exception
     */
    public static PublicKey initPublicKeyByContent(String keyContent) throws Exception{
        Preconditions.checkNotNull(keyContent, "检查安全存储配置或是否已初始化");

//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] keyByte = base64Decoder.decodeBuffer(keyContent);
// 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] keyByte = base64Decoder.decode(keyContent);
        KeyFactory kf = KeyFactory.getInstance(ENCRYPT_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyByte);
        return kf.generatePublic(keySpec);
    }

    /**
     * 签名算法: MD5withRSA
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception 签名异常直接抛出
     */
    public static String signWithMD5(String content, String charset,PrivateKey privateKey) throws Exception{
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(content.getBytes(charset));

        byte[] signByte = signature.sign();

//        BASE64Encoder base64Encoder = new BASE64Encoder();
//
//        return base64Encoder.encodeBuffer(signByte);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(signByte);
    }

    /**
     * 验签:MD5whtiRSA ,异常吞掉,返回验签失败
     *
     * @param content
     * @param sign
     * @param charset
     * @param publicKey
     * @return
     */
    public static boolean verifyWithMD5(String content, String sign, String charset, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(content.getBytes(charset));

//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] keyByte = base64Decoder.decodeBuffer(sign);
// 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
            Base64.Decoder base64Decoder = Base64.getDecoder();
            byte[] keyByte = base64Decoder.decode(sign);
            return signature.verify(keyByte);
        } catch (Exception e) {
            logger.error("验签出现异常",e);
            return false;
        }
    }

    /**
     * 加密
     *
     * @param content 待加密内容
     * @param charset 字符集
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String content,String charset ,Key key) throws Exception {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(content), "待加密内容不能为空");
        Preconditions.checkArgument(key instanceof RSAKey, "key类型必须为RSAKey");
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] data = content.getBytes(charset);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        int maxEncryptBlock = calcMaxEncryptBlock((RSAKey)key);
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }

//        BASE64Encoder base64Encoder = new BASE64Encoder();
//
//        return base64Encoder.encodeBuffer(out.toByteArray());
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Encoder
        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(out.toByteArray());
    }

    /**
     * 解密
     *
     * @param encryptedContent 待解密内容
     * @param charset 字符集
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptedContent,String charset ,Key key) throws Exception {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(encryptedContent), "待解密内容不能为空");
        Preconditions.checkArgument(key instanceof RSAKey, "key类型必须为RSAKey");
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,key);

//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] encryptedData = base64Decoder.decodeBuffer(encryptedContent);
        // 从JKD 9开始rt.jar包已废除，从JDK 1.8开始使用java.util.Base64.Decoder
        Base64.Decoder base64Decoder = Base64.getDecoder();
        byte[] encryptedData = base64Decoder.decode(encryptedContent);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        int maxDecryptBlock = calcMaxDecryptBlock((RSAKey) key);
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxDecryptBlock) {
                cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxDecryptBlock;
        }

        return new String(out.toByteArray(),charset);
    }


    //计算加密分块最大长度
    private static int calcMaxEncryptBlock(RSAKey key) {
        return key.getModulus().bitLength()/8 - 11;
    }
    //计算解密分块最大长度
    private static int calcMaxDecryptBlock(RSAKey key) {
        return key.getModulus().bitLength()/8;
    }

}
