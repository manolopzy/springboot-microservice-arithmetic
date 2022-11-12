package com.worldexplorer.arithmeticgamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldexplorer.arithmeticgamification.client.ArithmeticResultAttemptDeserializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * representacion de una prueba de revolver una expresion aritmetica de usuarios
 * recibida del micro servicio de "arithmetic"
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = ArithmeticResultAttemptDeserializer.class)
public final class ArithmeticResultAttempt {

    private final String userAlias;

    private final int arithmeticFactorA;
    private final int arithmeticFactorB;
    private final int resultAttempt;

    private final boolean correct;

    // Empty constructor for JSON
    public ArithmeticResultAttempt() {
        userAlias = null;
        arithmeticFactorA = -1;
        arithmeticFactorB = -1;
        resultAttempt = -1;
        correct = false;
    }

}
