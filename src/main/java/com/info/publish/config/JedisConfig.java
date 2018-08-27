package com.info.publish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Bean(name= "jedisPool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig config,
                               @Value("${spring.jedis.pool.host}")String host,
                               @Value("${spring.jedis.pool.port}")int port,
                               @Value("${spring.jedis.pool.timeout}")int timeout) {
            return new JedisPool(config, host, port,timeout);
    }

    @Bean(name= "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig (@Value("${spring.jedis.pool.config.maxTotal}")int maxTotal,
                                            @Value("${spring.jedis.pool.config.maxIdle}")int maxIdle,
                                            @Value("${spring.jedis.pool.config.maxWaitMillis}")int maxWaitMillis) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWaitMillis);
            return config;
        }


}
