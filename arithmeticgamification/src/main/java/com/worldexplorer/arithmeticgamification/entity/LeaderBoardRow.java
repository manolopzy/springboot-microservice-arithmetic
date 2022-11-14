package com.worldexplorer.arithmeticgamification.entity;

import lombok.Data;

/**
 * Represents a line in our Leaderboard: it links a user to a total score.
 */
@Data
public class LeaderBoardRow {

    private String userId;
    private Long totalScore;

    // Empty constructor for JSON / JPA
    public LeaderBoardRow() {
    }
}
