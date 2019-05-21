package com.changhr.cloud.grpc.cipher.virtual.pack.utils;

/**
 * 获取唯一序列
 * @author daidai
 */
public class SequenceIdUtil {

    private final int beginValue = 0;
    private final int maxValue = Integer.MAX_VALUE;
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
            if (value < beginValue) {
                value = beginValue;
            }

            if(value >= maxValue){
                value = beginValue;
            }

            value += STEP;

        return value;
    }

}