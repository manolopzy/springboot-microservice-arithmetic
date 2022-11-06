package com.worldexplorer.arithmetic.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldexplorer.arithmetic.entity.Arithmetic;

public interface ArithmeticRepository extends MongoRepository<Arithmetic, String>{

}
