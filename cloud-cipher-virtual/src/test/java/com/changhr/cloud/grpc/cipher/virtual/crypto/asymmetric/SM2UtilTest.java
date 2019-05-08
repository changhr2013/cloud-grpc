package com.changhr.cloud.grpc.cipher.virtual.crypto.asymmetric;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SM2UtilTest {

    private static final String text = "youth is the reason of hope and energy.";
    private static final byte[] data = text.getBytes(StandardCharsets.UTF_8);

    private static byte[] privateKey;
    private static byte[] publicKey;

    private static byte[] encryptByPub;

    private static byte[] signByPri;

    @BeforeClass
    public static void initKey() {
        Map<String, Object> keyMap = SM2Util.initKey();

        privateKey = SM2Util.getSwapPrivateKey(keyMap);
        System.out.println(Base64.getEncoder().encodeToString(privateKey));

        publicKey = SM2Util.getSwapPublicKey(keyMap);
        System.out.println(Base64.getEncoder().encodeToString(publicKey));
    }

    @Test
    public void test01Encrypt() {
        encryptByPub = SM2Util.encrypt(data, publicKey);
        System.out.println(Base64.getEncoder().encodeToString(encryptByPub));
    }

    @Test
    public void test02Decrypt() {
        byte[] srcBytes = SM2Util.decrypt(encryptByPub, privateKey);
        System.out.println(new String(srcBytes, StandardCharsets.UTF_8));
    }

    @Test
    public void test03Sign() {
        signByPri = SM2Util.sign(data, privateKey);
        System.out.println(Base64.getEncoder().encodeToString(signByPri));
    }

    @Test
    public void test04Verify() {
        boolean verify = SM2Util.verify(data, publicKey, signByPri);
        System.out.println(verify);
    }
}