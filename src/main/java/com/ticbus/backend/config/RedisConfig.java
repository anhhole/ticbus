package com.ticbus.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author AnhLH
 */
@Configuration
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String redisHostName;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(redisHostName);
    factory.setPort(redisPort);
    factory.setUsePool(true);
    return factory;
  }

  @Bean
  <T> RedisTemplate<String, T> redisTemplate() {
    RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }
}
