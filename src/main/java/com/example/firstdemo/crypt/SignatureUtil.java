package com.example.firstdemo.crypt;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名用的工具类
 */
public class SignatureUtil {

    /**
     * 签名方式为 md5(map非空属性按照自然顺序排序 + signKey)
     *
     * @param content     要签名的内容
     * @param signKey 签名用的key, 可以为空字符串, 但不能为 null
     * @return 签名
     */
    public static String getSignature(String content, String signKey) {
        return Hashing.md5().newHasher().putString(content + signKey, Charsets.UTF_8).hash().toString();
    }
}
