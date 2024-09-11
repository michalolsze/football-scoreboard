package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.exception.NonUniqueTeamsException;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Match(UUID id, Team homeTeam, Team awayTeam, Score score) {

    public Match {
        requireNonNull(id, "id cannot be null");
        requireNonNull(homeTeam, "homeTeam cannot be null");
        requireNonNull(awayTeam, "awayTeam cannot be null");
        requireNonNull(score, "score cannot be null");
        if (homeTeam.id() == awayTeam.id()) {
            throw new NonUniqueTeamsException();
        }
    }
}
