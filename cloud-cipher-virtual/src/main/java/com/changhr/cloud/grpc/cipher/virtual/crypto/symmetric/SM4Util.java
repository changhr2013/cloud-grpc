package com.changhr.cloud.grpc.cipher.virtual.crypto.symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

/**
 * 国密 SM4 对称加密算法工具类
 *
 * @author changhr
 * @create 2019-05-08 10:21
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class SM4Util {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "SM4";

    /**
     * 密钥的字节长度
     */
    public static final int KEY_LENGTH = 16;

    /**
     * 密钥位长度
     */
    public static final int KEY_SIZE = 128;

    /**
     * 加解密算法/工作模式/填充方式
     * Java 7 支持 PKCS5Padding 填充方式
     * Bouncy Castle 支持 PKCS7Padding 填充方式
     */
    public static final String CIPHER_ALGORITHM = "SM4/ECB/PKCS5Padding";

    /**
     * 生成 SM4 对称密钥
     *
     * @return SM4 对称密钥，长度为 128 位
     */
    public static byte[] initKey() {
        // 实例化
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception: " + KEY_ALGORITHM, e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("no such provider exception: " + BouncyCastleProvider.PROVIDER_NAME, e);
        }
        keyGenerator.init(KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * SM4 对称密钥加密
     *
     * @param keyBytes SM4对称密钥
     * @param plain    待加密数据
     * @return byte[]  加密后的数据
     */
    public static byte[] encrypt(byte[] keyBytes, byte[] plain) {
        if (keyBytes.length != KEY_LENGTH) {
            throw new RuntimeException("error key length");
        }
        try {
            Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            Cipher out = Cipher.getInstance(CIPHER_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            out.init(Cipher.ENCRYPT_MODE, key);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException("sm4 encrypt error", e);
        }
    }

    /**
     * SM4 对称密钥解密
     *
     * @param keyBytes SM4 对称密钥
     * @param cipher   待解密的数据
     * @return byte[]  解密后的数据
     */
    public static byte[] decrypt(byte[] keyBytes, byte[] cipher) {
        if (keyBytes.length != KEY_LENGTH) {
            throw new RuntimeException("error key length");
        }
        try {
            Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            Cipher in = Cipher.getInstance(CIPHER_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            in.init(Cipher.DECRYPT_MODE, key);
            return in.doFinal(cipher);
        } catch (Exception e) {
            throw new RuntimeException("sm4 decrypt error", e);
        }
    }
}
