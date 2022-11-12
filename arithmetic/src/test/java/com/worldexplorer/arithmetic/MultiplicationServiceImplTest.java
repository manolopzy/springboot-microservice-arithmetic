package com.worldexplorer.arithmetic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.entity.User;
import com.worldexplorer.arithmetic.service.RandomGeneratorService;
import com.worldexplorer.arithmetic.service.impl.ArithmeticServiceImpl;

public class MultiplicationServiceImplTest {
	private ArithmeticServiceImpl arithmaticServiceImpl;
	@Mock
	private RandomGeneratorService randomGeneratorService;

	static String operator = "×";
	@Before
	public void setUp() {
		// With this call to initialize Mocks
		// and tell Mockito to process the annotations
		MockitoAnnotations.openMocks(this);
		arithmaticServiceImpl = new ArithmeticServiceImpl(randomGeneratorService);
	}

	@Test
	public void createRandomMultiplicationTest() {
		// given (our mocked Random Generator service will return first 50, then 30)
		given(randomGeneratorService.randomMultiplicationFactor()).willReturn(50, 30);

		// when
		Arithmetic multiplication = arithmaticServiceImpl.createRandomExpression(operator);
		// assert
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		//assertThat(multiplication.getResult()).isEqualTo(1500);
	}

	@Test
	public void checkCorrectAttemptTest() {
		// given
		Arithmetic multiplication = new Arithmetic(50, 60, operator);
		User user = new User();
		user.setAlias("john_doe");
		ArithmeticAttempt attempt = new ArithmeticAttempt(user, multiplication, 3000, false);
		// when
		boolean attemptResult = arithmaticServiceImpl.checkAttempt(attempt);
		// assert
		assertThat(attemptResult).isTrue();
	}

	@Test
	public void checkWrongAttemptTest() {
		// given
		Arithmetic multiplication = new Arithmetic(50, 60, operator);
		User user = new User();
		user.setAlias("john_doe");
		ArithmeticAttempt attempt = new ArithmeticAttempt(user, multiplication, 3010, false);
		// when
		boolean attemptResult = arithmaticServiceImpl.checkAttempt(attempt);
		// assert
		assertThat(attemptResult).isFalse();
	}
}
