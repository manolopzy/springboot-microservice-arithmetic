package com.worldexplorer.arithmetic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.service.ArithmeticService;

import lombok.Getter;

@RestController
@RequestMapping("/arithmetic")
public class ArithmeticController {
	
	Logger logger = LogManager.getLogger(ArithmeticController.class);
	
	private final ArithmeticService arithmeticService;

	@Autowired
	public ArithmeticController(final ArithmeticService arithmeticService) {
		this.arithmeticService = arithmeticService;
	}

	//https://www.baeldung.com/spring-request-param
	//http://localhost:8080/arithmetic/random?operator=1
	@GetMapping("/random")
	Arithmetic getRandomExpression(@RequestParam(name = "operator") String operator) {
		logger.info("arithmetic operator = " + operator);
		return arithmeticService.createRandomExpression(operator);
	}

	@PostMapping("/attempt")
	ResponseEntity<ResultResponse> postResult(@RequestBody ArithmeticAttempt attempt) {
		return ResponseEntity.ok(new ResultResponse(arithmeticService.checkAttempt(attempt)));
	}

	@Getter
	private static final class ResultResponse {
		public ResultResponse(boolean checkAttempt) {
			this.correct = checkAttempt;
		}
		private final boolean correct;
		
	}
}
