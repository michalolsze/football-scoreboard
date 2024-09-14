package com.sportradar.scoreboard.model.exception;

public class NonUniqueTeamsException extends RuntimeException {
    public NonUniqueTeamsException() {
        super("Non unique teams in match.");
    }
}
