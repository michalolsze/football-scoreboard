package com.sportradar.scoreboard;

import com.sportradar.scoreboard.repository.InMemoryCrudRepository;

import static java.time.Clock.systemUTC;

public class ScoreboardFactory {
    public static Scoreboard create() {
        return new Scoreboard(systemUTC(), new InMemoryCrudRepository<>());
    }
}
