package com.changhr.cloud.grpc.cipher.virtual.pack.communication;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;
import lombok.Data;

/**
 * 测试二级请求包
 *
 * @author daidai
 */
@PackType(typeNo = 101)
@Data
public class EducationalBackground extends AbstractPack {

    /**
     * 开始年份
     */
    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.SHORT)
    private short startYear;

    /**
     * 结束年份
     */
    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.SHORT)
    private short endYear;

    /**
     * 学校名称
     */
    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.STRING)
    private String school;
}
