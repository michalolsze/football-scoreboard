package com.sportradar.scoreboard.repository;

public interface CrudRepository<ID, T> {
    void delete(ID id);
    void save(T t);
    T findById(ID id);
    Iterable<T> findAll();
}
