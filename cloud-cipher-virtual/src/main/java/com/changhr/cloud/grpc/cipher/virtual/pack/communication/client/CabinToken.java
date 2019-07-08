package com.changhr.cloud.grpc.cipher.virtual.pack.communication.client;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cabin Token
 *
 * @author changhr
 * @create 2019-07-02 14:27
 */

@ColumnProperty(type = ColumnType.OBJECT, clazz = CabinToken.class)
@Getter
@Setter
@ToString
public class CabinToken extends AbstractPack {

    @FieldOrder(1)
    @ColumnProperty(type = ColumnType.BYTE)
    private byte version;

    @FieldOrder(2)
    @ColumnProperty(type = ColumnType.BYTE)
    private byte type;

    @FieldOrder(3)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] licKeyId;

    @FieldOrder(4)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] licenseId;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] receiverDoeUserId;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] issuerDoeUserId;
}
