package com.worldexplorer.arithmeticgamification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.worldexplorer.arithmeticgamification.entity.GameStats;
@Component
public class GamestatsHashRepository {
	
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	
	@Autowired
	private RedisKeyValueTemplate redisKeyValueTemplate;
	
	public void setScore(long userId, int quantity) {
		
		PartialUpdate<GameStats> update = 
				new PartialUpdate<GameStats>((Long)userId, GameStats.class).set("score", quantity);
		redisKeyValueTemplate.update(update);
		
	}
	
	public void updateScore(long userId, int quantity) {
		RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
		connection.hIncrBy(null, null, quantity);
	}
}
