package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 4. 许可条款
 *
 * @author changhr
 * @create 2019-06-26 16:55
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = LicLimited.class)
@Getter
@Setter
@ToString
public class LicLimited extends AbstractPack {

    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.INT)
    private int version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.INT)
    private int flag;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.LONG)
    private long startTime;

    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.LONG)
    private long endTime;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.LONG)
    private long firstTime;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.LONG)
    private long times;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.INT)
    private int policy;

    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.INT)
    private int reserved;
}
