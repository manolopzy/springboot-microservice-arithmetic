package com.worldexplorer.arithmeticgamification.mappersandserializers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldexplorer.arithmeticgamification.entity.GameStats;

@WritingConverter
public class ObjectHashWriter implements Converter<GameStats, byte[]> {

	private final Jackson2JsonRedisSerializer<GameStats> serializer;

	public ObjectHashWriter() {
		serializer = new Jackson2JsonRedisSerializer<GameStats>(GameStats.class);
		serializer.setObjectMapper(new ObjectMapper());
	}

	@Override
	public byte[] convert(GameStats value) {
		return serializer.serialize(value);
	}

}
