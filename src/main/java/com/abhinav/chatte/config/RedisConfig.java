package com.abhinav.chatte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
    jedisConFactory.setHostName("127.0.0.1");
    jedisConFactory.setPort(6379);
    return jedisConFactory;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
