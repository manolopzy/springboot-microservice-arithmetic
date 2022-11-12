package com.worldexplorer.arithmeticgamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldexplorer.arithmeticgamification.entity.LeaderBoardRow;
import com.worldexplorer.arithmeticgamification.service.GameService;

/**
 * This class implements a REST API for the Gamification LeaderBoard service.
 */
@RestController
@RequestMapping("/leaders")
class LeaderBoardController {

    private final GameService gameService;

    public LeaderBoardController(final GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        return gameService.getCurrentLeaderBoard();
    }
}
