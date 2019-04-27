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
        KeyPair keyPair = GMUtil.generateKeyPair();
        System.out.println(Hex.toHexString(keyPair.getPrivate().getEncoded()));
        System.out.println(Hex.toHexString(keyPair.getPublic().getEncoded()));

        System.out.println(keyPair.getPrivate().getAlgorithm());
        System.out.println(keyPair.getPublic().getAlgorithm());

        System.out.println(keyPair.getPrivate().getFormat());
        System.out.println(keyPair.getPublic().getFormat());

        System.out.println(((BCECPrivateKey)keyPair.getPrivate()).getD());
        System.out.println(((BCECPublicKey)keyPair.getPublic()).getQ());

        byte[] msg = "message digest".getBytes();
        byte[] userId = "userid".getBytes();
        byte[] sign = GMUtil.signSm3WithSm2(msg, userId, keyPair.getPrivate());
        System.out.println(Hex.toHexString(sign));
        System.out.println(GMUtil.verifySm3WithSm2(msg, userId, sign, keyPair.getPublic()));
    }
}
