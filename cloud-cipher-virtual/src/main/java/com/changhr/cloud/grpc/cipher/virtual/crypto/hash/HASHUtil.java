package com.changhr.cloud.grpc.cipher.virtual.crypto.hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

/**
 * hash 摘要算法工具类
 *
 * @author changhr
 * @create 2019-05-08 12:45
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class HASHUtil {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    public static final String HASH_MD5 = "MD5";

    public static final String HASH_SHA1 = "SHA-1";

    public static final String HASH_SHA256 = "SHA-256";

    public static final String HASH_SM3 = "SM3";

    /**
     * MD5 消息摘要
     * @param data  待做摘要处理的数据
     * @return  byte[] 消息摘要
     */
    public static byte[] encodeMD5(byte[] data) {
        return encodeHash(data, HASH_MD5);
    }

    public static byte[] encodeMD5(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return encodeMD5(data);
    }

    public static String encodeMD5Hex(byte[] data) {
        return Hex.toHexString(encodeMD5(data));
    }

    public static String encodeMD5Hex(String text) {
        return Hex.toHexString(encodeMD5(text));
    }

    /**
     * SHA-1 消息摘要
     * @param data  待做摘要处理的数据
     * @return  byte[] 消息摘要
     */
    public static byte[] encodeSHA1(byte[] data) {
        return encodeHash(data, HASH_SHA1);
    }

    public static byte[] encodeSHA1(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return encodeSHA1(data);
    }

    public static String encodeSHA1Hex(byte[] data) {
        return Hex.toHexString(encodeSHA1(data));
    }

    public static String encodeSHA1Hex(String text) {
        return Hex.toHexString(encodeSHA1(text));
    }

    /**
     * SHA-256 消息摘要
     * @param data  待做摘要处理的数据
     * @return  byte[] 消息摘要
     */
    public static byte[] encodeSHA256(byte[] data) {
        return encodeHash(data, HASH_SHA256);
    }

    public static byte[] encodeSHA256(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return encodeSHA256(data);
    }

    public static String encodeSHA256Hex(byte[] data) {
        return Hex.toHexString(encodeSHA256(data));
    }

    public static String encodeSHA256Hex(String text) {
        return Hex.toHexString(encodeSHA256(text));
    }

    /**
     * SM3 消息摘要
     *
     * @param data 待 hash 的数据
     * @return byte[] SM3 hash 值
     */
    public static byte[] encodeSM3(byte[] data) {
        return encodeHash(data, HASH_SM3, BouncyCastleProvider.PROVIDER_NAME);
    }

    public static byte[] encodeSM3(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return encodeSM3(data);
    }

    public static String encodeSM3Hex(byte[] data) {
        return Hex.toHexString(encodeSM3(data));
    }

    public static String encodeSM3Hex(String text) {
        return Hex.toHexString(encodeSM3(text));
    }

    /**
     * 通用消息摘要
     * @param data          待做摘要的数据
     * @param hashAlgorithm 摘要的算法类型
     * @return  byte[] 消息摘要
     */
    private static byte[] encodeHash(byte[] data, final String hashAlgorithm) {
        // 初始化 MessageDigest
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + hashAlgorithm, e);
        }
        // 执行消息摘要
        return digest.digest(data);
    }

    /**
     * 通用消息摘要
     * @param data          待做摘要的数据
     * @param hashAlgorithm 摘要的算法类型
     * @param provider      jce 的提供者
     * @return  byte[] 消息摘要
     */
    private static byte[] encodeHash(byte[] data, final String hashAlgorithm, String provider) {
        // 初始化 MessageDigest
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(hashAlgorithm, provider);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + hashAlgorithm, e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("no such provider exception: " + provider, e);
        }
        // 执行消息摘要
        return digest.digest(data);
    }
}
