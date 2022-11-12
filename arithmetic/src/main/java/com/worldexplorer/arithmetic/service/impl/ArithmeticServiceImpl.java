package com.worldexplorer.arithmetic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.entity.User;
import com.worldexplorer.arithmetic.event.ArithmeticSolvedEvent;
import com.worldexplorer.arithmetic.event.EventDispatcher;
import com.worldexplorer.arithmetic.repository.ArithmeticAttemptRepository;
import com.worldexplorer.arithmetic.repository.UserRepository;
import com.worldexplorer.arithmetic.service.ArithmeticService;
import com.worldexplorer.arithmetic.service.RandomGeneratorService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ArithmeticServiceImpl implements ArithmeticService {
	
	@Autowired
	private ArithmeticAttemptRepository arithmeticAttemptRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventDispatcher eventDispatcher;
	
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
//		ArithmeticAttempt dbAttempt = repository.save(attempt);
//		log.info("dbObj = " + dbAttempt);
		// Check if the user already exists for that alias
		Optional<User> dbUser = userRepository.findByAlias(attempt.getUser().getAlias());
		User user = null;
		if(dbUser.isEmpty()) {
			user = userRepository.save(attempt.getUser());
		}
		else {
			user = dbUser.get();
		}
		// Check if the attempt is correct
		boolean isCorrect = attempt.getArithmetic().isCorrect(attempt.getResultAttempt());
		
		ArithmeticAttempt checkedAttempt = new ArithmeticAttempt(user ,attempt.getArithmetic(), attempt.getResultAttempt(), isCorrect);
		
		checkedAttempt = arithmeticAttemptRepository.save(checkedAttempt);
		log.info("checkedAttempt = " + checkedAttempt.toString());
		eventDispatcher.send(new ArithmeticSolvedEvent(checkedAttempt.getId(), user.getId(), checkedAttempt.isCorrect()));
		
		return isCorrect;
	}

	@Override
	public ArithmeticAttempt getArithmeticAttemptById(String id) {
		Optional<ArithmeticAttempt> result = arithmeticAttemptRepository.findById(id);
		return result.isEmpty() ? null : result.get();
	}

	@Override
	public List<ArithmeticAttempt> getStatsForUser(String alias) {
		// TODO Auto-generated method stub
		return null;
	}
}
