package com.changhr.cloud.grpc.cipher.virtual.crypto.hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

/**
 * PBKDF2 密钥生成算法工具类
 *
 * @author changhr
 * @create 2019-08-07 10:01
 */
public abstract class PBKDF2Util {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    public static final String PBKDF2_WITH_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

    private static final int DEFAULT_LOOP = 4096;

    private static final int DEFAULT_KEY_SIZE = 256;

    /**
     * 生成 PBKDF2 密钥
     * @param password  密码
     * @param salt      盐
     * @param loop      循环 hash 次数
     * @param keySize   密钥长度
     * @param algorithm 算法类型
     * @return byte[]   PBKDF2 密钥
     */
    public static byte[] generateKey(String password, byte[] salt, int loop, int keySize, final String algorithm) {
        SecretKeyFactory factory;
        try {
             factory = SecretKeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + algorithm);
        }

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, loop, keySize);
        Key key;
        try {
            key = factory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("invalid key spec exception!", e);
        }
        return key.getEncoded();
    }

    public static byte[] generateKey(String password, byte[] salt, int loop, int keySize) {
        return generateKey(password, salt, loop, keySize, PBKDF2_WITH_HMAC_SHA256);
    }

    public static byte[] generateKey(String password, byte[] salt, int loop) {
        return generateKey(password, salt, loop, DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(String password, byte[] salt) {
        return generateKey(password, salt, DEFAULT_LOOP, DEFAULT_KEY_SIZE);
    }
}
