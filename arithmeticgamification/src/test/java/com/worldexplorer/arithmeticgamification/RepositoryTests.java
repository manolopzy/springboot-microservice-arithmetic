package com.worldexplorer.arithmeticgamification;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
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
 * 
 * @author tanku
 *
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {

	@Resource(name = "keyvalueJsonRedisTemplate")
	HashOperations<String, byte[], byte[]> hashOperations;

	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private RedisKeyValueTemplate redisKeyValueTemplate;
	@Resource(name = "keyvalueJsonRedisTemplate")
	private RedisTemplate<String, Object> keyvalueJsonRedisTemplate;
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, Object> stringRedisTemplate;
	@Resource(name = "jdkRedisTemplate")
	private RedisTemplate<String, Object> jdkRedisTemplate;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String, Object> jsonRedisTemplate;
	@Resource(name = "xmlRedisTemplate")
	private RedisTemplate<String, Object> xmlRedisTemplate;
	@Autowired
	private GamestatsRepository gamestatsRepository;

	Random random = new Random();
	List<String> userIds = new ArrayList<>();
	private int numberOfUsers = 10;
	String userId = 0 + "";

	@BeforeEach
	public void setup() {
		for (int i = 0; i < numberOfUsers; i++) {
			userIds.add(random.nextLong() + "");
		}
		userId = userIds.get(random.nextInt(0, userIds.size()));
	}

	@Test
	@Order(1)
	public void testAddBadgeCard() {

		gameRepository.addBadgeCard(Badge.BRONZE_ARITHMETIC, userId);
		Set<Object> badgeSet = gameRepository.findAllBadgeCards(userId);
		assertThat(badgeSet).isNotNull().hasSize(1);
		TypedTuple<Object> result = gameRepository.popLastBadgeCard(userId);
		assertThat(result.getValue()).isNotNull();
		BadgeCard badgeCard = (BadgeCard) (result.getValue());
		assertThat(badgeCard.getUserId()).isEqualTo(userId);
		assertThat(badgeCard.getBadge()).isEqualTo(Badge.BRONZE_ARITHMETIC);
	}

	@Test
	@Order(2)
	public void testAddScoreCard() {

		ScoreCard scoreCard = new ScoreCard(userId, random.nextLong() + "");
		gameRepository.addScoreCard(scoreCard);
		Set<Object> badgeSet = gameRepository.findAllScoreCards(userId);
		assertThat(badgeSet).isNotNull().hasSize(1);
		TypedTuple<Object> result = gameRepository.popLastScoreCard(userId);
		assertThat(result.getValue()).isNotNull();
		ScoreCard dbScoreCard = (ScoreCard) (result.getValue());
		assertThat(dbScoreCard.getUserId()).isEqualTo(userId);

	}

	@Test
	@Order(3)
	public void testBasicsGameStatsRepository() {
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

	@Test
	@Order(4)
	public void testRedisHashtablePartialUpdate() {
		GameStats gameStats = new GameStats(userId, 43);
		GameStats dbGameStats = gamestatsRepository.save(gameStats);
		assertThat(dbGameStats).isNotNull();

		PartialUpdate<GameStats> update = new PartialUpdate<GameStats>((String) userId, GameStats.class).set("score",
				100);
		redisKeyValueTemplate.update(update);

		Optional<GameStats> result = gamestatsRepository.findById(gameStats.getUserId());
		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get().getUserId()).isEqualTo(userId);
		assertThat(result.get().getScore()).isEqualTo(100);

		gamestatsRepository.deleteById(userId);
		result = gamestatsRepository.findById(userId);
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	@Order(5)
	public void testRedisHashtable() {

	}

	String key = "arithmetic_gamestats:";

	@Test
	@Order(6)
	public void testRedisHash() {
		GameStats gameStats = new GameStats(userId, 1000);
		GameStats dbGameStats = gamestatsRepository.save(gameStats);
		assertThat(dbGameStats).isNotNull();
		key += userId;
		System.out.println("key 2 = " + key);
		String scoreField = "score";
		Object scoreObject = keyvalueJsonRedisTemplate.opsForHash().get(key, scoreField);
		System.out.println("score = " + scoreObject);

		Set<Object> keySet = keyvalueJsonRedisTemplate.opsForHash().keys(key);
		for (Object object : keySet) {
			System.out.println("key = " + object);
		}

		Map<Object, Object> keyvalueMap = keyvalueJsonRedisTemplate.opsForHash().entries(key);
		for (Map.Entry<Object, Object> entry : keyvalueMap.entrySet()) {
			System.out.println("key-value = " + entry.getKey() + "-" + entry.getValue());
		}
		System.out.println("keySet = " + keySet);
		System.out.println("keyvalueMap = " + keyvalueMap);
		RedisConnection connection = xmlRedisTemplate.getConnectionFactory().getConnection();
		byte[] bytes = connection.hGet(key.getBytes(), scoreField.getBytes());
		try {
			System.out.println("native connection get = " + bytes);
			System.out.println("hashOperations get = " + hashOperations.entries(key));
			System.out.println("stringRedisTemplate get = " + stringRedisTemplate.opsForHash().get(key, scoreField));
			System.out.println("stringRedisTemplate get = " + stringRedisTemplate.opsForHash().entries(key));
			System.out.println("jdkRedisTemplate get = " + jdkRedisTemplate.opsForHash().entries(key));
			System.out.println("jsonRedisTemplate get = " + jsonRedisTemplate.opsForHash().entries(key));
			System.out.println("xmlRedisTemplate get = " + xmlRedisTemplate.opsForHash().entries(key));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		connection.hIncrBy(key.getBytes(), scoreField.getBytes(), 199);
		connection.hIncrBy(key.getBytes(), scoreField.getBytes(), -29);

		bytes = connection.hGet(key.getBytes(), scoreField.getBytes());
		System.out.println("native connection get = " + bytes);
		ObjectMapper om = new ObjectMapper();
		try {
			String string = om.readValue(bytes, String.class);
			System.out.println("score = " + string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		ObjectMapper om = 
//				new ObjectMapper();
//		try {
//			String string = om.readValue(bytes, String.class);
//			System.out.println("score string = " + string);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

//	@Test @Order(7)
//	public void testRetriveRedisHash() {
//		Object scoreObject = jsonRedisTemplate.opsForHash().get(key, "score");
//		System.out.println("score = " + scoreObject);
//		
//		Set<Object> keySet = jsonRedisTemplate.opsForHash().keys(key);
//		for (Object object : keySet) {
//			System.out.println("key = " + object);
//		}
//		
//		Map<Object, Object> keyvalueMap = jsonRedisTemplate.opsForHash().entries(key);
//		for (Map.Entry<Object, Object> entry : keyvalueMap.entrySet()) {
//			System.out.println("key-value = " + entry.getKey() + "-" + entry.getValue());
//		}
//		System.out.println("keySet = " + keySet);
//		System.out.println("keyvalueMap = " + keyvalueMap);
//	}
}
