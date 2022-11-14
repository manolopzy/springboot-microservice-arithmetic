package com.worldexplorer.arithmetic.service;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;

public interface ArithmeticService {

	/**
	 * Create an arithmetic expression object with 
	 * specified operator 
	 * @return
	 */
	Arithmetic createRandomExpression(String operator);
	
	/**
	 * this is the service deals with user's attempt requests
	* @return true if the attempt matches the result of the
	* arithmetic expression, false otherwise.
	*/
	boolean checkAttempt(final ArithmeticAttempt attempt);
	/**
	 * get the arithmetic attempt data with its id
	 * @param id
	 * @return
	 */
	ArithmeticAttempt getArithmeticAttemptById(String id);
	/**
	 * Return a user's attempts by name
	 * @param alias
	 * @return
	 */
	Iterable<ArithmeticAttempt> getArithmeticAttempts(String alias);
}
