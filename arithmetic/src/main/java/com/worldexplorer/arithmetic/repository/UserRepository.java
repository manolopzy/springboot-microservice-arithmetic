package com.worldexplorer.arithmetic.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldexplorer.arithmetic.entity.User;

public interface UserRepository extends MongoRepository<User, String>{
	Optional<User> findByAlias(final String alias);
}
