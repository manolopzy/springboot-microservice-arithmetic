package com.worldexplorer.arithmetic;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.service.ArithmeticService;
import com.worldexplorer.arithmetic.service.RandomGeneratorService;
/**
 * This class must be create inside the test package, 
 * otherwise, the above import commands will not be 
 * resolved.
 * 
 * Control+Shift+O to auto import the needed classes
 * @author tanku
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceTest {
	/**
	 * The @MockBean annotation tells Spring to inject a 
	 * mock of the RandomGeneratorService bean, instead of 
	 * searching for a suitable implementation of the 
	 * interface (which doesn’t exist yet).
	 * BDD Behavior driven development
	 * TDD Test driven development
	 */
	@MockBean
	private RandomGeneratorService randomGeneratorService;
	@Autowired
	private ArithmeticService arithmeticService;
	static String[] operators = {"+", "-", "×", "÷"};
	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service 
		//will return first 50, then 30)
		given(randomGeneratorService.randomMultiplicationFactor()).
		willReturn(50, 30);
		String operator = operators[new Random().nextInt(0, operators.length)];
		operator = "×";
		// when
		Arithmetic multiplication = arithmeticService.createRandomExpression(operator);
		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		//assertThat(multiplication.getResult()).isEqualTo(1500);
	}
}
