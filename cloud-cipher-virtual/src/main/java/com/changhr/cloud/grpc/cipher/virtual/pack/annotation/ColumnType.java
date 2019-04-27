package com.changhr.cloud.grpc.cipher.virtual.pack.annotation;

import com.changhr.cloud.grpc.cipher.virtual.pack.AbstractPack;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 字段类型
 * @author changhr
 */
public enum ColumnType {

    /** 字段类型 */
    BYTE{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.put(n.byteValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.get();
        }
    },

    SHORT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.putShort(n.shortValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.getShort();
        }

    },

    INT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.putInt(n.intValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.getInt();
        }

    },

    LONG{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.putLong(n.longValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.getLong();
        }
    },

    FLOAT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.putFloat(n.floatValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.getFloat();
        }

    },

    DOUBLE{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            Number n = (Number)obj;
            bf.putDouble(n.doubleValue());
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return bf.getDouble();
        }

    },

    STRING{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            byte[] bs = obj.toString().getBytes("UTF-8");
            bf.putShort((short)bs.length);
            bf.put(bs);
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {

            short len = bf.getShort();
            if(len > 0){
                byte[] bs = new byte[len];
                bf.get(bs);
                return new String(bs,"UTF-8");
            }

            return "";
        }

    },

    OBJECT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            byte[] bs =new byte[]{};
            if (obj instanceof AbstractPack) {
                AbstractPack newAbstractPack = (AbstractPack) obj;
                bs = newAbstractPack.serialize();
            }
            bf.put(bs);
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            return AbstractPack.deserialize(bf);
        }

    },

    BYTE_ARRAYS{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            byte[] bs = (byte[]) obj;
            bf.put(bs);

        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            short len = bf.getShort();
            byte[] bs = new byte[len];
            bf.get(bs);
            return bs;
        }

    },

    LIST_INT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>)obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                INT.serialize(bf, item);
            }
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            short len = bf.getShort();
            List<Object> list = new ArrayList<Object>(len);
            if(len > 0){
                for(int i=0; i<len; i++){
                    list.add(INT.deserialize(bf));
                }
            }
            return list;
        }

    },

    LIST_STRING{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>)obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                STRING.serialize(bf, item);
            }

        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            short len = bf.getShort();
            List<Object> list = new ArrayList<Object>(len);
            if(len > 0){
                for(int i=0; i<len; i++){
                    list.add(STRING.deserialize(bf));
                }
            }
            return list;
        }

    },

    LIST_OBJECT{

        @Override
        public void serialize(ByteBuffer bf, Object obj) throws Exception {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>)obj;
            bf.putShort((short) list.size());
            for (Object item : list) {
                OBJECT.serialize(bf, item);
            }
        }

        @Override
        public Object deserialize(ByteBuffer bf) throws Exception {
            short len = bf.getShort();
            List<Object> list = new ArrayList<Object>(len);
            if(len > 0){
                for(int i=0; i<len; i++){
                    list.add(OBJECT.deserialize(bf));
                }
            }
            return list;
        }

    };

    /**
     * 序列化
     * @param bf
     * @param obj
     * @throws Exception
     */
    public abstract void serialize(ByteBuffer bf, Object obj) throws Exception;

    /**
     * 返序列化
     * @param bf
     * @return Object
     * @throws Exception
     */
    public abstract Object deserialize(ByteBuffer bf) throws Exception;
}
