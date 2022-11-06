package com.worldexplorer.arithmetic.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
/**
 * Mark the event with {@link Serializable}, because it is a 
 * requirement of json message converter
 * @author tanku
 *
 */
@SuppressWarnings("serial")
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ArithmeticSolvedEvent implements Serializable{

	private final String arithmeticResultAttemptId;
	private final String userId;
	private final boolean correct;
}
