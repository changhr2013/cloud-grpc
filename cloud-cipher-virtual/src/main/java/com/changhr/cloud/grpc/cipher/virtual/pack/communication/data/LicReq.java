package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 6. 许可请求
 *
 * @author changhr
 * @create 2019-06-26 16:59
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = LicReq.class)
@Getter
@Setter
@ToString
public class LicReq extends AbstractPack {

    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.INT)
    private int version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] fatherLicId;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] ownerUserId;

    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] ownerKeyLabel;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] keyId;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.INT)
    private int reserved;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.LONG)
    private long timeStamp;

    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.OBJECT, clazz = LicLimited.class)
    private LicLimited licLimited;

    @FieldOrder(9)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 256)
    private byte[] signature;
}
