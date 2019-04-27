package com.changhr.cloud.grpc.cipher.virtual.pack.communication;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;

/**
 * 测试二级请求包
 *
 * @author daidai
 */
@PackType(typeNo = 101)
public class EducationalBackground extends AbstractPack {

    /**
     * 开始年份
     */
    @ColumnProperty(type = ColumnType.SHORT)
    public short startYear;

    /**
     * 结束年份
     */
    @ColumnProperty(type = ColumnType.SHORT)
    public short endYear;

    /**
     * 学校名称
     */
    @ColumnProperty(type = ColumnType.STRING)
    public String school;

    @Override
    public String toString() {
        return "EducationalBackground [startYear=" + startYear + ", endYear="
                + endYear + ", school=" + school + "]";
    }

}
