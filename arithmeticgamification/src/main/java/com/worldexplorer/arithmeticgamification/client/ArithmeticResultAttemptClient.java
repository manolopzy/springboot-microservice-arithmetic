package com.worldexplorer.arithmeticgamification.client;

import com.worldexplorer.arithmeticgamification.client.dto.ArithmeticResultAttempt;

/**
 * Esta interfaz define metodos para comunicar con otros micro servicios
 * 
 */
public interface ArithmeticResultAttemptClient {

    ArithmeticResultAttempt retrieveArithmeticResultAttemptbyId(final String arithmeticId);

}
