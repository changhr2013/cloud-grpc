package com.changhr.cloud.grpc.cipher.virtual.pack.communication;

import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;
import lombok.Data;

import java.util.List;

/**
 * 测试请求包
 * 
 * @author changhr
 */
@PackType(typeNo = 100)
@Data
public class TestRequest extends AbstractPack {

    /**
     * 在进行tcp通讯时，根据此序列来对应请求包和返回包
     */
    @ColumnProperty(type = ColumnType.LONG)
    private long seq;

    /**
     * 用户名
     */
    @ColumnProperty(type = ColumnType.STRING)
    private String userName;

    /**
     * 密码
     */
    @ColumnProperty(type = ColumnType.STRING)
    private String pwd;

    /**
     * 真实姓名
     */
    @ColumnProperty(type = ColumnType.STRING)
    private String trueName;

    /**
     * 手机号
     */
    @ColumnProperty(type = ColumnType.LONG)
    private long telNo;

    /**
     * 性别，0为女性、1为男性
     */
    @ColumnProperty(type = ColumnType.BYTE)
    private byte gender;

    /**
     * 年龄
     */
    @ColumnProperty(type = ColumnType.SHORT)
    private short age;

    /**
     * 身高
     */
    @ColumnProperty(type = ColumnType.SHORT)
    private short height;

    /**
     * 体重
     */
    @ColumnProperty(type = ColumnType.FLOAT)
    private float weight;

    /**
     * 教育背景
     */
    @ColumnProperty(type = ColumnType.LIST_OBJECT)
    private List<EducationalBackground> eduBackground;
}
