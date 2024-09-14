package com.sportradar.scoreboard.repository;

import com.sportradar.scoreboard.model.Match;

import java.util.UUID;

public interface MatchRepository extends CrudRepository<UUID, Match> {
}
