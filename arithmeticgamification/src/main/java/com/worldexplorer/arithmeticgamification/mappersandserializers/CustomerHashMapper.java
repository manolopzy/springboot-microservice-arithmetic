package com.worldexplorer.arithmeticgamification.mappersandserializers;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

/**
 * Direct mapping, by using HashOperations and a serializer
 * 
 * Using Redis Repositories
 * 
 * Using HashMapper and HashOperations
 * 
 * @author tanku
 *
 */
public class CustomerHashMapper<T> {

	//@Resource(name = "keyvalueJsonRedisTemplate")
	HashOperations<String, byte[], byte[]> hashOperations;

	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

	public void writeHash(String key, T entity) {

		Map<byte[], byte[]> mappedHash = mapper.toHash(entity);
		hashOperations.putAll(key, mappedHash);
	}

	@SuppressWarnings("unchecked")
	public T loadHash(String key) {

		Map<byte[], byte[]> loadedHash = hashOperations.entries(key);
		return (T) mapper.fromHash(loadedHash);
	}
}
