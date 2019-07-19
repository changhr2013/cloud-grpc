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
    private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

    /**
     * 当前包的允许最大长度
     */
    private static final int PACK_MAX_SIZE = 2048;

    /**
     * 获取包类型号typeNo，包类型号不允许重复，一个包类型号对应一个包
     *
     * @return short
     */
    private short getTypeNo() {
        @SuppressWarnings("rawtypes")
        Class clazz = this.getClass();
        PackType packType = (PackType) clazz.getAnnotation(PackType.class);
        return packType.typeNo();
    }

    /**
     * 序列化对象，将包对象转化为字节数组
     *
     * @param havePackNo 序列化的字节数组是否带数据包类型号
     * @return byte[] 序列化后的字节数组
     * @throws Exception 异常
     */
    public byte[] serialize(boolean havePackNo) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(PACK_MAX_SIZE);
        buffer.order(BYTE_ORDER);

        if (havePackNo) {
            buffer.putShort(this.getTypeNo());
        }

        SortedSet<Field> fields = sortFields(this.getClass().getDeclaredFields());
        for (Field field : fields) {
            ColumnProperty cp = field.getAnnotation(ColumnProperty.class);
            field.setAccessible(true);
            Object obj = field.get(this);
            try {
                final ColumnType type = cp.type();
                type.serialize(buffer, obj, havePackNo);
            } catch (Exception e) {
                throw new Exception("#socket_serialize_error " + field + " " + obj + " " + cp + " " + e, e);
            }
        }

        byte[] result = new byte[buffer.position()];
        System.arraycopy(buffer.array(), 0, result, 0, result.length);
        return result;
    }

    /**
     * 反序列化，将字节数组转换为包对象
     *
     * @param data  待反序列化的字节数组
     * @param clazz 待反序列化的字节数组代表的类
     * @return {@link AbstractPack}
     */
    public static AbstractPack deserialize(byte[] data, Class clazz) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.order(BYTE_ORDER);

        return deserialize(byteBuffer, clazz);
    }

    /**
     * 反序列化，将字节数组转换为包对象
     *
     * @param data 待反序列化的字节数组
     * @return {@link AbstractPack}
     */
    public static AbstractPack deserialize(byte[] data) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.order(BYTE_ORDER);

        return deserialize(byteBuffer);
    }

    /**
     * 反序列化，将字节缓冲区转换为包对象
     *
     * @param byteBuffer 字节缓冲 buffer
     * @param clazz      字节表示的类
     * @return {@link AbstractPack}
     * @throws Exception 异常
     */
    public static AbstractPack deserialize(ByteBuffer byteBuffer, Class clazz) throws Exception {
        return getPack(byteBuffer, clazz);
    }

    /**
     * 反序列化，将字节缓冲区转换为包对象
     *
     * @param byteBuffer 字节缓冲 buffer
     * @return {@link AbstractPack}
     * @throws Exception 异常
     */
    public static AbstractPack deserialize(ByteBuffer byteBuffer) throws Exception {

        short typeNo = byteBuffer.getShort();

        Class<?> clazz = PackAuthorize.getInstance().getTypeNoAndPackMap().get(typeNo);

        return getPack(byteBuffer, clazz);
    }

    /**
     * 反序列化字节缓冲区的字节数组为对应的对象
     *
     * @param byteBuffer 字节缓冲区 buffer
     * @param clazz      对象 Class
     * @return {@link AbstractPack}
     * @throws Exception 异常
     */
    private static AbstractPack getPack(ByteBuffer byteBuffer, Class<?> clazz) throws Exception {

        AbstractPack abstractPack = (AbstractPack) clazz.newInstance();

        SortedSet<Field> fields = sortFields(clazz.getDeclaredFields());
        for (Field field : fields) {
            ColumnProperty cp = field.getAnnotation(ColumnProperty.class);
            field.setAccessible(true);
            try {
                final ColumnType type = cp.type();
                if (cp.clazz() != Class.class || cp.length() != 0) {
                    field.set(abstractPack, type.deserialize(byteBuffer, cp));
                } else {
                    field.set(abstractPack, type.deserialize(byteBuffer));
                }
            } catch (Exception e) {
                throw new Exception("#socket_serialize_error " + field + " " + cp + " " + e, e);
            }
        }
        return abstractPack;
    }

    /**
     * 按照注解{@link FieldOrder}中的数字对反射后的 Field 排序
     *
     * @param fields 反射得到的 Field 数组
     * @return SortedSet<Field> 排序的 Field 集合
     */
    private static SortedSet<Field> sortFields(Field[] fields) {
        SortedSet<Field> sortedSet =
                new TreeSet<>(Comparator.comparingInt(field -> field.getAnnotation(FieldOrder.class).value()));
        sortedSet.addAll(Arrays.asList(fields));
        return sortedSet;
    }
}
