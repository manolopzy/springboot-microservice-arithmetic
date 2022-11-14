package com.worldexplorer.arithmeticgamification.entity;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Esta clase representa la puntuacion asociada a cada prueba de un 
 * usuario, cada vez el usuario prueba el juego, se genera y registra un tarjeta 
 * de puntuacion ligado el tiempo que lo consigue.
 */
@Data
@ToString
@EqualsAndHashCode
@RedisHash("arithmetic_gamestats")
public class GameStats {
	/**
	 * Used for redis serialization
	 */
	public GameStats() {}
    public GameStats(String userId, int score) {
		this.userId = userId;
		this.score = score;
	}
    public GameStats(String userId, int score, List<Badge> badges) {
		this.userId = userId;
		this.score = score;
		this.badges = badges;
	}
	@Id
    private String userId;

    private int score;

    private List<Badge> badges;
    
    /**
     * Factory method to build an empty instance (zero points and no badges)
     * @param userId the user's id
     * @return a {@link GameStats} object with zero score and no badges
     */
    public static GameStats emptyStats(final String userId) {
        return new GameStats(userId, 0, Collections.emptyList());
    }
	public void addNewBadges(List<Badge> newBadges) {
		if(newBadges == null || newBadges.size() == 0) {
			return;
		}
		if(badges == null) {
			badges = newBadges;
		}
		else {
			badges.addAll(newBadges);
		}
		
	}
}
