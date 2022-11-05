package com.worldexplorer.arithmetic;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldexplorer.arithmetic.controller.ArithmeticController;
import com.worldexplorer.arithmetic.entity.Arithmetic;
import com.worldexplorer.arithmetic.service.ArithmeticService;

@RunWith(SpringRunner.class)
@WebMvcTest(ArithmeticController.class)
public class MultiplicationControllerTest {

	@MockBean
	private ArithmeticService arithmeticService;
	@Autowired
	private MockMvc mvc;
	// This object will be magically initialized by the initFields method below.
	private JacksonTester<Arithmetic> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getRandomMultiplicationTest() throws Exception {
		String operator = "×";
		// given
		given(arithmeticService.createRandomExpression(operator)).willReturn(new Arithmetic(70, 20, operator));
		// when
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/arithmetic/random").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(json.write(new Arithmetic(70, 20, operator)).getJson());
	}
}
