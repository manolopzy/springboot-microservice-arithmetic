package com.worldexplorer.arithmetic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;
import com.worldexplorer.arithmetic.entity.QArithmeticAttempt;
import com.worldexplorer.arithmetic.repository.ArithmeticAttemptRepository;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class ArithmeticAttemptRepositoryTest {

	@Autowired
	private ArithmeticAttemptRepository attemptsRepository;
	
	@Test
	public void readFirstPageTest() {
		//load the first page, no less than fifteen records
		Page<ArithmeticAttempt> attempts = 
				attemptsRepository.findAll(PageRequest.of(0, 15));
		assertThat(attempts.isFirst()).isTrue();
	}
	
	@Test
	public void findByNameTest() {
		QArithmeticAttempt qQuery = new QArithmeticAttempt("arithmeticAttempt");
		Iterable<ArithmeticAttempt> result = attemptsRepository.findAll(qQuery.user.alias.eq("manolopzy"));
		result.forEach(attempt -> {assertThat(attempt.getUser().getAlias()).isEqualTo("manolopzy");});
		
	}
}
