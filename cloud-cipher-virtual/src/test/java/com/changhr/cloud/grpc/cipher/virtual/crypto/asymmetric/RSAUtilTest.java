package com.changhr.cloud.grpc.cipher.virtual.crypto.asymmetric;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RSAUtilTest {

    private static final String text = "youth is the reason of hope and energy.";
    private static final byte[] data = text.getBytes(StandardCharsets.UTF_8);

    private static byte[] privateKey;
    private static byte[] publicKey;

    private static byte[] encryptByPub;
    private static byte[] encryptByPri;

    private static byte[] signByPri;

    @BeforeClass
    public static void initKey(){
        Map<String, Object> keyMap = RSAUtil.initKey();
        privateKey = RSAUtil.getPrivateKey(keyMap);
        System.out.println("PriKey: " + Base64.getEncoder().encodeToString(privateKey));
        publicKey = RSAUtil.getPublicKey(keyMap);
        System.out.println("PubKey: " + Base64.getEncoder().encodeToString(publicKey));
    }

    @Test
    public void test01EncryptByPublicKey() {
        encryptByPub = RSAUtil.encryptByPublicKey(data, publicKey);
        System.out.println("EncryptByPub: " + Base64.getEncoder().encodeToString(encryptByPub));

    }

    @Test
    public void test02DecryptByPrivateKey() {
        byte[] srcBytes = RSAUtil.decryptByPrivateKey(encryptByPub, privateKey);
        System.out.println(new String(srcBytes, StandardCharsets.UTF_8));
    }

    @Test
    public void test03EncryptByPrivateKey() {
        encryptByPri = RSAUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("EncryptByPri: " + Base64.getEncoder().encodeToString(encryptByPri));

    }

    @Test
    public void test04DecryptByPublicKey() {
        byte[] srcBytes = RSAUtil.decryptByPublicKey(encryptByPri, publicKey);
        System.out.println(new String(srcBytes, StandardCharsets.UTF_8));
    }

    @Test
    public void test05Sign() {
        signByPri = RSAUtil.sign(data, privateKey);
        System.out.println("sign: " + Base64.getEncoder().encodeToString(signByPri));
    }

    @Test
    public void test06Verify() {
        boolean verify = RSAUtil.verify(data, publicKey, signByPri);
        System.out.println("verify: " + verify);
    }
}