
package com.worldexplorer.arithmeticgamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldexplorer.arithmeticgamification.entity.GameStats;
import com.worldexplorer.arithmeticgamification.service.GameService;

/**
 * This class implements a REST API for the Gamification User Statistics service.
 */
@RestController
@RequestMapping("/stats")
class UserStatsController {

    private final GameService gameService;

    public UserStatsController(final GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameStats getStatsForUser(@RequestParam("userId") final String userId) {
        return gameService.retrieveStatsForUser(userId);
    }
}
