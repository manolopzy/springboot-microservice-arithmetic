package com.worldexplorer.arithmeticgamification.service;

import java.util.List;

import com.worldexplorer.arithmeticgamification.entity.GameStats;
import com.worldexplorer.arithmeticgamification.entity.LeaderBoardRow;

/**
 * This service includes the main logic for gamifying the system.
 */
public interface GameService {

    /**
     * Process a new attempt from a given user.
     *
     * @param userId    the user's unique id
     * @param attemptId the attempt id, can be used to retrieve extra data if needed
     * @param correct   indicates if the attempt was correct
     *
     * @return a {@link GameStats} object containing the new score and badge cards obtained
     */
    GameStats newArithmeticAttempt(String userId, String attemptId, boolean correct);

    /**
     * Gets the game statistics for a given user
     * @param userId the user
     * @return the total statistics for that user
     */
    GameStats retrieveStatsForUser(String userId);
    /**
    * Retrieves the current leader board with the top score
    users
    * @return the users with the highest score
    */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
