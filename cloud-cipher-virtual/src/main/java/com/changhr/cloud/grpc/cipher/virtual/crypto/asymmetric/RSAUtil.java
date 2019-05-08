package com.changhr.cloud.grpc.cipher.virtual.crypto.asymmetric;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 非对称加密算法工具类
 *
 * @author changhr
 * @create 2019-05-08 14:52
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RSAUtil {

    /**
     * 密钥算法类型
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA 密钥长度，默认 2048 位
     * 密钥长度必须是 64 的倍数，范围在 512 ~ 65535 位之间
     */
    private static final int KEY_SIZE = 2048;

    /**
     * 签名
     *
     * @param data   待签名数据
     * @param priKey 私钥
     * @return byte[] 数字签名
     */
    public static byte[] sign(byte[] data, byte[] priKey) {
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 获取私钥对象
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 实例化 Signature
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 初始化 Signature
            signature.initSign(privateKey);
            // 更新
            signature.update(data);
            // 签名
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验签
     *
     * @param data   待验签数据
     * @param pubKey 公钥
     * @param sign   数字签名
     * @return boolean 验签通过返回 true，失败返回 false
     */
    public static boolean verify(byte[] data, byte[] pubKey, byte[] sign) {
        // 获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 实例化 Signature
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 初始化 Signature
            signature.initVerify(publicKey);
            // 更新
            signature.update(data);
            // 验证
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data   待解密数据
     * @param priKey 私钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] priKey) {
        // 获取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data   待加密数据
     * @param pubKey 私钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] pubKey) {
        // 获取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pubKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data   待解密数据
     * @param pubKey 公钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] pubKey) {
        // 获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data   待加密数据
     * @param pubKey 公钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] pubKey) {
        // 获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥 Map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥 Map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 初始化密钥
     *
     * @return 密钥 Map
     */
    public static Map<String, Object> initKey() {
        return initKey(KEY_SIZE);
    }

    /**
     * 初始化密钥
     *
     * @param keySize 密钥长度
     * @return 密钥 Map
     */
    public static Map<String, Object> initKey(int keySize) {
        // 实例化密钥对生成器
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm: " + KEY_ALGORITHM, e);
        }
        // 初始化密钥对生成器
        keyPairGen.initialize(keySize);
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 封装密钥
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
}
