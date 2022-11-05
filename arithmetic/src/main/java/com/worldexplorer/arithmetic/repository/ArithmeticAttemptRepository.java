package com.worldexplorer.arithmetic.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldexplorer.arithmetic.entity.ArithmeticAttempt;

public interface ArithmeticAttemptRepository extends MongoRepository<ArithmeticAttempt, String>{
}
