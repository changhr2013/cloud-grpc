package com.changhr.cloud.grpc.cipher.virtual.crypto.hash;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * MAC 摘要算法工具类
 *
 * @author changhr
 * @create 2019-05-08 10:53
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HMACUtil {

    // 密钥算法类型
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * 初始化 HmacMD5 密钥
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initHmacMD5Key() {
        // 初始化 KeyGenerator
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(HMAC_MD5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HMAC_MD5);
        }
        // 产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * 初始化 Hex 形式的 HmacMD5 密钥
     *
     * @return String 16进制字符串密钥
     */
    public static String initHmacMD5KeyHex() {
        return Hex.toHexString(initHmacMD5Key());
    }

    /**
     * 初始化 base64 形式的 HmacMD5 密钥
     *
     * @return String base64字符串密钥
     */
    public static String initHmacMD5KeyBase64() {
        return Base64.getEncoder().encodeToString(initHmacMD5Key());
    }

    /**
     * HmacMD5 消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[] 消息摘要
     */
    public static byte[] encodeHmacMD5(byte[] data, byte[] key) {
        // 还原密钥
        SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_MD5);
        // 实例化 Mac
        Mac mac;
        try {
            mac = Mac.getInstance(secretKey.getAlgorithm());
            // 初始化 Mac
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HMAC_MD5);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("invalid key exception");
        }
        // 执行消息摘要
        return mac.doFinal(data);
    }

    public static String encodeHmacMD5ToBase64(byte[] data, byte[] key) {
        return Base64.getEncoder().encodeToString(encodeHmacMD5(data, key));
    }

    public static String encodeHmacMD5ToHex(byte[] data, byte[] key) {
        return Hex.toHexString(encodeHmacMD5(data, key));
    }

    public static byte[] encodeHmacMD5WithHexKey(String text, String hexKey) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        byte[] key = Hex.decode(hexKey);
        return encodeHmacMD5(data, key);
    }

    public static String encodeHmacMD5WithHexKeyToBase64(String text, String hexKey) {
        return Base64.getEncoder().encodeToString(encodeHmacMD5WithHexKey(text, hexKey));
    }

    public static String encodeHmacMD5WithHexKeyToHex(String text, String hexKey) {
        return Hex.toHexString(encodeHmacMD5WithHexKey(text, hexKey));
    }

    public static byte[] encodeHmacMD5WithBase64Key(String text, String base64Key) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        byte[] key = Base64.getDecoder().decode(base64Key);
        return encodeHmacMD5(data, key);
    }

    public static String encodeHmacMD5WithBase64KeyToBase64(String text, String base64Key) {
        return Base64.getEncoder().encodeToString(encodeHmacMD5WithBase64Key(text, base64Key));
    }

    public static String encodeHmacMD5WithBase64KeyToHex(String text, String base64Key) {
        return Hex.toHexString(encodeHmacMD5WithBase64Key(text, base64Key));
    }

    /**
     * 初始化 HmacSHA256 密钥
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initHmacSHA256Key() {
        // 初始化 KeyGenerator
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(HMAC_SHA256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HMAC_SHA256);
        }
        // 产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * 初始化 Hex 形式的 HmacSHA256 密钥
     *
     * @return String 16进制字符串密钥
     */
    public static String initHmacSHA256KeyHex() {
        return Hex.toHexString(initHmacSHA256Key());
    }

    /**
     * 初始化 base64 形式的 HmacSHA256 密钥
     *
     * @return String base64字符串密钥
     */
    public static String initHmacSHA256KeyBase64() {
        return Base64.getEncoder().encodeToString(initHmacSHA256Key());
    }

    /**
     * HmacSHA256 消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[] 消息摘要
     */
    public static byte[] encodeHmacSHA256(byte[] data, byte[] key) {
        // 还原密钥
        SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_SHA256);
        // 实例化 Mac
        Mac mac;
        try {
            mac = Mac.getInstance(secretKey.getAlgorithm());
            // 初始化 Mac
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HMAC_SHA256);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("invalid HmacSHA256 key exception");
        }
        // 执行消息摘要
        return mac.doFinal(data);
    }

    public static String encodeHmacSHA256ToBase64(byte[] data, byte[] key) {
        return Base64.getEncoder().encodeToString(encodeHmacSHA256(data, key));
    }

    public static String encodeHmacSHA256ToHex(byte[] data, byte[] key) {
        return Hex.toHexString(encodeHmacSHA256(data, key));
    }

    public static byte[] encodeHmacSHA256WithHexKey(String text, String hexKey) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        byte[] key = Hex.decode(hexKey);
        return encodeHmacSHA256(data, key);
    }

    public static String encodeHmacSHA256WithHexKeyToBase64(String text, String hexKey) {
        return Base64.getEncoder().encodeToString(encodeHmacSHA256WithHexKey(text, hexKey));
    }

    public static String encodeHmacSHA256WithHexKeyToHex(String text, String hexKey) {
        return Hex.toHexString(encodeHmacSHA256WithHexKey(text, hexKey));
    }

    public static byte[] encodeHmacSHA256WithBase64Key(String text, String base64Key) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        byte[] key = Base64.getDecoder().decode(base64Key);
        return encodeHmacSHA256(data, key);
    }

    public static String encodeHmacSHA256WithBase64KeyToBase64(String text, String base64Key) {
        return Base64.getEncoder().encodeToString(encodeHmacSHA256WithBase64Key(text, base64Key));
    }

    public static String encodeHmacSHA256WithBase64KeyToHex(String text, String base64Key) {
        return Hex.toHexString(encodeHmacSHA256WithBase64Key(text, base64Key));
    }
}
