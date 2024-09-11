package com.sportradar.scoreboard.exception;

public class NegativeScoreException extends IllegalArgumentException {
    public NegativeScoreException() {
        super("Score cannot be negative.");
    }
}
