package com.worldexplorer.arithmetic.service;

public interface RandomGeneratorService {

	/**
	 * Generate a random integer between 
	 * 5 and 99
	 * @return
	 */
	int randomMultiplicationFactor();
	
	int randomDivisionFactor();
	
	int randomAdditionFactor();
	
	int randomSubtractionFactor();
}
