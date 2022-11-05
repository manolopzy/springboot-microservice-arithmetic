package com.worldexplorer.arithmetic.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.worldexplorer.arithmetic.service.RandomGeneratorService;
@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {

	final static int MINIMUM_FACTOR = 5;
	final static int MAXIMUM_FACTOR = 99;

	@Override
	public int randomMultiplicationFactor() {
		return ThreadLocalRandom.current().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR;
	}

	@Override
	public int randomDivisionFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int randomAdditionFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int randomSubtractionFactor() {
		// TODO Auto-generated method stub
		return 0;
	}
}
