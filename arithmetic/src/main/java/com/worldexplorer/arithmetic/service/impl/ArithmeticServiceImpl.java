package com.worldexplorer.arithmetic.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.repository.ArithmeticAttemptRepository;
import com.worldexplorer.arithmetic.service.ArithmeticService;
import com.worldexplorer.arithmetic.service.RandomGeneratorService;

@Service
public class ArithmeticServiceImpl implements ArithmeticService {
	Logger logger = LogManager.getLogger(ArithmeticServiceImpl.class);
	
	@Autowired
	private ArithmeticAttemptRepository repository;
	
	private RandomGeneratorService randomGeneratorService;

//	private static final Map<String, String> operators = new HashMap<>();
//	static {
//		operators.put("1", "+");
//		operators.put("2", "-");
//		operators.put("3", "×");
//		operators.put("4", "÷");
//	}
	@Autowired
	public ArithmeticServiceImpl(RandomGeneratorService randomGeneratorService) {
		this.randomGeneratorService = randomGeneratorService;
	}

	@Override
	public Arithmetic createRandomExpression(String operator) {
		int factorA = randomGeneratorService.randomMultiplicationFactor();
		int factorB = randomGeneratorService.randomMultiplicationFactor();
		return new Arithmetic(factorA, factorB, operator);
	}

	@Override
	public boolean checkAttempt(final ArithmeticAttempt attempt) {
		ArithmeticAttempt dbObj = repository.save(attempt);
		logger.info("dbObj = " + dbObj);
		return attempt.getResultAttempt() ==
				attempt.getArithmetic().getFactorA() * attempt.getArithmetic().getFactorB();
	}
}
