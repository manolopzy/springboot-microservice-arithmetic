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
@Document(collection = "arithmetic_attempts")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ArithmeticAttempt {

	private User user;
	private Arithmetic arithmetic;
	//this is the user's calculation result, could be either wrong or right
	private int resultAttempt;

	// Empty constructor for JSON (de)serialization
	ArithmeticAttempt() {
		user = null;
		arithmetic = null;
		resultAttempt = -1;
	}

	public ArithmeticAttempt(User user, Arithmetic arithmetic, int resultAttempt) {
		this.user = user;
		this.arithmetic = arithmetic;
		this.resultAttempt = resultAttempt;
	}
}
