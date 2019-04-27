package com.changhr.cloud.grpc.cipher.virtual.utils.test;

import com.changhr.cloud.grpc.cipher.virtual.utils.GMUtil;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.security.KeyPair;

/**
 * @author changhr
 */
public class GMUtilTest {

    public static void main(String[] args) {
        KeyPair keyPair = GMUtil.generateKeyPair();
        String priKey = Hex.toHexString(keyPair.getPrivate().getEncoded());
        System.out.println(priKey);
        System.out.println(priKey.length());
        String pubKey = Hex.toHexString(keyPair.getPublic().getEncoded());
        System.out.println(pubKey);
        System.out.println(pubKey.length());

//        System.out.println(keyPair.getPrivate().getAlgorithm());
//        System.out.println(keyPair.getPublic().getAlgorithm());
//
//        System.out.println(keyPair.getPrivate().getFormat());
//        System.out.println(keyPair.getPublic().getFormat());

        String privateKey = Hex.toHexString(GMUtil.getPrivateKeyD((BCECPrivateKey) keyPair.getPrivate()));
        System.out.println(privateKey);
        System.out.println(privateKey.length());
        String publicKey = Hex.toHexString(GMUtil.getPublicKeyXY((BCECPublicKey) keyPair.getPublic()));
        System.out.println(publicKey);
        System.out.println(publicKey.length());

        BigInteger srcD = ((BCECPrivateKey) keyPair.getPrivate()).getD();
        String srcrecoverPriKey = Hex.toHexString(GMUtil.buildPrivateKeyFromD(srcD).getEncoded());
        System.out.println(srcrecoverPriKey);

        String recoverPriKey = Hex.toHexString(GMUtil.buildPrivateKeyFromD(Hex.decode(privateKey)).getEncoded());
        System.out.println(recoverPriKey);
        String recoverPubKey = Hex.toHexString(GMUtil.buildPublicKeyFromXY(Hex.decode(publicKey)).getEncoded());
        System.out.println(recoverPubKey);
//        Assert.isTrue(priKey.equals(recoverPriKey), "private key recover success");
//        Assert.isTrue(pubKey.equals(recoverPubKey), "private key recover success");

        System.out.println(priKey.equals(recoverPriKey));
        System.out.println(pubKey.equals(recoverPubKey));

//        System.out.println("D = " + ((BCECPrivateKey)keyPair.getPrivate()).getD());
//        System.out.println("X = " + ((BCECPublicKey)keyPair.getPublic()).getQ().getXCoord());
//        System.out.println("Y = " + ((BCECPublicKey)keyPair.getPublic()).getQ().getYCoord());
//
        byte[] msg = "message digest".getBytes();
        byte[] userId = "userid".getBytes();
        byte[] sign = GMUtil.signSm3WithSm2(msg, userId, keyPair.getPrivate());
        byte[] bytes = GMUtil.signSm3WithSm2(msg, userId, GMUtil.buildPrivateKeyFromD(Hex.decode(privateKey)));
        System.out.println("sign1" + Hex.toHexString(sign));
        System.out.println("sign2" + Hex.toHexString(bytes));


        System.out.println(GMUtil.verifySm3WithSm2(msg, userId, sign, keyPair.getPublic()));

        byte[] sm2Encrypt = GMUtil.sm2Encrypt(msg, keyPair.getPublic());

        byte[] bytes1 = GMUtil.sm2Decrypt(sm2Encrypt, keyPair.getPrivate());
        System.out.println(new String(bytes1));

        byte[] signSm3WithSm2 = GMUtil.signSm3WithSm2(msg, userId, keyPair.getPrivate());

        boolean b = GMUtil.verifySm3WithSm2(msg, userId, signSm3WithSm2, keyPair.getPublic());
        System.out.println(b);
    }
}
