package com.worldexplorer.arithmeticgamification;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldexplorer.arithmeticgamification.entity.Badge;
import com.worldexplorer.arithmeticgamification.entity.BadgeCard;
import com.worldexplorer.arithmeticgamification.entity.GameStats;
import com.worldexplorer.arithmeticgamification.entity.ScoreCard;
import com.worldexplorer.arithmeticgamification.repository.GameRepository;
import com.worldexplorer.arithmeticgamification.repository.GamestatsRepository;

/**
 * Tests of the repositories
 * @author tanku
 *
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests2 {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private RedisKeyValueTemplate redisKeyValueTemplate;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private GamestatsRepository gamestatsRepository;
	
	Random random = new Random();
	List<String> userIds = new ArrayList<>();
	private int numberOfUsers = 10;
	@BeforeEach
	public void setup() {
		System.out.println("test order 0");
		for (int i = 0; i < numberOfUsers; i++) {
			userIds.add(random.nextLong()+"");
		}
		
	}
	
	@Test @Order(1)
	public void testAddBadgeCard() {
		System.out.println("test order 1");
		String userId = userIds.get(random.nextInt(0, userIds.size()));
		gameRepository.addBadgeCard(Badge.BRONZE_ARITHMETIC, userId);
		Set<Object> badgeSet =  gameRepository.findAllBadgeCards(userId);
		assertThat(badgeSet).isNotNull().hasSize(1);
		TypedTuple<Object> result = gameRepository.popLastBadgeCard(userId);
		assertThat(result.getValue()).isNotNull();
		BadgeCard badgeCard = (BadgeCard)(result.getValue());
		assertThat(badgeCard.getUserId()).isEqualTo(userId);
		assertThat(badgeCard.getBadge()).isEqualTo(Badge.BRONZE_ARITHMETIC);
	}
	@Test @Order(2)
	public void testAddScoreCard() {
		System.out.println("test order 2");
		String userId = userIds.get(random.nextInt(0, userIds.size()));
		ScoreCard scoreCard = new ScoreCard(userId, random.nextLong()+"");
		gameRepository.addScoreCard(scoreCard);
		Set<Object> badgeSet =  gameRepository.findAllScoreCards(userId);
		assertThat(badgeSet).isNotNull().hasSize(1);
		TypedTuple<Object> result = gameRepository.popLastScoreCard(userId);
		assertThat(result.getValue()).isNotNull();
		ScoreCard dbScoreCard = (ScoreCard)(result.getValue());
		assertThat(dbScoreCard.getUserId()).isEqualTo(userId);
		
	}
	
	@Test @Order(3)
	public void testBasicsGameStatsRepository() {
		System.out.println("test order 3");
		String userId = userIds.get(random.nextInt(0, userIds.size()));
		System.out.println("test with user id = " + userId);
		Optional<GameStats> result = gamestatsRepository.findById(userId);
		assertThat(result.isEmpty()).isTrue();
		
		GameStats gameStats = new GameStats(userId, 43);
		GameStats dbGameStats = gamestatsRepository.save(gameStats);
		assertThat(dbGameStats).isNotNull();
		
		result = gamestatsRepository.findById(gameStats.getUserId());
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get().getUserId()).isEqualTo(userId);
		
		gamestatsRepository.deleteById(userId);
		result = gamestatsRepository.findById(userId);
		assertThat(result.isEmpty()).isTrue();
		
		
	}
	
	@Test @Order(4)
	public void testRedisHashtablePartialUpdate() {
		System.out.println("test order 4");
		String userId = userIds.get(random.nextInt(0, userIds.size()));
		GameStats gameStats = new GameStats(userId, 43);
		GameStats dbGameStats = gamestatsRepository.save(gameStats);
		assertThat(dbGameStats).isNotNull();
		
		PartialUpdate<GameStats> update = new PartialUpdate<GameStats>((String)userId, GameStats.class)
				  .set("score", 100);
		redisKeyValueTemplate.update(update);
		
		Optional<GameStats> result = gamestatsRepository.findById(gameStats.getUserId());
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get().getUserId()).isEqualTo(userId);
		assertThat(result.get().getScore()).isEqualTo(100);
		
		gamestatsRepository.deleteById(userId);
		result = gamestatsRepository.findById(userId);
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test @Order(5)
	public void testRedisHashtable() {
		System.out.println("test order 5");
	}
	
	String key = "arithmetic_gamestats:";
	@Test @Order(6)
	public void testRedisHash() {
		System.out.println("test order 6");
		String userId = userIds.get(random.nextInt(0, userIds.size()));
		
		GameStats gameStats = new GameStats(userId, 1000);
		GameStats dbGameStats = gamestatsRepository.save(gameStats);
		assertThat(dbGameStats).isNotNull();
		key += userId;
		System.out.println("key = " + key);
		String scoreField = "score";
		Object scoreObject = redisTemplate.opsForHash().get(key, scoreField);
		
		System.out.println("score = " + scoreObject);
		
		Set<Object> keySet = redisTemplate.opsForHash().keys(key);
		for (Object object : keySet) {
			System.out.println("key = " + object);
		}
		
		Map<Object, Object> keyvalueMap = redisTemplate.opsForHash().entries(key);
		for (Map.Entry<Object, Object> entry : keyvalueMap.entrySet()) {
			System.out.println("key-value = " + entry.getKey() + "-" + entry.getValue());
		}
		System.out.println("keySet = " + keySet);
		System.out.println("keyvalueMap = " + keyvalueMap);
		//ObjectMapper provides functionality for reading and writing JSON
		ObjectMapper om = 
				new ObjectMapper();
		
		RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
		byte[] bytes = connection.hGet(key.getBytes(), scoreField.getBytes());
		System.out.println("native connection get = " + bytes);
		try {
			String string = om.readValue(bytes, String.class);
			System.out.println("score = " + string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<byte[], byte[]> bytesMap = connection.hGetAll(key.getBytes());
		
		
		for (Map.Entry<byte[], byte[]> entry : bytesMap.entrySet()) {
			System.out.println("bytes key-value = " + entry.getKey() + "-" + entry.getValue());
			try {
				Object dbKey = om.readValue(entry.getKey(), Object.class);
				System.out.println("key = " + dbKey);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Object dbValue = om.readValue(entry.getValue(), Object.class);
				System.out.println("value = " + dbValue);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
		Object object = mapper.fromHash(bytesMap);
		System.out.println("object = " + object);
		
		connection.hIncrBy(key.getBytes(), scoreField.getBytes(), 199);
		connection.hIncrBy(key.getBytes(), scoreField.getBytes(), 199);
		
		bytes = connection.hGet(key.getBytes(), scoreField.getBytes());
		System.out.println("native connection get = " + bytes);
		try {
			String string = om.readValue(bytes, String.class);
			System.out.println("score = " + string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

