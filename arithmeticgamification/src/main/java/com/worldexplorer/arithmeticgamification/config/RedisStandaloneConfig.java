package com.worldexplorer.arithmeticgamification.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.OxmSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

/**
 * In redis, the data is stored in the format of binary, it could represent
 * data of json, string, xml, or other formats depending on what kind of serializer we use
 * to convert java objects to that binary data
 * 
 * java objects -> specific serializer -> bytes array -> redis database
 * java objects <- specific serializer <- bytes array <- redis database
 * 
 * java objects -> json serializer -> json bytes array -> redis database
 * java objects <- json serializer <- json bytes array <- redis database
 * 
 * java objects -> jdk serializer -> bytes array -> redis database
 * java objects <- jdk serializer <- bytes array <- redis database
 * 
 * java objects -> xml serializer -> bytes array -> redis database
 * java objects <- xml serializer <- bytes array <- redis database
 * @author tanku
 *
 */
@Configuration
//@EnableCaching
public class RedisStandaloneConfig {
	@Value("${spring.cache.redis.time-to-live}")
    private Duration timeToLive = Duration.ZERO;
	@Value("${redis.client.pool.maxIdle}")
	private String maxIdle;
	@Value("${redis.client.pool.minIdle}")
	private String minIdle;
	
	
	@Bean
	JedisConnectionFactory redisConnectionFactory() {
		// if there is only one redis server (standalone)
		// used for setting up {@link RedisConnection} via {@link
		// RedisConnectionFactory}
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(configuration);
		return jedisConFactory;
	}

	/**
	 * the template is thread safe, can be reused across multiple instances
	 * 
	 * @return
	 */
	/**
	 * the template is thread safe, can be reused across multiple instances
	 * 
	 * this template works with the default serializer
	 * {@link JdkSerializationRedisSerializer} for key, value, hash key, hash value
	 * 
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> jdkRedisTemplate() {
		RedisTemplate<String, Object> jdkTemplate = new RedisTemplate<>();
		jdkTemplate.setConnectionFactory(redisConnectionFactory());
		return jdkTemplate;
	}

	/**
	 * the template is thread safe, can be reused across multiple instances
	 * 
	 * this template works with the default serializer {@link StringRedisSerializer}
	 * for key, value, hash key, hash value
	 * 
	 * With spring boot, the string redis template will be created by default
	 * @return
	 */
//	@Bean
//	public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		RedisTemplate<String, String> stringTemplate = new RedisTemplate<>();
//		stringTemplate.setConnectionFactory(redisConnectionFactory);
//		stringTemplate.setDefaultSerializer(stringRedisSerializer());
//		return stringTemplate;
//	}

	/**
	 * the template is thread safe, can be reused across multiple instances
	 * 
	 * this template works with the default serializer {@link StringRedisSerializer}
	 * for key, value, hash key, hash value
	 * String serializer for all keys
	 * json serializer for all values
	 * 
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> jsonTemplate = new RedisTemplate<>();
		jsonTemplate.setConnectionFactory(redisConnectionFactory);
		jsonTemplate.setKeySerializer(stringRedisSerializer());
		jsonTemplate.setValueSerializer(jsonRedisSerializer());
		jsonTemplate.setHashKeySerializer(stringRedisSerializer());
		jsonTemplate.setValueSerializer(jsonRedisSerializer());
		return jsonTemplate;
	}
	@Bean
	public RedisTemplate<String, Object> keyvalueJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> jsonTemplate = new RedisTemplate<>();
		jsonTemplate.setConnectionFactory(redisConnectionFactory);
		jsonTemplate.setKeySerializer(jsonRedisSerializer());
		jsonTemplate.setValueSerializer(jsonRedisSerializer());
		jsonTemplate.setHashKeySerializer(jsonRedisSerializer());
		jsonTemplate.setValueSerializer(jsonRedisSerializer());
		return jsonTemplate;
	}
	/**
	 * String serializer for all keys
	 * xml serializer for all values
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> xmlRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> xmlTemplate = new RedisTemplate<>();
		xmlTemplate.setConnectionFactory(redisConnectionFactory);
		xmlTemplate.setKeySerializer(stringRedisSerializer());
		xmlTemplate.setValueSerializer(xmlRedisSerializer());
		xmlTemplate.setHashKeySerializer(stringRedisSerializer());
		xmlTemplate.setValueSerializer(xmlRedisSerializer());
		return xmlTemplate;
	}

	/**
	 * UTF_8
	 * @return
	 */
	private StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}
	
	private OxmSerializer xmlRedisSerializer() {
		
		//Jaxb2Marshaller jm = new Jaxb2Marshaller();
		//return new OxmSerializer(jm, jm);
		XStreamMarshaller xsm = new XStreamMarshaller();
		return new OxmSerializer(xsm, xsm);
	}
	/**
	 * // Charset DEFAULT_CHARSET = StandardCharsets.UTF_8
	 * @return
	 */
	private Jackson2JsonRedisSerializer<Object> jsonRedisSerializer() {
		ObjectMapper objectMapper = new ObjectMapper();
		// ANY includes private and public fields
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// specify the input type, or what types can be serialized, here, the class must
		// be no final
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		// Serialize and deserialize objects from and to json bytes
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}

//	@Bean
//	public JedisPoolConfig getRedisConfig() {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxIdle(Integer.valueOf(maxIdle));
//		config.setMinIdle(Integer.valueOf(minIdle));
//		return config;
//	}
	
	
}
