package com.worldexplorer.arithmeticgamification.entity;

import org.springframework.data.annotation.Transient;

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
public class RScoreCard {

    // The default score assigned to this card, if not specified.
    @Transient
	public static final int DEFAULT_SCORE = 10;
    
    private final Long userId;

    private final Long attemptId;

    private final long scoreTimestamp;

    private final int score;

    // Empty constructor for JSON / JPA
    public RScoreCard() {
        this(null, null, 0, 0);
    }

    public RScoreCard(final Long userId, final Long attemptId) {
        this(userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }
}
