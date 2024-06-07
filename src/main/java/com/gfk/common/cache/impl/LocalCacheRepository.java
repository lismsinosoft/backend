package com.gfk.common.cache.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.gfk.common.cache.CacheRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 应用本地缓存操作服务
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
//@Service
public class LocalCacheRepository implements CacheRepository {

    private final ConcurrentHashMap<String, TimedObject> container = new ConcurrentHashMap<>();


    /**
     * 存入对象到redis
     * @param key   索引
     * @param value 值
     */
    public void set(String key, Object value) {
        container.put(key, new TimedObject(value));
    }

    /**
     * 删除对象
     * @param key   索引
     */
    public void delete(String key) {
        container.remove(key);
    }

    /**
     * 删除对象
     * @param prefix   前缀
     * @param key   索引
     */
    public void delete(String prefix, String key) {
        this.delete(prefix +":"+ key);
    }

    /**
     * 存入对象到redis
     * @param key   索引
     * @param value 值
     * @param timeout 过期时间
     * @param unit 过期时间单位（TimeUnit）
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        long convert = unit.convert(timeout, TimeUnit.MILLISECONDS);
        container.put(key, new TimedObject(value, convert));
    }

    /**
     * 存入对象到redis
     * @param prefix   索引前缀
     * @param key   索引
     * @param value 值
     * @param timeout 过期时间
     * @param unit 过期时间单位（TimeUnit）
     */
    public void set(String prefix, String key, Object value, long timeout, TimeUnit unit) {
        this.set(prefix +":"+ key, value, timeout, unit);
    }

    /**
     * 存入对象到redis
     * @param prefix   索引前缀
     * @param key   索引
     * @param value 值
     */
    public void set(String prefix, String key, Object value) {
        container.put(prefix +":"+ key, new TimedObject(value));
    }

    /**
     * 获取对象
     * @param prefix   索引前缀
     * @param key   索引
     */
    public Object get(String prefix, String key) {
        return this.get(prefix +":"+ key);
    }

    /**
     * 获取对象
     * @param key   索引
     */
    public Object get(String key) {
        long currentTime = System.currentTimeMillis();
        TimedObject timedObject = container.get(key);
        if (null != timedObject) {
            long expired = timedObject.getExpired();
            if (currentTime < expired) {
                return timedObject.domain;
            }
        }
        return timedObject;
    }

    /**
     * 获取对象过期时间
     * @param prefix   索引前缀
     * @param key   索引
     */
    public Long getExpireTime(String prefix, String key,TimeUnit unit) {
        return this.getExpireTime(prefix +":"+ key,unit);
    }

    /**
     * 获取对象过期时间
     * @param key   索引
     */
    public Long getExpireTime(String key,TimeUnit unit) {
        TimedObject timedObject = container.get(key);
        if (timedObject != null) {
            long expired = timedObject.getExpired();
            return unit.convert(expired, unit);
        }
        return null;
    }

    @Data
    static class TimedObject {
        private Object domain;
        private long expired ;

        public TimedObject(Object domain) {
            this.domain = domain;
            this.expired = Long.MAX_VALUE;
        }

        public TimedObject(Object domain, long expired) {
            this.domain = domain;
            this.expired = expired;
        }
    }
}
