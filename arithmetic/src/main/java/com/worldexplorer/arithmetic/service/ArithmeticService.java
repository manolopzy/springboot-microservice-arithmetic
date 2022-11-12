package com.worldexplorer.arithmetic.service;

import java.util.List;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;

public interface ArithmeticService {

	/**
	 * Create a Multiplication object with two 
	 * randomly generated factors between 10 and 99
	 * @return
	 */
	Arithmetic createRandomExpression(String operator);
	
	/**
	 * this is the service deals with user's attempt requests
	* @return true if the attempt matches the result of the
	* multiplication, false otherwise.
	*/
	boolean checkAttempt(final ArithmeticAttempt attempt);

	ArithmeticAttempt getArithmeticAttemptById(String id);

	List<ArithmeticAttempt> getStatsForUser(String alias);
}
