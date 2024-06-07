package com.gfk.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * 本地数据缓存
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public interface CacheRepository {

    /**
     * 存入对象到redis
     * @param key   索引
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 删除对象
     * @param key   索引
     */
    void delete(String key);

    /**
     * 删除对象
     * @param prefix   前缀
     * @param key   索引
     */
    void delete(String prefix, String key);

    /**
     * 存入对象到redis
     * @param key   索引
     * @param value 值
     * @param timeout 过期时间
     * @param unit 过期时间单位（TimeUnit）
     */
    void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 存入对象到redis
     * @param prefix   索引前缀
     * @param key   索引
     * @param value 值
     * @param timeout 过期时间
     * @param unit 过期时间单位（TimeUnit）
     */
    void set(String prefix, String key, Object value, long timeout, TimeUnit unit);

    /**
     * 存入对象到redis
     * @param prefix   索引前缀
     * @param key   索引
     * @param value 值
     */
    void set(String prefix, String key, Object value);

    /**
     * 获取对象
     * @param prefix   索引前缀
     * @param key   索引
     */
    Object get(String prefix, String key);

    /**
     * 获取对象
     * @param key   索引
     */
    Object get(String key);

    /**
     * 获取对象过期时间
     * @param prefix   索引前缀
     * @param key   索引
     */
    Long getExpireTime(String prefix, String key,TimeUnit unit);
}
