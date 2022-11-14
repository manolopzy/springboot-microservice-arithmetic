package com.worldexplorer.arithmetic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//this annotation already includes {@link @ComponentScan}, so, other configuration classes will be loaded automatically
@SpringBootApplication
@EnableEurekaClient
//@ImportResource("classpath:mongodbConfig.xml")
public class ArithmeticApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArithmeticApplication.class, args);
	}

}
