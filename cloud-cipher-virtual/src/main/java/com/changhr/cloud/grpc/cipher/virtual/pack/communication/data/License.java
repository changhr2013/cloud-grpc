package com.changhr.cloud.grpc.cipher.virtual.pack.communication.data;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 5. 许可信息
 *
 * @author changhr
 * @create 2019-06-26 20:47
 */
@ColumnProperty(type = ColumnType.OBJECT, clazz = License.class)
@Getter
@Setter
@ToString
public class License extends AbstractPack {

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
    private byte[] licId;

    @FieldOrder(5)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] fatherLicId;

    @FieldOrder(6)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] issuerUserId;

    @FieldOrder(7)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] ownerUserId;

    @FieldOrder(8)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] ownerKeyLabel;

    @FieldOrder(9)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 16)
    private byte[] keyId;

    @FieldOrder(10)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] keyLabel;

    @FieldOrder(11)
    @ColumnProperty(type = ColumnType.OBJECT, clazz = LicLimited.class)
    private LicLimited licLimited;

    @FieldOrder(12)
    @ColumnProperty(type = ColumnType.BYTE_ARRAYS, length = 32)
    private byte[] mac;
}
