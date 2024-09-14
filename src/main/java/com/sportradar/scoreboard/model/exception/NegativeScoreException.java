package com.sportradar.scoreboard.model.exception;

public class NegativeScoreException extends IllegalArgumentException {
    public NegativeScoreException() {
        super("Score cannot be negative.");
    }
}
