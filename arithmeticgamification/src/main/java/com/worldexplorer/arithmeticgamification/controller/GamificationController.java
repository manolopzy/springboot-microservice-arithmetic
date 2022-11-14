
package com.worldexplorer.arithmeticgamification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldexplorer.arithmeticgamification.entity.GameStats;
import com.worldexplorer.arithmeticgamification.entity.LeaderBoardRow;
import com.worldexplorer.arithmeticgamification.service.GameService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class implements a REST API for the Gamification User Statistics service.
 */
@Slf4j
@RestController
@RequestMapping("/gamification")
class GamificationController {

    private final GameService gameService;

    public GamificationController(final GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/stats")
    public GameStats getStatsForUser(@RequestParam("userId") final String userId) {
        return gameService.retrieveStatsForUser(userId);
    }
    
//    @GetMapping("/leaders")
//    public List<LeaderBoardRow> getLeaderBoard() {
//        return gameService.getCurrentLeaderBoard();
//    }
    
    @GetMapping("/leaders")
    public ResponseEntity<List<LeaderBoardRow>> getLeaderBoardRows(){
    	List<LeaderBoardRow> currentLeaderBoard = 
    			gameService.getCurrentLeaderBoard();
    	log.info("return {} number of leader board records", currentLeaderBoard.size());
    	return ResponseEntity.ok(currentLeaderBoard);
    }
}
