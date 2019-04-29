package com.changhr.cloud.grpc.cipher.virtual.pack;

import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnProperty;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.ColumnType;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.FieldOrder;
import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * 通用包父类
 *
 * @author changhr
 */
public abstract class AbstractPack {

    /**
     * 字节序
     */
    final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

    /**
     * 当前包的允许最大长度
     */
    final int packMaxSize = 1500;

    /**
     * 获取包类型号typeNo，包类型号不允许重复，一个包类型号对应一个包
     *
     * @return short
     */
    public final short getTypeNo() {
        @SuppressWarnings("rawtypes")
        Class clazz = this.getClass();
        @SuppressWarnings("unchecked")
        PackType packType = (PackType) clazz.getAnnotation(PackType.class);
        return packType.typeNo();
    }

    /**
     * 序列化，将包对象转化为字节数组
     *
     * @return byte[]
     * @throws Exception
     */
    public byte[] serialize() throws Exception {

        ByteBuffer bf = ByteBuffer.allocate(packMaxSize);
        bf.order(byteOrder);
        bf.putShort(this.getTypeNo());

        SortedSet<Field> fields = sortFields(this.getClass().getDeclaredFields());
        for (Field field : fields) {
            ColumnProperty cp = field.getAnnotation(ColumnProperty.class);
            Object obj = field.get(this);
            try {
                final ColumnType type = cp.type();
                type.serialize(bf, obj);
            } catch (Exception e) {
                throw new Exception("#socket_serialize_error " + field + " " + obj + " " + cp + " " + e, e);
            }
        }

        byte[] result = new byte[bf.position()];
        System.arraycopy(bf.array(), 0, result, 0, result.length);
        return result;
    }

    /**
     * 反序列化，将字节数组转换为包对象
     *
     * @param datas 待反序列化的字节数组
     * @return AbstractPack
     */
    public static AbstractPack deserialize(byte[] datas) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.wrap(datas);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        return deserialize(byteBuffer);
    }

    /**
     * 反序列化，将字节缓冲区转换为包对象
     *
     * @param byteBuffer
     * @return AbstractPack
     * @throws Exception
     */
    public static AbstractPack deserialize(ByteBuffer byteBuffer) throws Exception {

        short typeNo = byteBuffer.getShort();

        Class<?> clazz = PackAuthorize.getInstance().getTypeNoAndPackMap().get(typeNo);

        return getPack(byteBuffer, clazz);
    }

    private static AbstractPack getPack(ByteBuffer byteBuffer, Class<?> clazz) throws Exception {

        AbstractPack abstractPack = (AbstractPack) clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ColumnProperty cp = field.getAnnotation(ColumnProperty.class);
            field.setAccessible(true);
            try {
                final ColumnType type = cp.type();
                field.set(abstractPack, type.deserialize(byteBuffer));
            } catch (Exception e) {
                throw new Exception("#socket_serialize_error " + field + " " + cp + " " + e, e);
            }
        }
        return abstractPack;
    }

    private static SortedSet<Field> sortFields(Field[] fields) {
        SortedSet<Field> sortedSet =
                new TreeSet<>(Comparator.comparingInt(field -> field.getAnnotation(FieldOrder.class).value()));
        sortedSet.addAll(Arrays.asList(fields));
        return sortedSet;
    }

}
