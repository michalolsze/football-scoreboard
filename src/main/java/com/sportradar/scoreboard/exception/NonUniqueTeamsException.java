package com.sportradar.scoreboard.exception;

public class NonUniqueTeamsException extends RuntimeException {
    public NonUniqueTeamsException() {
        super("Non unique teams in match.");
    }
}
