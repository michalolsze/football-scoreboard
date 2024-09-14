package com.sportradar.scoreboard.testutils;

import com.sportradar.scoreboard.util.MatchIdGenerator;

import java.util.UUID;

public record FixedMatchIdGenerator(UUID fixed) implements MatchIdGenerator {
    @Override
    public UUID generate() {
        return fixed;
    }
}
