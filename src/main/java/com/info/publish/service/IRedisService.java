package com.info.publish.service;

import java.util.List;
import java.util.Set;

public interface IRedisService {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    void set(String key, String value);


    /**
     * 获取缓存内容
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 删除缓存内容
     *
     * @param key
     * @return
     */
    void del(String key);

    /**
     * 设置一个key并设置超时时间
     * @param key
     * @param value
     * @param timeout 单位秒
     */
    void setex(String key,String value,int timeout);

    /**
     * 自增
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 自减
     * @param key
     * @return
     */
    long decr(String key);

    /**
     * 自增一个数
     * @param key
     * @param num
     * @return
     */
    long incrby(String key, long num);

    /**
     * 自减一个数
     * @param key
     * @param num
     * @return
     */
    long decrby(String key, long num);
    
    /**
     * redis list集合中添加元素
     * @param key
     * @param value
     */
    void lpush(String key, String value);
    
    /**
     * 根据key获取redis集合中的数据
     * @param key
     * @return
     */
    List<String> lrange(String key);

    /**
     * 根据pattern匹配删除
     * @param pattern
     */
    void delete(String pattern);
    
    /**
     * 获取所有匹配pattern的key
     * @param pattern
     */
    Set<String> keys(String pattern);
    
    /**
     * 判断key是否存在
     * @param key
     */
    boolean exists(String key);
}
