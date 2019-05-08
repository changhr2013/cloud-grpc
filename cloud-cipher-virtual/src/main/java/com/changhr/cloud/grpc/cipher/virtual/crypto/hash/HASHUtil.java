package com.changhr.cloud.grpc.cipher.virtual.crypto.hash;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * hash 摘要算法工具类
 *
 * @author changhr
 * @create 2019-05-08 12:45
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class HASHUtil {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    public static final String HASH_MD5 = "MD5";

    public static final String HASH_SHA256 = "SHA-256";

    /**
     * MD5 消息摘要
     * @param data  待做摘要处理的数据
     * @return  byte[] 消息摘要
     */
    public static byte[] encodeMD5(byte[] data) {
        // 初始化 MessageDigest
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(HASH_MD5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HASH_MD5, e);
        }
        // 执行消息摘要
        return digest.digest(data);
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
     * SHA-256 消息摘要
     * @param data  待做摘要处理的数据
     * @return  byte[] 消息摘要
     */
    public static byte[] encodeSHA256(byte[] data) {
        // 初始化 MessageDigest
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(HASH_SHA256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + HASH_SHA256, e);
        }
        // 执行消息摘要
        return digest.digest(data);
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
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(data, 0, data.length);
        byte[] result = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(result, 0);
        return result;
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
}
