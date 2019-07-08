package com.changhr.cloud.grpc.cipher.virtual.pack.test;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.EducationalBackground;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.client.CabinToken;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.data.LicReq;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.TestRequest;
import com.changhr.cloud.grpc.cipher.virtual.pack.utils.SequenceIdUtil;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.StopWatch;

import java.security.SecureRandom;
import java.util.*;

/**
 * 测试类
 *
 * @author changhr
 */
public class Test {

    public static void main(String[] args) throws Exception {

//        // 创建一个请求包对象，并给对象赋值
//        TestRequest request = new TestRequest();
//        int sequenceId = SequenceIdUtil.getInstance().nextVal();
//        request.setSeq(sequenceId);
//        request.setUserName("zhangsanfeng");
//        request.setPwd("123456");
//        request.setTrueName("张三丰");
//        request.setTelNo(18311111111L);
//        request.setGender((byte)1);
//        request.setAge((short)24);
//        request.setHeight((short)176);
//        request.setWeight(65.5F);
//
//        byte[] temp = new byte[16];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(temp);
//        for (byte b : temp) {
//            System.out.print(b + " ");
//        }
//        System.out.println();
//        request.setRandomId(temp);
//
//        EducationalBackground primarySchool = new EducationalBackground();
//        primarySchool.setStartYear((short) 1998);
//        primarySchool.setEndYear((short)2003);
//        primarySchool.setSchool("林州市实验小学");
//
//        EducationalBackground middleSchool = new EducationalBackground();
//        middleSchool.setStartYear((short)2003);
//        middleSchool.setEndYear((short)2009);
//        middleSchool.setSchool("八一中学");
//
//        EducationalBackground university = new EducationalBackground();
//        university.setStartYear((short)2009);
//        university.setEndYear((short)2013);
//        university.setSchool("清华大学");
//
//        List<EducationalBackground> list = new ArrayList<>();
//        list.add(primarySchool);
//        list.add(middleSchool);
//        list.add(university);
//
//        request.setEduBackground(list);
//
//        // 打印创建完成的请求对象
//        System.out.println(request.toString());
//
//        // 调用序列化方法将对象转换成字节数组
//        byte[] bs = request.serialize(false);
//
//        // 打印生成的字节数组
//        for (byte b : bs) {
//            System.out.print(b + " ");
//        }
//        System.out.println();
//
//        // 调用返序列化方法，将字节数组转换为对象
//        TestRequest request2 = (TestRequest) AbstractPack.deserialize(bs, TestRequest.class);
//        System.out.println(request2);
//
//        String licReqStr = "ARAAEBHaXwBz50wdg0Pk3Q5Q1usCF1ljgylP8ZzTAKjBtFdS8FOiz/elJ/x3WNeLRTHM3Qp80v1uGWZw4TmS0eo6inCFldi8jPBHq5JFGv4PkK+HAAAAALofE10AAAAAARAAEAMAAAAKGxNdAAAAADr9Hl0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABgAAAAAAAAAwRgIhAMe1l7yP6b7qZ+I5I2t0PbuUg9gMUkTmt9D9NQUA0oN6AiEAwOfdArOFfFaJufVrE6O7XUnQgb3dZpWSv3j5BMkkooAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//        LicReq deserialize = (LicReq) AbstractPack.deserialize(Base64.getDecoder().decode(licReqStr), LicReq.class);
//        System.out.println(deserialize);
//        System.out.println(Hex.toHexString(deserialize.getFatherLicId()));
//        System.out.println(Hex.toHexString(deserialize.getKeyId()));

        String cabinTokenBase64 = "AAAomM4u0ilBML8WRwbwX31+rnrp2KVCTOqv5UeNgl8oAwfXO/fOQUdXgmbfMpSMzLoSRoWVepFLm5VF9504YOuB";
        byte[] decode = Base64.getDecoder().decode(cabinTokenBase64);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CabinToken deserialize = (CabinToken) AbstractPack.deserialize(decode, CabinToken.class);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

        System.out.println(deserialize);
        System.out.println("licenseKeyId=" + Hex.toHexString(deserialize.getLicKeyId()));
        System.out.println("licenseId=" + Hex.toHexString(deserialize.getLicenseId()));
        System.out.println("receiver=" + Hex.toHexString(deserialize.getReceiverDoeUserId()));
        System.out.println("issuer=" + Hex.toHexString(deserialize.getIssuerDoeUserId()));
        System.out.println("version=" + deserialize.getVersion());
        System.out.println("type=" + deserialize.getType());
    }
}
