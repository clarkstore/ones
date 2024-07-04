package com.ones.common.web.util;

import cn.hutool.crypto.symmetric.AES;

import java.io.UnsupportedEncodingException;

/**
 * AES方法类
 * 前端配置resources/aesUtils.js使用
 *
 * @author Clark
 * @version 2021-09-09
 */
public class OsAesUtils {
    /**
     * 密钥 (需要前端和后端保持一致)
     */
    private String secret;

    private AES aes;

    public OsAesUtils(String secret) {
        this.secret = secret;
        if (aes == null) {
            try {
                aes = new AES(secret.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * aes解密
     *
     * @param encrypt 解密Hex（16进制）或Base64表示的字符串，默认UTF-8编码
     * @return 解密后字符串
     */
    public String aesDecrypt(String encrypt) {
        String decryptStr = aes.decryptStr(encrypt);
        return decryptStr;
    }

    /**
     * aes加密
     *
     * @param content
     * @return 加密后的Base64
     */
    public String aesEncrypt(String content) {
        String encryptBase64 = aes.encryptBase64(content);
        return encryptBase64;
    }
}
