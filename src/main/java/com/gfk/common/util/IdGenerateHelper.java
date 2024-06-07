package com.gfk.common.util;

/**
 * ID生成器
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class IdGenerateHelper {

    /**
     * 获取ID
     * @return
     */
    public static String nextId() {
//        return UUID.randomUUID().toString();
        return SnowFlake.nextId();
    }
}
