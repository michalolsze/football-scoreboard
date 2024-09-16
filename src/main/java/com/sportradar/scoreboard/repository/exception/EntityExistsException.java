package com.sportradar.scoreboard.repository.exception;

public class EntityExistsException extends PersistenceException {
    public EntityExistsException(Object id) {
        super("Entity with id: " + id + " already exists.");
    }
}
