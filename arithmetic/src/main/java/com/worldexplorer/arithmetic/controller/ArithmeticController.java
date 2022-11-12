package com.worldexplorer.arithmetic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.service.ArithmeticService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/arithmetic")
public class ArithmeticController {
	
	private final ArithmeticService arithmeticService;

	@Autowired
	public ArithmeticController(final ArithmeticService arithmeticService) {
		this.arithmeticService = arithmeticService;
	}

	//https://www.baeldung.com/spring-request-param
	//http://localhost:8080/arithmetic/random?operator=1
	@GetMapping("/random")
	Arithmetic getRandomExpression(@RequestParam(name = "operator") String operator) {
		log.info("arithmetic operator = " + operator);
		return arithmeticService.createRandomExpression(operator);
	}

	@PostMapping("/attempt")
	ResponseEntity<ArithmeticAttempt> postResult(@RequestBody ArithmeticAttempt attempt) {
		boolean isCorrect = arithmeticService.checkAttempt(attempt);
        ArithmeticAttempt attemptCopy = new ArithmeticAttempt(
        		attempt.getUser(),
        		attempt.getArithmetic(),
        		attempt.getResultAttempt(),
        		isCorrect
        );
        return ResponseEntity.ok(attemptCopy);
	}

    @GetMapping
    ResponseEntity<List<ArithmeticAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
        		arithmeticService.getStatsForUser(alias)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ArithmeticAttempt> getAttemptResultById(final @PathVariable("id") String id) {
    	log.info("ask information with attempt result id = " + id);
        return ResponseEntity.ok(arithmeticService.getArithmeticAttemptById(id));
    }
	@Getter
	private static final class ResultResponse {
		public ResultResponse(boolean checkAttempt) {
			this.correct = checkAttempt;
		}
		private final boolean correct;
		
	}
}
