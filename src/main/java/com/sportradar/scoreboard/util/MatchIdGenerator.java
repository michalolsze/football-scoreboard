package com.sportradar.scoreboard.util;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public interface MatchIdGenerator {
    default UUID generate() {
        return randomUUID();
    }
}
