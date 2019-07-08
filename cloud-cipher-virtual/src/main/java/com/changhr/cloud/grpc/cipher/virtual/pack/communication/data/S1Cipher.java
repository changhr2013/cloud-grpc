package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 7. S1密文数据包
 *
 * @author changhr
 * @create 2019-06-26 21:19
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = S1Cipher.class)
@Getter
@Setter
@ToString
public class S1Cipher extends AbstractPack {

    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.INT)
    private int version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.INT)
    private int length;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 256)
    private byte[] cipher;
}
