package com.changhr.cloud.grpc.cipher.virtual.pack.test;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.EducationalBackground;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.client.CabinToken;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.data.KeyRec;
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

//        String keyRec = "ASAAEAAAAAC/kS1dAAAAAGPmVDdAC0JgrUlst/n1CTFLsLkgrOrrs1xEPLoAkkPb88x7L9vPnl3+RcvJMTCzPVeg7fNOCU7cs2P+Ek3bf1fnT7+JSA1c65xZRJ5HEK94mDpYshsOB//TSAauy8a4SggAAAAAiIiIEScAAAAAAAC+kS1dAAAAAP////////9/AQAAAAABAACb44RinqK5QgO14RX5IkSftUsUj/7Q3V+mCPI071ejQ4TsqW3mF8rtYbYSesKGmSh+VYwD0rB/b6JYIFh9t64MZd3lLakG8z8=";
//
//        KeyRec deserialize = (KeyRec) AbstractPack.deserialize(Base64.getDecoder().decode(keyRec), KeyRec.class);
//        System.out.println(deserialize);

        String userPubKey = "ARAAEDDdyYYg4kq/gpxmMVz0ZKAAAAAAF6bjWwAAAAAAAQEAAAQAAIwAAAAwgYkCgYEAoNTXjrV8RYkamzihQELz0Mh2OUHrhEzfepUANHkE2zu41O3X1XCLyLc2ylB7SrpWhtD/STVYm43zBYr0SyO5gidExPNJhF06OU/6P4YRqkjiEiO4Ev2Ja3O1IWdhd9fdvLSnf5sH9CVXAstanXwj0mwthJ57wnBCuyN8HFuUSBMCAwEAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACKcU7piT27izfep8U6Z0M8rJwIEDf7kB2y2oL6Pd2wA==";

    }
}
