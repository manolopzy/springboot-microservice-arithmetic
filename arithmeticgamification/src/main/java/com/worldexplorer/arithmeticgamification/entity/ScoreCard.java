package com.worldexplorer.arithmeticgamification.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Esta clase representa la puntuacion asociada a cada prueba de un 
 * usuario, cada vez el usuario prueba el juego, se genera y registra un tarjeta 
 * de puntuacion ligado el tiempo que lo consigue.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class ScoreCard implements Serializable{

	/**
	 * marker for redis serialization when use {@link RedisTemplate}
	 */
    private static final long serialVersionUID = -2787963296978378975L;

	// The default score assigned to this card, if not specified.
    @Transient
	public static final int DEFAULT_SCORE = 10;
    
    private final String userId;

    private final String attemptId;

    private final long scoreTimestamp;

    private final int score;

    // Empty constructor for JSON / JPA
    public ScoreCard() {
        this(null, null, 0, 0);
    }

    public ScoreCard(final String userId, final String attemptId) {
        this(userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }

}
