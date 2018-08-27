package com.info.publish.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.publish.service.IRedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService implements IRedisService {

    @Autowired
    private JedisPool jedisPool;
    
    public void set(String key, String value) {
    	try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public String get(String key) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.get(key);
        }
    }

    public void del(String key) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		jedis.del(key);
        }
    }

    public void setex(String key, String value, int timeout) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		jedis.setex(key, timeout, value);
        }
    }

    public long incr(String key) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.incr(key);
    	}
    }

    public long decr(String key) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.decr(key);
    	}
    }

    public long incrby(String key, long num) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.incrBy(key, num);
    	}
    }

    public long decrby(String key, long num) {
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.decrBy(key, num);
    	}
    }
    
    public void lpush(String key, String value){
    	try (Jedis jedis = jedisPool.getResource()) {
    		jedis.lpush(key, value);
    	}
    }
    
    public List<String> lrange(String key){
    	try (Jedis jedis = jedisPool.getResource()) {
    		return jedis.lrange(key, 0L, jedis.llen(key));
    	}
    }
    
    public void delete(String pattern){
    	try (Jedis jedis = jedisPool.getResource()) {
    		Set<String> keys = jedis.keys(pattern);
    		for(String key : keys){
    			jedis.del(key);
    		}
    	}
    }

	public Set<String> keys(String pattern) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.keys(pattern);
    	}
	}

	public boolean exists(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.exists(key);
        }
	}
}
