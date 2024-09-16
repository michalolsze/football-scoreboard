package com.sportradar.scoreboard.repository.exception;

public abstract class PersistenceException extends Exception {
    public PersistenceException(String message) {
        super(message);
    }
}
