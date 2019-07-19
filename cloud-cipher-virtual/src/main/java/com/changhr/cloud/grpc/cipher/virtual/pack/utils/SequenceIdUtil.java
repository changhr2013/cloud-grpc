package com.changhr.cloud.grpc.cipher.virtual.pack.utils;

/**
 * 获取唯一序列
 * @author changhr
 */
public class SequenceIdUtil {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    private static final int STEP = 1;

    private int value;

    private SequenceIdUtil(){}

    private static SequenceIdUtil instance;

    public static SequenceIdUtil getInstance() {
        if (instance == null) {
            synchronized (SequenceIdUtil.class) {
                if (instance == null) {
                    instance = new SequenceIdUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取一个序列值
     * @return int
     */
        public synchronized int nextVal() {
            if (value < MIN_VALUE) {
                value = MIN_VALUE;
            }

            if(value >= MAX_VALUE){
                value = MIN_VALUE;
            }

            value += STEP;

        return value;
    }

}