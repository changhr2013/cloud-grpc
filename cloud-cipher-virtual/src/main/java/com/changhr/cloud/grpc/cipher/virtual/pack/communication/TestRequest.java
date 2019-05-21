package com.changhr.cloud.grpc.cipher.virtual.pack.communication;

import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;
import lombok.Data;

import java.util.List;

/**
 * 测试请求包
 * 
 * @author changhr
 */
@PackType(typeNo = 100)
@ColumnProperty(type = ColumnType.OBJECT, clazz = TestRequest.class)
@Data
public class TestRequest extends AbstractPack {

    /**
     * 在进行tcp通讯时，根据此序列来对应请求包和返回包
     */
    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.LONG)
    public long seq;

    /**
     * 用户名
     */
    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.STRING)
    public String userName;

    /**
     * 密码
     */
    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.STRING)
    public String pwd;

    /**
     * 真实姓名
     */
    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.STRING)
    public String trueName;

    /**
     * 手机号
     */
    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.LONG)
    public long telNo;

    /**
     * 性别，0为女性、1为男性
     */
    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.BYTE)
    public byte gender;

    /**
     * 年龄
     */
    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.SHORT)
    public short age;

    /**
     * 身高
     */
    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.SHORT)
    public short height;

    /**
     * 体重
     */
    @FieldOrder(9)
    @ColumnProperty(type = ColumnType.FLOAT)
    public float weight;

    /**
     * 教育背景
     */
    @FieldOrder(10)
    @ColumnProperty(type = ColumnType.LIST_OBJECT, clazz = EducationalBackground.class)
    public List<EducationalBackground> eduBackground;

    @FieldOrder(11)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    public byte[] randomId;
}
