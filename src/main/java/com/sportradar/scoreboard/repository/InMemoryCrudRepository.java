package com.sportradar.scoreboard.repository;

import com.sportradar.scoreboard.repository.exception.EntityExistsException;
import com.sportradar.scoreboard.repository.exception.EntityNotFoundException;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.requireNonNull;
import static org.apache.logging.log4j.LogManager.getLogger;

public class InMemoryCrudRepository<ID, T> implements CrudRepository<ID, T> {

    private static final Logger LOG = getLogger();

    private final Map<ID, T> internal;

    public InMemoryCrudRepository() {
        this(new ConcurrentHashMap<>());
    }

    InMemoryCrudRepository(ConcurrentMap<ID, T> internal) {
        this.internal = internal;
    }

    @Override
    public void delete(ID id) throws EntityNotFoundException {
        requireNonNull(id, "id cannot be null.");
        LOG.debug(() -> "Deleting entity with id: " + id + ".");
        T deleted = internal.remove(id);
        if (deleted == null) {
            throw new EntityNotFoundException(id);
        }
    }

    @Override
    public void insert(ID id, T entity) throws EntityExistsException {
        requireNonNull(id, "id cannot be null.");
        requireNonNull(entity, "entity cannot be null.");
        LOG.debug(() -> "Inserting entity: " + entity + " with id: " + id + ".");
        T old = internal.putIfAbsent(id, entity);
        if (old != null) {
            throw new EntityExistsException(id);
        }
    }

    @Override
    public void update(ID id, T entity) throws EntityNotFoundException {
        requireNonNull(id, "id cannot be null.");
        requireNonNull(entity, "entity cannot be null.");
        LOG.debug(() -> "Updating entity: " + entity + " with id: " + id + ".");
        T old = internal.computeIfPresent(id, (oldId, oldEntity) -> entity);
        if (old == null) {
            throw new EntityNotFoundException(id);
        }
    }

    @Override
    public T findById(ID id) {
        requireNonNull(id, "id cannot be null.");
        LOG.trace(() -> "Finding entity with id: " + id + ".");
        return internal.get(id);
    }

    @Override
    public Iterable<T> findAll() {
        LOG.trace("Finding all entities.");
        return internal.values();
    }
}
