package com.sportradar.scoreboard.repository;

import com.sportradar.scoreboard.repository.exception.EntityExistsException;
import com.sportradar.scoreboard.repository.exception.EntityNotFoundException;

public interface CrudRepository<Id, Entity> {
    void delete(Id id) throws EntityNotFoundException;
    void insert(Id id, Entity entity) throws EntityExistsException;
    void update(Id id, Entity entity) throws EntityNotFoundException;
    Entity findById(Id id);
    Iterable<Entity> findAll();
}
