package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 2. 密钥记录
 *
 * @author changhr
 * @create 2019-06-26 20:38
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = KeyRec.class)
@Getter
@Setter
@ToString
public class KeyRec extends AbstractPack {

    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.INT)
    private int version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.INT)
    private int reserved;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.LONG)
    private long timeStamp;

    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] keyId;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] keyLabel;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] ownerUserId;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] ownerKeyLabel;

    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 8)
    private byte[] developerId;

    @FieldOrder(9)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 8)
    private byte[] appId;

    @FieldOrder(10)
    @ColumnProperty(type = ColumnType.LONG)
    private long startTime;

    @FieldOrder(11)
    @ColumnProperty(type = ColumnType.LONG)
    private long endTime;

    @FieldOrder(12)
    @ColumnProperty(type = ColumnType.INT)
    private int flag;

    @FieldOrder(13)
    @ColumnProperty(type = ColumnType.INT)
    private int bits;

    @FieldOrder(14)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 40)
    private byte[] keyCloudEnc;

    @FieldOrder(15)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] mac;
}
