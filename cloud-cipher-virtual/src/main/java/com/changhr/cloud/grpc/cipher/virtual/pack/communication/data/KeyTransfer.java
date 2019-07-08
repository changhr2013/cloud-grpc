package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 9. 密钥所有权转移请求
 *
 * @author changhr
 * @create 2019-06-27 10:21
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = KeyTransfer.class)
@Getter
@Setter
@ToString
public class KeyTransfer extends AbstractPack {

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
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] newOwnerUserId;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] newOwnerKeyLabel;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 256)
    private byte[] signature;
}
