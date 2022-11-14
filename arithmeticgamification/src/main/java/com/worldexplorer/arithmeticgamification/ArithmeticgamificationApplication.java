package com.worldexplorer.arithmeticgamification;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
//Shift + Windows logo key + S
@EnableEurekaClient
@SpringBootApplication
public class ArithmeticgamificationApplication {

	@Autowired
	RedisKeyValueTemplate redisKVTemplate;
	public static void main(String[] args) {
		SpringApplication.run(ArithmeticgamificationApplication.class, args);
	}
	
	@PostConstruct
	public void check() {
		System.out.println("RedisKeyValueTemplate = " + redisKVTemplate);
	}

}
