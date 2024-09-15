package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.NonUniqueTeamsException;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Match(UUID id, Team homeTeam, Team awayTeam, Score score, Instant startTime) {

    public Match {
        requireNonNull(id, "id cannot be null");
        requireNonNull(homeTeam, "homeTeam cannot be null");
        requireNonNull(awayTeam, "awayTeam cannot be null");
        requireNonNull(score, "score cannot be null");
        requireNonNull(startTime, "startTime cannot be null");
        if (homeTeam.id() == awayTeam.id()) {
            throw new NonUniqueTeamsException();
        }
    }

    public Match updateScore(Score newScore) {
        return new Match(id, homeTeam, awayTeam, newScore, startTime);
    }

    public Integer getTotalScore() {
        return score.getTotal();
    }
}
