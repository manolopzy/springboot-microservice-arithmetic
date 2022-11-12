package com.worldexplorer.arithmeticgamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldexplorer.arithmeticgamification.client.ArithmeticResultAttemptClient;
import com.worldexplorer.arithmeticgamification.client.dto.ArithmeticResultAttempt;
import com.worldexplorer.arithmeticgamification.entity.Badge;
import com.worldexplorer.arithmeticgamification.entity.BadgeCard;
import com.worldexplorer.arithmeticgamification.entity.GameStats;
import com.worldexplorer.arithmeticgamification.entity.LeaderBoardRow;
import com.worldexplorer.arithmeticgamification.entity.ScoreCard;
import com.worldexplorer.arithmeticgamification.repository.GamestatsRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * Ctrl+1 + select "Create new local variable"
 * @author tanku
 *
 */
@Service
@Slf4j
@Data
public class GameServiceImpl implements GameService{
	
	public static final int LUCKY_NUMBER = 42;
	@Autowired
	private GamestatsRepository gamestatsRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private ArithmeticResultAttemptClient arithmeticResultAttemptClient;
	/**
     * Deal with new attempt events from arithmetic microservice
     */
	
    public GameStats newArithmeticAttempt(final String userId,
                                       final String attemptId,
                                       final boolean correct) {
        //damos la puntuacion solo si esta correcto la respuesta
        if(correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            gameRepository.addScoreCard(scoreCard);
            Optional<GameStats> result = gamestatsRepository.findById(userId);
            
            GameStats gameStats = null;
            if(result.isEmpty()) {
            	gameStats = new GameStats(userId, scoreCard.getScore());
            }
            else {
            	gameStats = result.get();
            	//Anadir la puntuacion al usuario
            	gameStats.setScore(gameStats.getScore() + scoreCard.getScore());
            }
            List<Badge> newBadges = processForNewBadges(attemptId, gameStats);
            gameStats.addNewBadges(newBadges);
            gameRepository.addNewBadges(userId, newBadges);
            return gamestatsRepository.save(gameStats);
        }
        return GameStats.emptyStats(userId);
    }

	
	
	/**
     * Checks the total score and the different score cards obtained
     * to give new badges in case their conditions are met.
     */
    private List<Badge> processForNewBadges(final String attemptId, GameStats gamestats) {
        List<Badge> newBadges = new ArrayList<>();
        log.info("New score for user {} is {}", gamestats.getUserId(), gamestats);

        List<Badge> badges = gamestats.getBadges();
        
        // Badges depending on score
        checkAndGiveBadgeBasedOnScore(badges, newBadges,
                Badge.BRONZE_ARITHMETIC, gamestats.getScore(), 100, gamestats);
        checkAndGiveBadgeBasedOnScore(badges, newBadges,
                Badge.SILVER_ARITHMETIC, gamestats.getScore(), 500, gamestats);
        checkAndGiveBadgeBasedOnScore(badges, newBadges,
                Badge.GOLD_ARITHMETIC, gamestats.getScore(), 999, gamestats);

        // First won badge
        if(badges == null || (badges.size() == 1 && !badges.contains(Badge.FIRST_WON))) {
        	newBadges.add(Badge.FIRST_WON);
        }

        // Lucky number badge
        ArithmeticResultAttempt attempt = 
        		arithmeticResultAttemptClient.retrieveArithmeticResultAttemptbyId(attemptId);
        if(badges != null && !badges.contains(Badge.LUCKY_NUMBER) &&
                (LUCKY_NUMBER == attempt.getArithmeticFactorA() ||
                LUCKY_NUMBER == attempt.getArithmeticFactorB())) {
            newBadges.add(Badge.LUCKY_NUMBER);
        }

        return newBadges;
    }
    
    private void checkAndGiveBadgeBasedOnScore(
            final List<Badge> badgeCards, List<Badge> newBadges, final Badge badge,
            final int score, final int scoreThreshold, final GameStats totalScoreCard) {
        if(score >= scoreThreshold && !badgeCards.contains(badge)) {
        	newBadges.add(badge);
        }
    }

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GameStats retrieveStatsForUser(String userId) {
		Optional<GameStats> result = gamestatsRepository.findById(userId);
		return result.isEmpty() ? null : result.get();
	}
}
