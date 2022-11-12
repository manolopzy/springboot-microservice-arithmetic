package com.worldexplorer.arithmeticgamification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
//@Configuration
public class RedisConfig {
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		//if there is only one redis server (standalone)
		//used for setting up {@link RedisConnection} via {@link RedisConnectionFactory}
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(configuration);
	    return jedisConFactory;
	}

	/**
	 * the template is thread safe, can be reused across multiple instances
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}
}
