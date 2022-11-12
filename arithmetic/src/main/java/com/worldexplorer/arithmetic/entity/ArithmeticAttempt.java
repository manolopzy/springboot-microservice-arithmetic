package com.worldexplorer.arithmetic.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * var data = { user: { alias: userAlias }, multiplication: { factorA: a, factorB: b }, resultAttempt: attempt };
 * @author tanku
 *
 */
@Document(collection = "arithmeticAttempts")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ArithmeticAttempt {
	private String id;
	private User user;
	private Arithmetic arithmetic;
	//this is the user's calculation result, could be either wrong or right
	private int resultAttempt;
	private boolean correct;
	// Empty constructor for JSON (de)serialization
	ArithmeticAttempt() {
		user = null;
		arithmetic = null;
		resultAttempt = -1;
		correct = false;
	}

	public ArithmeticAttempt(User user, Arithmetic arithmetic, int resultAttempt, boolean correct) {
		this.user = user;
		this.arithmetic = arithmetic;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}
}
