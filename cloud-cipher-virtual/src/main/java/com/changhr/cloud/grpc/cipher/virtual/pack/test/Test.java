package com.changhr.cloud.grpc.cipher.virtual.pack.test;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.EducationalBackground;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.TestRequest;
import com.changhr.cloud.grpc.cipher.virtual.pack.utils.SequenceIdUtil;

import java.security.SecureRandom;
import java.util.*;

/**
 * 测试类
 *
 * @author changhr
 */
public class Test {

    public static void main(String[] args) throws Exception {

        // 创建一个请求包对象，并给对象赋值
        TestRequest request = new TestRequest();
        int sequenceId = SequenceIdUtil.getInstance().nextVal();
        request.setSeq(sequenceId);
        request.setUserName("zhangsanfeng");
        request.setPwd("123456");
        request.setTrueName("张三丰");
        request.setTelNo(18311111111L);
        request.setGender((byte)1);
        request.setAge((short)24);
        request.setHeight((short)176);
        request.setWeight(65.5F);

        byte[] temp = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(temp);
        for (byte b : temp) {
            System.out.print(b + " ");
        }
        System.out.println();
        request.setRandomId(temp);

        EducationalBackground primarySchool = new EducationalBackground();
        primarySchool.setStartYear((short) 1998);
        primarySchool.setEndYear((short)2003);
        primarySchool.setSchool("林州市实验小学");

        EducationalBackground middleSchool = new EducationalBackground();
        middleSchool.setStartYear((short)2003);
        middleSchool.setEndYear((short)2009);
        middleSchool.setSchool("八一中学");

        EducationalBackground university = new EducationalBackground();
        university.setStartYear((short)2009);
        university.setEndYear((short)2013);
        university.setSchool("清华大学");

        List<EducationalBackground> list = new ArrayList<>();
        list.add(primarySchool);
        list.add(middleSchool);
        list.add(university);

        request.setEduBackground(list);

        // 打印创建完成的请求对象
        System.out.println(request.toString());

        // 调用序列化方法将对象转换成字节数组
        byte[] bs = request.serialize(false);

        // 打印生成的字节数组
        for (byte b : bs) {
            System.out.print(b + " ");
        }
        System.out.println();

        // 调用返序列化方法，将字节数组转换为对象
        TestRequest request2 = (TestRequest) AbstractPack.deserialize(bs, TestRequest.class);
        System.out.println(request2);
    }
}
