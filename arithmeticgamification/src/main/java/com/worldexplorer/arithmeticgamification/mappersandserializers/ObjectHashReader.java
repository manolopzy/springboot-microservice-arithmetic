package com.worldexplorer.arithmeticgamification.mappersandserializers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldexplorer.arithmeticgamification.entity.GameStats;

@ReadingConverter
public class ObjectHashReader implements Converter<byte[], GameStats> {
	private final Jackson2JsonRedisSerializer<GameStats> serializer;

	  public ObjectHashReader() {

	    serializer = new Jackson2JsonRedisSerializer<GameStats>(GameStats.class);
	    serializer.setObjectMapper(new ObjectMapper());
	  }

	  @Override
	  public GameStats convert(byte[] value) {
	    return serializer.deserialize(value);
	  }
}
