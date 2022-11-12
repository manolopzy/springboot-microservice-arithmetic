package com.worldexplorer.arithmeticgamification.repository;

import org.springframework.data.repository.CrudRepository;

import com.worldexplorer.arithmeticgamification.entity.GameStats;

public interface GamestatsRepository extends CrudRepository<GameStats, String>{
}
