package com.worldexplorer.arithmetic.controller;

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
/**
 * One can set up different levels of cross origin
 * @author tanku
 *
 */
@Slf4j
//@CrossOrigin
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
	//@CrossOrigin(origins = "http://localhost:9090")
	@GetMapping("/random")
	Arithmetic getRandomExpression(@RequestParam(name = "operator") String operator) {
		log.info("request a random arithmetic expression = " + operator);
		return arithmeticService.createRandomExpression(operator);
	}
	@PostMapping("/attempt")
	ResponseEntity<ArithmeticAttempt> postResult(@RequestBody ArithmeticAttempt attempt) {
		log.info("user attempt = " + attempt.toString());
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
    ResponseEntity<Iterable<ArithmeticAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
        		arithmeticService.getArithmeticAttempts(alias)
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
