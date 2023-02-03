package com.cherry.img.utils.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Slf4j
public class JedisService {
    @Bean
    public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.jedis.pool.max-active}") int maxActive,
                                           @Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
                                           @Value("${spring.redis.jedis.pool.min-idle}") int minIdle,
                                           @Value("${spring.redis.jedis.pool.max-wait}") long maxWaitMillis) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(@Value("${spring.redis.host}") String redisHost,
                               @Value("${spring.redis.password}") String password,
                               @Value("${spring.redis.port}") int redisPort,
                               @Value("${spring.redis.timeout}") int timeout, JedisPoolConfig jedisPoolConfig) {

        log.info("=====创建JedisPool连接池=====");
        if (StringUtils.isNotEmpty(password)) {
            return new JedisPool(jedisPoolConfig, redisHost, redisPort, timeout, password);
        }
        return new JedisPool(jedisPoolConfig, redisHost, redisPort, timeout);
    }
}
