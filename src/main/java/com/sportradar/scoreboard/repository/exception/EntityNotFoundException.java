package com.sportradar.scoreboard.repository.exception;

public class EntityNotFoundException extends PersistenceException {
    public EntityNotFoundException(Object id){
        super("Entity with id: " + id + " not found.");
    }
}
