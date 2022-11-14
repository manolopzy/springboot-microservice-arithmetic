package com.worldexplorer.arithmeticgamification.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.worldexplorer.arithmeticgamification.entity.Badge;
import com.worldexplorer.arithmeticgamification.entity.BadgeCard;
import com.worldexplorer.arithmeticgamification.entity.LeaderBoardRow;
import com.worldexplorer.arithmeticgamification.entity.ScoreCard;

import lombok.extern.slf4j.Slf4j;
//==============================================not finished yet
@Slf4j
@Component
public class GameRepository {

	public static final String BADGE_CARD_ZSET_REDIS_KEY = "player_badge_cards";
	public static final String SCORE_CARD_ZSET_REDIS_KEY = "player_score_cards";
	public static final String LEADER_BOARD_ZSET_REDIS_KEY = "player_leader_boards";
	@Autowired
	private RedisTemplate<String, Object> jdkRedisTemplate;

	public BadgeCard addBadgeCard(Badge badge, String userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		boolean success = jdkRedisTemplate.opsForZSet().add(BADGE_CARD_ZSET_REDIS_KEY + userId, badgeCard,
				badgeCard.getBadgeTimestamp());
		if (success) {
			log.info("User with id {} won a new badge: {}", userId, badge);
		} else {
			log.info("User with id {} can not create a new badge to database: {}", userId, badge);
		}

		return success ? badgeCard : null;
	}

	public void addScoreCard(ScoreCard scoreCard) {
		
		// each user has a ordered set of {@link ScoreCard}
		boolean success = jdkRedisTemplate.opsForZSet().add(SCORE_CARD_ZSET_REDIS_KEY + scoreCard.getUserId(), scoreCard,
				scoreCard.getScoreTimestamp());
		if (success) {
			log.info("User with id {} create a new score card: {}", scoreCard.getUserId(), scoreCard.toString());
		} else {
			log.info("User with id {} can not create a new badge to database: {}", scoreCard.getUserId(),
					scoreCard.toString());
		}
	}

	public Set<Object> findAllBadgeCards(String userId) {
		return jdkRedisTemplate.opsForZSet().range(BADGE_CARD_ZSET_REDIS_KEY + userId, 0, -1);
	}
	
	public Set<Object> findAllScoreCards(String userId) {
		return jdkRedisTemplate.opsForZSet().range(SCORE_CARD_ZSET_REDIS_KEY + userId, 0, -1);
	}

	public TypedTuple<Object> popLastBadgeCard(String userId) {
		return jdkRedisTemplate.opsForZSet().popMax(BADGE_CARD_ZSET_REDIS_KEY + userId);
	}

	public TypedTuple<Object> popLastScoreCard(String userId) {
		return jdkRedisTemplate.opsForZSet().popMax(SCORE_CARD_ZSET_REDIS_KEY + userId);
	}

	public void addNewBadges(String userId, List<Badge> newBadges) {
		if(newBadges == null || newBadges.size() == 0)
			return;
		Set<BadgeCard> badgeCards = new HashSet<>(newBadges.size());
		for (Badge badge : newBadges) {
			BadgeCard badgeCard = new BadgeCard(userId, badge);
			badgeCards.add(badgeCard);
			jdkRedisTemplate.opsForZSet().add(BADGE_CARD_ZSET_REDIS_KEY + userId, badgeCard, badgeCard.getBadgeTimestamp());
		}
		//==============================================
	}

	public void addLeaderBoard(String userId, int newScore) {
		jdkRedisTemplate.opsForZSet().add(LEADER_BOARD_ZSET_REDIS_KEY, userId, newScore);
	}

	public List<LeaderBoardRow> getLeaderBoards(int numbers) {
		
		Set<TypedTuple<Object>> leaderBoards = jdkRedisTemplate.opsForZSet().reverseRangeWithScores(LEADER_BOARD_ZSET_REDIS_KEY, 0, numbers - 1);
		//jdkRedisTemplate.opsForZSet().rangeWithScores(LEADER_BOARD_ZSET_REDIS_KEY, 0, numbers - 1);
		log.info("leader boards infomation: " + leaderBoards);
		if(leaderBoards == null || leaderBoards.size() == 0) {
			return null;
		}
		List<LeaderBoardRow> leaderBoardRows = new ArrayList<>(leaderBoards.size());
		for (TypedTuple<Object> typedTuple : leaderBoards) {
			LeaderBoardRow row = new LeaderBoardRow();
			row.setUserId((String)typedTuple.getValue());
			row.setTotalScore(typedTuple.getScore().longValue());
			log.info("leader boards infomation row: " + row.toString());
			leaderBoardRows.add(row);
		}
		return leaderBoardRows;
	}

}
