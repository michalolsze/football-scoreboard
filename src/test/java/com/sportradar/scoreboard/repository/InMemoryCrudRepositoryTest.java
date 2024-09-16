package com.sportradar.scoreboard.repository;

import com.sportradar.scoreboard.repository.exception.EntityExistsException;
import com.sportradar.scoreboard.repository.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCrudRepositoryTest {

    private static final Object ENTITY = new Object();
    private static final Object ID = new Object();

    private final ConcurrentHashMap<Object, Object> internal = new ConcurrentHashMap<>();

    private final CrudRepository<Object, Object> repository = new InMemoryCrudRepository<>(internal);

    @Test
    public void shouldInsertEntity() {
        // when
        assertDoesNotThrow(() -> repository.insert(ID, ENTITY));

        // then
        assertEquals(Map.of(ID, ENTITY), internal);
    }

    @Test
    public void shouldUpdateEntity() {
        // given
        internal.put(ID, ENTITY);
        Object newEntity = new Object();

        // when
        assertDoesNotThrow(() -> repository.update(ID, newEntity));

        // then
        assertEquals(Map.of(ID, newEntity), internal);
    }

    @Test
    public void shouldDeleteEntity() {
        // given
        internal.put(ID, ENTITY);

        // when
        assertDoesNotThrow(() -> repository.delete(ID));

        // then
        assertEquals(emptyMap(), internal);
    }

    @Test
    public void shouldFindById() {
        // given
        internal.put(ID, ENTITY);

        // when
        Object actual = assertDoesNotThrow(() -> repository.findById(ID));

        // then
        assertEquals(ENTITY, actual);
    }

    @Test
    public void shouldFindAll() {
        // given
        Object anotherId = new Object();
        Object anotherEntity = new Object();
        internal.put(ID, ENTITY);
        internal.put(anotherId, anotherEntity);

        // when
        Iterable<Object> actual = assertDoesNotThrow(repository::findAll);

        // then
        assertEquals(Set.of(anotherEntity, ENTITY), iterableAsSet(actual));
    }

    @Test
    public void shouldFailWhenDeletingNonExistingEntity() {
        // when
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> repository.delete(ID));

        // then
        EntityNotFoundException expected = new EntityNotFoundException(ID);
        assertEquals(expected.getMessage(), thrown.getMessage());
    }

    @Test
    public void shouldFailOnInsertWhenEntityAlreadyExists() {
        // given
        internal.put(ID, ENTITY);

        // when
        EntityExistsException thrown = assertThrows(EntityExistsException.class, () -> repository.insert(ID, ENTITY));

        // then
        EntityExistsException expected = new EntityExistsException(ID);
        assertEquals(expected.getMessage(), thrown.getMessage());
    }

    @Test
    public void shouldReturnNullWhenCouldNotFindById() {
        // when
        Object actual = repository.findById(ID);

        // then
        assertNull(actual);
    }

    @Test
    public void shouldFindAllForEmptyRepository() {
        // when
        Iterable<Object> actual = repository.findAll();

        // then
        assertEquals(emptyList(), iterableAsList(actual));
    }

    private <T> List<T> iterableAsList(Iterable<T> iterable) {
        return stream(iterable.spliterator(), false).toList();
    }

    private <T> Set<T> iterableAsSet(Iterable<T> iterable) {
        return stream(iterable.spliterator(), false).collect(toSet());
    }

}
