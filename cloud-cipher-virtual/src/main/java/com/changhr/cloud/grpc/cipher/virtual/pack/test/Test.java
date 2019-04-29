package com.changhr.cloud.grpc.cipher.virtual.pack.test;

import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import com.changhr.cloud.grpc.cipher.virtual.pack.communication.EducationalBackground;

import java.lang.reflect.Field;
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
//        request.seq = 0;
//        request.userName = "zhangsanfeng";
//        request.pwd = "123456";
//        request.trueName = "张三丰";
//        request.telNo = 18311111111L;
//        request.gender = 1;
//        request.age = 24;
//        request.height = 176;
//        request.weight = 65.5F;
//
//        EducationalBackground primarySchool = new EducationalBackground();
//        primarySchool.startYear = 1998;
//        primarySchool.endYear = 2003;
//        primarySchool.school = "实验小学";
//
//        EducationalBackground middleSchool = new EducationalBackground();
//        middleSchool.startYear = 2003;
//        middleSchool.endYear = 2009;
//        middleSchool.school = "八一中学";
//
//        EducationalBackground university = new EducationalBackground();
//        university.startYear = 2009;
//        university.endYear = 2013;
//        university.school = "清华大学";
//
//        List<EducationalBackground> list = new ArrayList<>();
//        list.add(primarySchool);
//        list.add(middleSchool);
//        list.add(university);
//
//        request.eduBackground = list;
//
//        // 打印创建完成的请求对象
//        System.out.println(request.toString());
//
//        // 调用序列化方法将对象转换成字节数组
//        byte[] bs = request.serialize();
//
//        // 打印生成的字节数组
//        System.out.println(bs.toString());
//        for (byte b : bs) {
//            System.out.print(b + " ");
//        }
//        System.out.println();
//
//        // 调用返序列化方法，将字节数组转换为对象
//        TestRequest request2 = (TestRequest) AbstractPack.deserialize(bs);
//
//        // 打印通过字节数组转换的对象
//        System.out.println(request2);

        EducationalBackground middleSchool = new EducationalBackground();
        middleSchool.setStartYear((short) 2003);
        middleSchool.setEndYear((short) 2009);
        middleSchool.setSchool("八一中学");

        Field[] fields = EducationalBackground.class.getDeclaredFields();
        SortedSet<Field> sortedFields = new TreeSet<>(new FieldComparator());
        sortedFields.addAll(Arrays.asList(fields));

        for (Field sortedField : sortedFields) {
            System.out.println(sortedField.getName());
        }
    }
    private static class FieldComparator implements Comparator<Field> {
        @Override
        public int compare(Field field1, Field field2) {
            return Integer.compare(field1.getAnnotation(FieldOrder.class).value(),
                    field2.getAnnotation(FieldOrder.class).value());
        }
    }
}
