package com.changhr.cloud.grpc.cipher.virtual.pack;

import com.changhr.cloud.grpc.cipher.virtual.pack.annotation.PackType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 包授权管理
 * @author changhr
 */
public class PackAuthorize {

    /**
     * 存放通讯包类型号和通讯包对应关系
     */
    private static final ConcurrentMap<Short, Class<?>> typeNoAndPackMap;

    static{
        typeNoAndPackMap = new ConcurrentHashMap<>();
        registerPack(
                com.changhr.cloud.grpc.cipher.virtual.pack.communication.EducationalBackground.class,
                com.changhr.cloud.grpc.cipher.virtual.pack.communication.TestRequest.class
        );
    }

    private PackAuthorize(){}

    private static PackAuthorize instance;

    public static PackAuthorize getInstance(){
        if(instance == null){
            synchronized(PackAuthorize.class){
                if(instance == null){
                    instance = new PackAuthorize();
                }
            }
        }
        return instance;
    }

    /**
     * 注册通讯包和包类型号的对应关系管理
     * @param classes pack
     */
    public static void registerPack(Class<?>... classes){

        for(Class<?> clazz : classes){
            PackType packType = clazz.getAnnotation(PackType.class);
            typeNoAndPackMap.put(packType.typeNo(), clazz);
        }
    }

    public ConcurrentMap<Short, Class<?>> getTypeNoAndPackMap() {
        return typeNoAndPackMap;
    }

}