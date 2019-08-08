package com.changhr.cloud.grpc.cipher.virtual.crypto.symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * AES 对称加密算法工具类
 *
 * @author changhr
 * @create 2019-05-08 9:29
 */
@SuppressWarnings({"WeakerAccess", "unused", "Duplicates"})
public abstract class AESUtil {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

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
    public static final String ECB_NO_PADDING = "AES/ECB/NoPadding";
    public static final String ECB_PKCS_5_PADDING = "AES/ECB/PKCS5Padding";
    public static final String ECB_PKCS_7_PADDING = "AES/ECB/PKCS7Padding";
    public static final String GCM_NO_PADDING = "AES/GCM/NoPadding";

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
        return decrypt(data, key, ECB_PKCS_5_PADDING);
    }

    /**
     * 解密
     *
     * @param data            待解密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法/工作模式/填充模式
     * @return byte[] 解密的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key, final String cipherAlgorithm) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt error", e);
        }
    }

    /**
     * 解密，使用 BC 库 PKCS7Padding
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密的数据
     */
    public static byte[] decryptByPKCS7(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS_7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt error", e);
        }
    }

    /**
     * 生成用来初始化 GCMParameterSpec 的随机数
     * @return byte[] 12 个随机字节
     */
    public static byte[] generateGCMNonce() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception");
        }
        byte[] nonce = new byte[96 / Byte.SIZE];
        random.nextBytes(nonce);
        return nonce;
    }

    /**
     * 解密 AES-GCM 算法加密的数据
     *
     * @param data  待解密数据
     * @param key   AES 密钥
     * @param nonce 用来初始化 GCMParameterSpec 的随机数
     * @param aad   Associated data 关联数据
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptByGCM(byte[] data, byte[] key, byte[] nonce, byte[] aad) {
        // 还原密钥
        Key k = toKey(key);
        try {
            final Cipher cipher = Cipher.getInstance(GCM_NO_PADDING);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);

            // 初始化，设置为加密模式
            cipher.init(Cipher.DECRYPT_MODE, k, gcmParameterSpec);

            if (aad != null) {
                cipher.updateAAD(aad);
            }

            byte[] decrypted = new byte[cipher.getOutputSize(data.length)];

            int updateSize = cipher.update(data, 0, data.length, decrypted, 0);
            // 执行操作
            cipher.doFinal(decrypted, updateSize);
            return decrypted;
        } catch (Exception e) {
            throw new RuntimeException("AES-GCM decrypt error", e);
        }
    }

    public static byte[] decryptByGCM(byte[] data, byte[] key, byte[] nonce) {
        return decryptByGCM(data, key, nonce, null);
    }

    /**
     * 加密，使用 AES-GCM 算法
     *
     * @param data  待加密数据
     * @param key   AES 密钥
     * @param nonce 用来初始化 GCMParameterSpec 的随机数
     * @param aad   Associated data 关联数据
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptByGCM(byte[] data, byte[] key, byte[] nonce, byte[] aad) {
        // 还原密钥
        Key k = toKey(key);
        try {
            final Cipher cipher = Cipher.getInstance(GCM_NO_PADDING);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);

            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k, gcmParameterSpec);

            if (aad != null) {
                cipher.updateAAD(aad);
            }

            byte[] encrypted = new byte[cipher.getOutputSize(data.length)];

            int updateSize = cipher.update(data, 0, data.length, encrypted, 0);
            // 执行操作
            cipher.doFinal(encrypted, updateSize);
            return encrypted;
        } catch (Exception e) {
            throw new RuntimeException("AES-GCM encrypt error", e);
        }
    }

    public static byte[] encryptByGCM(byte[] data, byte[] key, byte[] nonce) {
        return encryptByGCM(data, key, nonce, null);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        return encrypt(data, key, ECB_PKCS_5_PADDING);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param key             密钥
     * @param cipherAlgorithm 算法/工作模式/填充模式
     * @return byte[] 加密的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key, final String cipherAlgorithm) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt error", e);
        }
    }

    /**
     * 加密，使用 BC 库 PKCS7Padding
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密的数据
     */
    public static byte[] encryptByPKCS7(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS_7_PADDING, BouncyCastleProvider.PROVIDER_NAME);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt error", e);
        }
    }

    /**
     * 生成密钥
     * 不指定密钥长度，默认为 256 位
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 生成密钥
     * 128、192、256 可选
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

    public static void main(String[] args) {
        byte[] key = AESUtil.initKey();
        byte[] data = "Nice to meet you!".getBytes(StandardCharsets.UTF_8);
        byte[] nonce = AESUtil.generateGCMNonce();
        byte[] aad = "aadKey".getBytes(StandardCharsets.UTF_8);

        byte[] encrypt = AESUtil.encryptByGCM(data, key, nonce);

        System.out.println(Hex.toHexString(encrypt));

        byte[] bytes = AESUtil.decryptByGCM(encrypt, key, nonce);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}
