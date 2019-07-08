package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 3. 公钥信息
 *
 * @author changhr
 * @create 2019-06-26 10:10
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = PublicKeyReq.class)
@Getter
@Setter
@ToString
public class PublicKeyReq extends AbstractPack {

    /**
     * 0x10002001
     */
    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.INT)
    private int version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.INT)
    private int attribute;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] keyLabel;

    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] ownerUserId;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.LONG)
    private long timeStamp;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.INT)
    private int flag;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.INT)
    private int bits;

    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.INT)
    private int keyLen;

    @FieldOrder(9)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 300)
    private byte[] keyValue;

    @FieldOrder(10)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] mac;
}
