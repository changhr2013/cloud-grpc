package com.changhr.cloud.grpc.cipher.virtual.utils.test;

import com.changhr.cloud.grpc.cipher.virtual.utils.GMUtil;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;

import java.security.KeyPair;

/**
 * @author changhr
 */
public class GMUtilTest {

    public static void main(String[] args) {
        // 生成 BCEC 密钥对
        KeyPair keyPair = GMUtil.generateKeyPair();
        String priKey = Hex.toHexString(keyPair.getPrivate().getEncoded());
        System.out.println(priKey);
        System.out.println(priKey.length());
        String pubKey = Hex.toHexString(keyPair.getPublic().getEncoded());
        System.out.println(pubKey);
        System.out.println(pubKey.length());

        // 公私钥格式
        System.out.println(keyPair.getPrivate().getFormat());
        System.out.println(keyPair.getPublic().getFormat());

        // 打印交换私钥和公钥的 Hex 字符串
        String privateKey = Hex.toHexString(GMUtil.getPrivateKeyD((BCECPrivateKey) keyPair.getPrivate()));
        System.out.println(privateKey);
        System.out.println(privateKey.length());
        String publicKey = Hex.toHexString(GMUtil.getPublicKeyXY((BCECPublicKey) keyPair.getPublic()));
        System.out.println(publicKey);
        System.out.println(publicKey.length());

        // 通过交换公私钥还原 BCEC 公私钥
        String recoverPriKey = Hex.toHexString(GMUtil.buildPrivateKeyFromD(Hex.decode(privateKey)).getEncoded());
        System.out.println(recoverPriKey);
        String recoverPubKey = Hex.toHexString(GMUtil.buildPublicKeyFromXY(Hex.decode(publicKey)).getEncoded());
        System.out.println(recoverPubKey);

        // 打印各个分量
        System.out.println("D = " + ((BCECPrivateKey)keyPair.getPrivate()).getD());
        System.out.println("X = " + ((BCECPublicKey)keyPair.getPublic()).getQ().getXCoord());
        System.out.println("Y = " + ((BCECPublicKey)keyPair.getPublic()).getQ().getYCoord());

        // 使用 SM2 私钥签名
        byte[] msg = "message digest".getBytes();
        byte[] signBySrcPriKey = GMUtil.signSm3WithSm2(msg, keyPair.getPrivate());
        byte[] signByRecoverPriKey = GMUtil.signSm3WithSm2(msg, GMUtil.buildPrivateKeyFromD(Hex.decode(privateKey)));
        System.out.println("sign1 = " + Hex.toHexString(signBySrcPriKey));
        System.out.println("sign2 = " + Hex.toHexString(signByRecoverPriKey));

        // 使用公钥对签名验签
        System.out.println(GMUtil.verifySm3WithSm2(msg, signBySrcPriKey, keyPair.getPublic()));
        System.out.println(GMUtil.verifySm3WithSm2(msg, signByRecoverPriKey, keyPair.getPublic()));

        // 公钥加密，私钥解密
        byte[] sm2Encrypt = GMUtil.sm2Encrypt(msg, keyPair.getPublic());
        System.out.println(Hex.toHexString(sm2Encrypt));
        byte[] srcPriKeyDecrypt = GMUtil.sm2Decrypt(sm2Encrypt, keyPair.getPrivate());
        System.out.println("srcPriKeyDecrypt = " + new String(srcPriKeyDecrypt));
        byte[] recoverPriKeyDecrypt = GMUtil.sm2Decrypt(sm2Encrypt, GMUtil.buildPrivateKeyFromD(Hex.decode(privateKey)));
        System.out.println("recoverPriKeyDecrypt = " + new String(recoverPriKeyDecrypt));
    }
}
