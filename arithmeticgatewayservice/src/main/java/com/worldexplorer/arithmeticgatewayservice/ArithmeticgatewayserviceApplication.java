package com.worldexplorer.arithmeticgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class ArithmeticgatewayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArithmeticgatewayserviceApplication.class, args);
	}

}
