package com.changhr.cloud.grpc.cipher.virtual.crypto.symmetric;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES 对称加密算法工具类
 *
 * @author changhr
 * @create 2019-05-08 9:29
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class AESUtil {

    /**
     * 密钥算法类型
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥的默认位长度
     */
    public static final int KEY_SIZE = 256;

    /**
     * 加解密算法/工作模式/填充方式
     * Java 7 支持 PKCS5Padding 填充方式
     * Bouncy Castle 支持 PKCS7Padding 填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    private static Key toKey(byte[] key) {
        // 实例化 DES 密钥材料
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);
        try {
            /*
             * 实例化
             * 使用 PKCS7Padding 填充方式，按如下方式实现
             * Cipher.getInstance(CIPHER_ALGORITHM, "BC")
             */
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt error", e);
        }
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);
        try {
            /*
             * 实例化
             * 使用 PKCS7Padding 填充方式，按如下方式实现
             * Cipher.getInstance(CIPHER_ALGORITHM, "BC")
             */
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt error", e);
        }
    }

    /**
     * 生成密钥 不指定密钥长度，默认为 256 位
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 生成密钥
     *
     * @param keySize 密钥长度
     * @return byte[] 二进制密钥
     */
    public static byte[] initKey(int keySize) {
        // AES 要求密钥长度为 128 位、192 位或 256 位
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new RuntimeException("error keySize: " + keySize);
        }
        // 实例化
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + KEY_ALGORITHM, e);
        }
        keyGenerator.init(keySize);
        // 生成秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }
}