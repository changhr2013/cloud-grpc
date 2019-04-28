package com.changhr.cloud.grpc.cipher.virtual.utils;

import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * 国密算法工具类
 *
 * @author changhr
 */
@SuppressWarnings({"WeakerAccess", "unused", "AlibabaClassNamingShouldBeCamel"})
public class GMUtil {

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");

    private static final byte[] USER_ID = "1234567812345678".getBytes();

    private static final int RS_LEN = 32;

    private static ECDomainParameters ecDomainParameters =
            new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    private static ECParameterSpec ecParameterSpec =
            new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    /**
     * 使用私钥对数据签名，结果为直接拼接 rs 的字节数组
     *
     * @param msg        待签名数据
     * @param userId     签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param privateKey 私钥
     * @return r||s，直接拼接 byte 数组的 rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, byte[] userId, PrivateKey privateKey) {
        return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, userId, privateKey));
    }

    /**
     * 使用私钥对数据签名，结果为直接拼接 rs 的字节数组
     *
     * @param msg        待签名数据
     * @param privateKey 私钥
     * @return r||s，直接拼接 byte 数组的 rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, PrivateKey privateKey) {
        return signSm3WithSm2(msg, USER_ID, privateKey);
    }

    /**
     * 使用私钥对数据签名，结果为 ASN1 格式的 rs 的字节数组
     *
     * @param msg        待签名数据
     * @param userId     签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param privateKey 私钥
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId, PrivateKey privateKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", BouncyCastleProvider.PROVIDER_NAME);
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            return signer.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用私钥对数据签名，结果为 ASN1 格式的 rs 的字节数组
     *
     * @param msg        待签名数据
     * @param privateKey 私钥
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, PrivateKey privateKey) {
        return signSm3WithSm2Asn1Rs(msg, USER_ID, privateKey);
    }

    /**
     * 验证直接拼接 rs 的签名
     *
     * @param msg       待验签的数据
     * @param userId    签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param rs        r||s，直接拼接 byte 数组的 rs
     * @param publicKey 公钥
     * @return boolean
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs), publicKey);
    }

    /**
     * 验证直接拼接 rs 的签名
     *
     * @param msg       待验签的数据
     * @param rs        签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param publicKey 公钥
     * @return boolean
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] rs, PublicKey publicKey) {
        return verifySm3WithSm2(msg, USER_ID, rs, publicKey);
    }

    /**
     * 验证 ASN1 格式的签名
     *
     * @param msg       待验签的数据
     * @param userId    签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param rs        in <b>asn1 format<b/>
     * @param publicKey 公钥
     * @return boolean
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey) {
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", BouncyCastleProvider.PROVIDER_NAME);
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证 ASN1 格式的签名
     *
     * @param msg       待验签的数据
     * @param rs        in <b>asn1 format<b/>
     * @param publicKey 公钥
     * @return boolean
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] rs, PublicKey publicKey) {
        return verifySm3WithSm2Asn1Rs(msg, USER_ID, rs, publicKey);
    }

    /**
     * BC 加解密使用旧标 c1||c2||c3，此方法在加密后调用，将结果转换为 c1||c3||c2
     *
     * @param c1c2c3 c1c2c3 拼接顺序的 byte 数组
     * @return byte[] c1c3c2
     */
    private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
        // sm2p256v1 的这个固定 65。可以看 GMNamedCurves、ECCurve 代码
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;
        // 长度为 new SM3Digest().getDigestSize()
        final int c3Len = 32;
        byte[] result = new byte[c1c2c3.length];
        // c1
        System.arraycopy(c1c2c3, 0, result, 0, c1Len);
        // c3
        System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len);
        // c2
        System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length - c1Len - c3Len);
        return result;
    }

    /**
     * BC 加解密使用旧标 c1||c2||c3，此方法在解密前调用，将密文转换为 c1||c2||c3 再去解密
     *
     * @param c1c3c2 c1c3c2 拼接的 byte 数组
     * @return byte[] c1c2c3
     */
    public static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
        // sm2p256v1 的这个固定 65。可以看 GMNamedCurves、ECCurve 代码
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1;
        // 长度为 new SM3Digest().getDigestSize()
        final int c3Len = 32;
        byte[] result = new byte[c1c3c2.length];
        // c1
        System.arraycopy(c1c3c2, 0, result, 0, c1Len);
        // c2
        System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length - c1Len - c3Len);
        // c3
        System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len);
        return result;
    }

    /**
     * 使用旧标准 c1||c2||c3 顺序的 SM2 非对称公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return byte[]
     */
    public static byte[] sm2EncryptOld(byte[] data, PublicKey key) {
        BCECPublicKey localECPublicKey = (BCECPublicKey) key;
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用新标准 c1||c3||c2 顺序的 SM2 非对称公钥加密
     *
     * @param data 待加密数据
     * @param key  SM2 公钥
     * @return byte[] 加密后的数据
     */
    public static byte[] sm2Encrypt(byte[] data, PublicKey key) {
        return changeC1C2C3ToC1C3C2(sm2EncryptOld(data, key));
    }

    /**
     * 使用旧标准 c1||c2||c3 顺序的 SM2 非对称私钥解密
     *
     * @param data 待解密数据
     * @param key  SM2 私钥
     * @return byte[] 解密后的数据
     */
    public static byte[] sm2DecryptOld(byte[] data, PrivateKey key) {
        BCECPrivateKey localECPrivateKey = (BCECPrivateKey) key;
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(localECPrivateKey.getD(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, ecPrivateKeyParameters);
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用新标准 c1||c3||c2 顺序的 SM2 非对称私钥解密
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return byte[]
     */
    public static byte[] sm2Decrypt(byte[] data, PrivateKey key) {
        return sm2DecryptOld(changeC1C3C2ToC1C2C3(data), key);
    }

    /**
     * SM4 对称密钥加密
     *
     * @param keyBytes SM对称密钥
     * @param plain    待加密数据
     * @return byte[]  加密后的数据
     */
    public static byte[] sm4Encrypt(byte[] keyBytes, byte[] plain) {
        if (keyBytes.length != 16) {
            throw new RuntimeException("error key length");
        }

        if (plain.length % 16 != 0) {
            throw new RuntimeException("error data length");
        }

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher out = Cipher.getInstance("SM4/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);
            out.init(Cipher.ENCRYPT_MODE, key);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SM4 对称密钥解密
     *
     * @param keyBytes SM4 对称密钥
     * @param cipher   待解密的数据
     * @return byte[]  解密后的数据
     */
    public static byte[] sm4Decrypt(byte[] keyBytes, byte[] cipher) {
        if (keyBytes.length != 16) {
            throw new RuntimeException("error key length");
        }

        if (cipher.length % 16 != 0) {
            throw new RuntimeException("error data length");
        }

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance("SM4/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);
            in.init(Cipher.DECRYPT_MODE, key);
            return in.doFinal(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SM3 Hash 算法
     *
     * @param bytes 待 hash 的数据
     * @return byte[] SM3 hash 值
     */
    public static byte[] sm3(byte[] bytes) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(result, 0);
        return result;
    }

    /**
     * 将 BigInteger 转换为定长的字节数组
     *
     * @param rOrS BigInteger
     * @return byte[] 定长的字节数组，长度为 32(RS_LEN)
     */
    private static byte[] bigIntToFixedLengthBytes(BigInteger rOrS) {
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // n and s are the result of mod n, so they should be less than n and have length < 32.
        byte[] rs = rOrS.toByteArray();
        if (rs.length == RS_LEN) {
            return rs;
        } else if (rs.length == RS_LEN + 1 && rs[0] == 0) {
            return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        } else if (rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte) 0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new RuntimeException("error rs: " + Hex.toHexString(rs));
        }
    }

    /**
     * BC 的 SM3withSM2 签名得到的结果的 rs 是 asn1 格式的，这个方法转换成直接拼接的 r||s
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
        ASN1Sequence sequence = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixedLengthBytes(ASN1Integer.getInstance(sequence.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixedLengthBytes(ASN1Integer.getInstance(sequence.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }

    /**
     * BC 的 SM3withSM2 验签需要的 rs 是 asn1 格式的，这个方法将直接拼接 r||s 的字节数组转化成 asn1 格式
     *
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
        if (sign.length != RS_LEN * 2) {
            throw new RuntimeException("error rs.");
        }
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 EC 密钥对
     *
     * @return KeyPair
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            kpGen.initialize(ecParameterSpec, new SecureRandom());
            return kpGen.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从 D 分量还原完整的 BCEC 私钥
     *
     * @param d D 分量
     * @return BCECPrivateKey 完整的私钥
     */
    private static BCECPrivateKey buildPrivateKeyFromD(BigInteger d) {
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * 从 D 分量字节数组还原完整的 BCEC 私钥
     *
     * @param privateKeyD D 分量的字节数组
     * @return BCECPrivateKey 完整的私钥
     */
    public static BCECPrivateKey buildPrivateKeyFromD(byte[] privateKeyD) {
        return buildPrivateKeyFromD(new BigInteger(1, privateKeyD));
    }

    /**
     * 从 X，Y 分量还原完整的 BCEC 公钥
     *
     * @param x X 分量
     * @param y Y 分量
     * @return BCECPublicKey 完整的公钥
     */
    private static BCECPublicKey buildPublicKeyFromXY(BigInteger x, BigInteger y) {
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecParameterSpec);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * 从 X，Y 分量拼合的字节数组还原完整的 BCEC 公钥
     *
     * @param publicKeyXY X，Y 分量拼合的字节数组
     * @return BCECPublicKey 完整的公钥
     */
    public static BCECPublicKey buildPublicKeyFromXY(byte[] publicKeyXY) {
        BigInteger x = getXFromBytes(publicKeyXY);
        BigInteger y = getYFromBytes(publicKeyXY);
        return buildPublicKeyFromXY(x, y);
    }

    /**
     * 从 D 分量的字节数组获取 D 分量
     *
     * @param privateKeyD D 分量的字节数组
     * @return BigInteger D 分量
     */
    private static BigInteger getDFromBytes(byte[] privateKeyD) {
        return new BigInteger(1, privateKeyD);
    }

    /**
     * 从 X||Y 拼接的字节数组获取 X 分量
     *
     * @param publicKeyXY X||Y 拼接的字节数组
     * @return BigInteger X 分量
     */
    private static BigInteger getXFromBytes(byte[] publicKeyXY) {
        byte[] x = new byte[RS_LEN];
        System.arraycopy(publicKeyXY, 0, x, 0, x.length);
        return new BigInteger(1, x);
    }

    /**
     * 从 X||Y 拼接的字节数组获取 Y 分量
     *
     * @param publicKeyXY X||Y 拼接的字节数组
     * @return BigInteger Y 分量
     */
    private static BigInteger getYFromBytes(byte[] publicKeyXY) {
        byte[] y = new byte[RS_LEN];
        System.arraycopy(publicKeyXY, RS_LEN, y, 0, y.length);
        return new BigInteger(1, y);
    }

    /**
     * 获取私钥分量 D 作为交换的私钥
     *
     * @param privateKey 私钥
     * @return byte[] 私钥分量 D 的字节数组
     */
    public static byte[] getPrivateKeyD(BCECPrivateKey privateKey) {
        return bigIntToFixedLengthBytes(privateKey.getD());
    }

    /**
     * 获取公钥分量 X，Y 作为交换的公钥
     *
     * @param publicKey 公钥
     * @return byte[] 公钥分量 X，Y 拼合的字节数组
     */
    public static byte[] getPublicKeyXY(BCECPublicKey publicKey) {
        byte[] x = bigIntToFixedLengthBytes(publicKey.getQ().getXCoord().toBigInteger());
        byte[] y = bigIntToFixedLengthBytes(publicKey.getQ().getYCoord().toBigInteger());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(x, 0, result, 0, x.length);
        System.arraycopy(y, 0, result, RS_LEN, y.length);
        return result;
    }

    /**
     * 从证书获取公钥
     *
     * @param file X509 文件
     * @return 公钥
     */
    public static PublicKey getPublicKeyFromX509File(File file) {
        try {
            try (FileInputStream in = new FileInputStream(file)) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
                X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(in);
                return certificate.getPublicKey();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
