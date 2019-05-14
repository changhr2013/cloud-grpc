package com.changhr.cloud.grpc.cipher.virtual.crypto.asymmetric;

import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.cert.X509v1CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v1CertificateBuilder;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.encoders.Hex;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Calendar.YEAR;

/**
 * 国密 SM2 非对称加密算法工具类
 *
 * @author changhr
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class SM2Util {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    /**
     * 密钥算法类型
     */
    public static final String KEY_ALGORITHM = "EC";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SM3withSM2";

    /**
     * KeyMap 中公钥索引 KEY
     */
    private static final String PUBLIC_KEY = "SM2PublicKey";

    /**
     * KeyMap 中私钥索引 KEY
     */
    private static final String PRIVATE_KEY = "SM2PrivateKey";

    /**
     * SM2 标准曲线
     */
    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");

    /**
     * SM2 标准杂凑值
     */
    private static final byte[] USER_ID = "1234567812345678".getBytes();

    private static final int RS_LEN = 32;

    private static ECDomainParameters ecDomainParameters =
            new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    private static ECParameterSpec ecParameterSpec =
            new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    /**
     * 使用私钥对数据签名，结果为直接拼接 rs 的字节数组
     *
     * @param msg            待签名数据
     * @param swapPrivateKey SM2 交换私钥
     * @return r||s，直接拼接 byte 数组的 rs
     */
    public static byte[] sign(byte[] msg, byte[] swapPrivateKey) {
        return sign(msg, USER_ID, swapPrivateKey);
    }

    /**
     * 使用私钥对数据签名，结果为直接拼接 rs 的字节数组
     *
     * @param msg            待签名数据
     * @param userId         签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param swapPrivateKey SM2 交换私钥
     * @return r||s，直接拼接 byte 数组的 rs
     */
    public static byte[] sign(byte[] msg, byte[] userId, byte[] swapPrivateKey) {
        return rsAsn1ToPlainByteArray(signAsn1Rs(msg, userId, swapPrivateKey));
    }

    /**
     * 使用私钥对数据签名，结果为 ASN1 格式的 rs 的字节数组
     *
     * @param msg            待签名数据
     * @param swapPrivateKey SM2 交换私钥
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signAsn1Rs(byte[] msg, byte[] swapPrivateKey) {
        return signAsn1Rs(msg, USER_ID, swapPrivateKey);
    }

    /**
     * 使用私钥对数据签名，结果为 ASN1 格式的 rs 的字节数组
     *
     * @param msg            待签名数据
     * @param userId         签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param swapPrivateKey SM2 交换私钥
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signAsn1Rs(byte[] msg, byte[] userId, byte[] swapPrivateKey) {
        BCECPrivateKey bcecPrivateKey = buildPrivateKey(swapPrivateKey);
        SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
        try {
            Signature signer = Signature.getInstance(SIGNATURE_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            signer.setParameter(parameterSpec);
            signer.initSign(bcecPrivateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            return signer.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证直接拼接 rs 的签名
     *
     * @param msg           待验签的数据
     * @param userId        签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param swapPublicKey SM2 交换公钥
     * @param rs            r||s，直接拼接 byte 数组的 rs
     * @return boolean
     */
    public static boolean verify(byte[] msg, byte[] userId, byte[] swapPublicKey, byte[] rs) {
        return verifyAsn1Rs(msg, userId, swapPublicKey, rsPlainByteArrayToAsn1(rs));
    }

    /**
     * 验证直接拼接 rs 的签名
     *
     * @param msg           待验签的数据
     * @param swapPublicKey SM2 交换公钥
     * @param rs            r||s，直接拼接 byte 数组的 rs
     * @return boolean
     */
    public static boolean verify(byte[] msg, byte[] swapPublicKey, byte[] rs) {
        return verify(msg, USER_ID, swapPublicKey, rs);
    }

    /**
     * 验证 ASN1 格式的签名
     *
     * @param msg           待验签的数据
     * @param userId        签名者身份信息，默认应使用 "1234567812345678".getBytes()
     * @param swapPublicKey SM2 交换公钥
     * @param rs            in <b>asn1 format<b/>
     * @return boolean
     */
    public static boolean verifyAsn1Rs(byte[] msg, byte[] userId, byte[] swapPublicKey, byte[] rs) {
        BCECPublicKey bcecPublicKey = buildPublicKey(swapPublicKey);
        SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
        try {
            Signature verifier = Signature.getInstance(SIGNATURE_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            verifier.setParameter(parameterSpec);
            verifier.initVerify(bcecPublicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证 ASN1 格式的签名
     *
     * @param msg           待验签的数据
     * @param swapPublicKey SM2 交换公钥
     * @param rs            in <b>asn1 format<b/>
     * @return boolean
     */
    public static boolean verifyAsn1Rs(byte[] msg, byte[] swapPublicKey, byte[] rs) {
        return verifyAsn1Rs(msg, USER_ID, swapPublicKey, rs);
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
    private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
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
     * @param data          待加密数据
     * @param swapPublicKey SM2 交换公钥
     * @return byte[]
     */
    public static byte[] encryptOld(byte[] data, byte[] swapPublicKey) {
        BCECPublicKey bcecPublicKey = buildPublicKey(swapPublicKey);
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(bcecPublicKey.getQ(), ecDomainParameters);
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
     * @param data          待加密数据
     * @param swapPublicKey SM2 交换公钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] swapPublicKey) {
        return changeC1C2C3ToC1C3C2(encryptOld(data, swapPublicKey));
    }

    /**
     * 使用旧标准 c1||c2||c3 顺序的 SM2 非对称私钥解密
     *
     * @param data           待解密数据
     * @param swapPrivateKey SM2 交换私钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decryptOld(byte[] data, byte[] swapPrivateKey) {
        BCECPrivateKey bcecPrivateKey = buildPrivateKey(swapPrivateKey);
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(bcecPrivateKey.getD(), ecDomainParameters);
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
     * @param data           待解密数据
     * @param swapPrivateKey SM2 交换私钥
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data, byte[] swapPrivateKey) {
        return decryptOld(changeC1C3C2ToC1C2C3(data), swapPrivateKey);
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
     * 初始化密钥
     *
     * @return Map 密钥 Map
     */
    public static Map<String, Object> initKey() {
        // 实例化密钥对生成器
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            // 初始化密钥对生成器
            keyPairGen.initialize(ecParameterSpec, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm: " + KEY_ALGORITHM, e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("no such provider: " + BouncyCastleProvider.PROVIDER_NAME, e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid algorithm parameter", e);
        }
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();
        // 私钥
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        // 封装密钥
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 获取私钥分量 D 作为交换的私钥
     *
     * @param keyMap 密钥 Map
     * @return byte[] SM2 交换私钥（私钥分量 D 的字节数组）
     */
    public static byte[] getSwapPrivateKey(Map<String, Object> keyMap) {
        BCECPrivateKey privateKey = (BCECPrivateKey) keyMap.get(PRIVATE_KEY);
        return bigIntToFixedLengthBytes(privateKey.getD());
    }

    /**
     * 获取公钥分量 X，Y 作为交换的公钥
     *
     * @param keyMap 密钥 Map
     * @return byte[] SM2 交换公钥（公钥分量 X，Y 拼合的字节数组）
     */
    public static byte[] getSwapPublicKey(Map<String, Object> keyMap) {
        BCECPublicKey publicKey = (BCECPublicKey) keyMap.get(PUBLIC_KEY);
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

    /**
     * 从交换私钥(D 分量字节数组)还原完整的 BCEC 私钥
     *
     * @param swapPrivateKey 交换私钥
     * @return BCECPrivateKey 完整的私钥
     */
    private static BCECPrivateKey buildPrivateKey(byte[] swapPrivateKey) {
        return buildPrivateKey(getDFromSwapPrivateKey(swapPrivateKey));
    }

    /**
     * 从 D 分量还原完整的 BCEC 私钥
     *
     * @param d D 分量
     * @return BCECPrivateKey 完整的私钥
     */
    private static BCECPrivateKey buildPrivateKey(BigInteger d) {
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * 从交换公钥(X，Y 分量拼合的字节数组)还原完整的 BCEC 公钥
     *
     * @param swapPublicKey 交换公钥
     * @return BCECPublicKey 完整的公钥
     */
    private static BCECPublicKey buildPublicKey(byte[] swapPublicKey) {
        BigInteger x = getXFromSwapPublicKey(swapPublicKey);
        BigInteger y = getYFromSwapPublicKey(swapPublicKey);
        return buildPublicKey(x, y);
    }

    /**
     * 从 X，Y 分量还原完整的 BCEC 公钥
     *
     * @param x X 分量
     * @param y Y 分量
     * @return BCECPublicKey 完整的公钥
     */
    private static BCECPublicKey buildPublicKey(BigInteger x, BigInteger y) {
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecParameterSpec);
        return new BCECPublicKey(KEY_ALGORITHM, ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    /**
     * 从 D 分量的字节数组(交换私钥)获取 D 分量
     *
     * @param swapPrivateKey D 分量的字节数组
     * @return BigInteger D 分量
     */
    private static BigInteger getDFromSwapPrivateKey(byte[] swapPrivateKey) {
        return new BigInteger(1, swapPrivateKey);
    }

    /**
     * 从 X||Y 拼接的字节数组(交换公钥)获取 X 分量
     *
     * @param swapPublicKey X||Y 拼接的字节数组
     * @return BigInteger X 分量
     */
    private static BigInteger getXFromSwapPublicKey(byte[] swapPublicKey) {
        byte[] x = new byte[RS_LEN];
        System.arraycopy(swapPublicKey, 0, x, 0, x.length);
        return new BigInteger(1, x);
    }

    /**
     * 从 X||Y 拼接的字节数组(交换公钥)获取 Y 分量
     *
     * @param swapPublicKey X||Y 拼接的字节数组
     * @return BigInteger Y 分量
     */
    private static BigInteger getYFromSwapPublicKey(byte[] swapPublicKey) {
        byte[] y = new byte[RS_LEN];
        System.arraycopy(swapPublicKey, RS_LEN, y, 0, y.length);
        return new BigInteger(1, y);
    }

    public static void main(String[] args)
            throws Exception
    {
//        if (args.length != 2)
//        {
//            System.err.println("Usage: GenTrustAnchorKeyStore keyStoreName keyStorePassword");
//            System.exit(1);
//        }
        args = new String[2];
        args[0] = "simple";
        args[1] = "123456";

        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("ECDSA", "BC");

        kpGen.initialize(new ECNamedCurveGenParameterSpec("secp256r1"));

        KeyPair kp = kpGen.generateKeyPair();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);

        builder.addRDN(BCStyle.C, "AU");
        builder.addRDN(BCStyle.O, "Crypto Workshop Pty Ltd");
        builder.addRDN(BCStyle.OU, "Ximix Node Test CA");
        builder.addRDN(BCStyle.L, "Melbourne");
        builder.addRDN(BCStyle.ST, "Victoria");
        builder.addRDN(BCStyle.CN, "Trust Anchor");

        Date startDate = new Date(System.currentTimeMillis() - 50000);

        ContentSigner sigGen = new JcaContentSignerBuilder("SHA256withECDSA")
                .setProvider("BC")
                .build(kp.getPrivate());

        X509v1CertificateBuilder certGen1 = new JcaX509v1CertificateBuilder(
                builder.build(),
                BigInteger.valueOf(1),
                startDate,
                new Date(System.currentTimeMillis() + 2 * YEAR),
                builder.build(),
                kp.getPublic());

        X509Certificate cert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certGen1.build(sigGen));

        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");

        keyStore.load(null, null);

        keyStore.setKeyEntry("trust", kp.getPrivate(), null, new Certificate[] { cert });

        keyStore.store(new FileOutputStream(args[0] + ".p12"), args[1].toCharArray());

        JcaPEMWriter pWrt = new JcaPEMWriter(new FileWriter(args[0] + ".pem"));

        pWrt.writeObject(cert);

        pWrt.close();
    }
}