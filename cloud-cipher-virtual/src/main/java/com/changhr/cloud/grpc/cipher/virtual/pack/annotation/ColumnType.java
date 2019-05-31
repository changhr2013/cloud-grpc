package com.changhr.cloud.grpc.cipher.virtual.pack.annotation;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 字段类型
 *
 * @author changhr
 */
public enum ColumnType {

    /**
     * 字段类型
     */
    BYTE {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.put(n.byteValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.get();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }
    },

    SHORT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.putShort(n.shortValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.getShort();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }

    },

    INT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.putInt(n.intValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.getInt();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }

    },

    LONG {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.putLong(n.longValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.getLong();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }
    },

    FLOAT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.putFloat(n.floatValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.getFloat();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }

    },

    DOUBLE {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            Number n = (Number) obj;
            bf.putDouble(n.doubleValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return bf.getDouble();
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            return deserialize(bf);
        }

    },

    STRING {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            byte[] bs = obj.toString().getBytes(StandardCharsets.UTF_8);
            bf.putShort((short) bs.length);
            bf.put(bs);
        }

        @Override
        public Object deserialize(ByteBuffer bf) {

            short len = bf.getShort();
            if (len > 0) {
                byte[] bs = new byte[len];
                bf.get(bs);
                return new String(bs, StandardCharsets.UTF_8);
            }

            return "";
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            int len = property.length();
            if (len > 0) {
                byte[] bs = new byte[len];
                bf.get(bs);
                return new String(bs, StandardCharsets.UTF_8);
            }
            return "";
        }

    },

    OBJECT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) throws Exception {
            byte[] bs = new byte[]{};
            if (obj instanceof AbstractPack) {
                AbstractPack newAbstractPack = (AbstractPack) obj;
                bs = newAbstractPack.serialize(havePackNo);
            }
            bf.put(bs);
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return AbstractPack.deserialize(bf);
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) throws Exception {
            return AbstractPack.deserialize(bf, property.clazz());
        }

    },

    BYTE_ARRAYS {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) {
            byte[] bs = (byte[]) obj;
            bf.put(bs);
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            short len = bf.getShort();
            byte[] bs = new byte[len];
            bf.get(bs);
            return bs;
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) {
            if (property.length() != 0) {
                byte[] bs = new byte[property.length()];
                bf.get(bs);
                return bs;
            }
            return deserialize(bf);
        }

    },

    LIST_INT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) throws Exception{
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                INT.serialize(bf, item, havePackNo);
            }
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception{
            short len = bf.getShort();
            List<Object> list = new ArrayList<>(len);
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    list.add(INT.deserialize(bf));
                }
            }
            return list;
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) throws Exception{
            return deserialize(bf);
        }

    },

    LIST_STRING {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) throws Exception{
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                STRING.serialize(bf, item, havePackNo);
            }
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception{
            short len = bf.getShort();
            List<Object> list = new ArrayList<>(len);
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    list.add(STRING.deserialize(bf));
                }
            }
            return list;
        }

        @SuppressWarnings("Duplicates")
        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) throws Exception{
            int len = property.length();
            List<Object> list = new ArrayList<>(len);
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    list.add(STRING.deserialize(bf));
                }
            }
            return list;
        }

    },

    LIST_OBJECT {
        @Override
        public void serialize(ByteBuffer bf, Object obj, boolean havePackNo) throws Exception {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                OBJECT.serialize(bf, item, havePackNo);
            }
        }

        @Override
        public Object deserialize(ByteBuffer bf) {
            return null;
        }

        @Override
        public Object deserialize(ByteBuffer bf, ColumnProperty property) throws Exception {
            short len = bf.getShort();
            List<Object> list = new ArrayList<>(len);
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    list.add(OBJECT.deserialize(bf, property));
                }
            }
            return list;
        }
    };

    /**
     * 序列化
     *
     * @param bf         序列化的字节数组
     * @param obj        序列化的目标对象
     * @param havePackNo 是否使用包定义模式
     * @throws Exception 序列化异常
     */
    public abstract void serialize(ByteBuffer bf, Object obj, boolean havePackNo) throws Exception;

    /**
     * 反序列化
     *
     * @param bf 待反序列化的字节数组
     * @return Object   反序列化出的 java 对象
     * @throws Exception 反序列化异常
     */
    public abstract Object deserialize(ByteBuffer bf) throws Exception;

    /**
     * 反序列化
     *
     * @param bf       待反序列化的字节数组
     * @param property 属性的注解对象
     * @return Object   反序列化出的 java 对象
     * @throws Exception 反序列化异常
     */
    public abstract Object deserialize(ByteBuffer bf, ColumnProperty property) throws Exception;
}
