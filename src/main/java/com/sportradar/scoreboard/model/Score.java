package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.NegativeScoreException;

import static java.util.Objects.requireNonNull;

public record Score(Integer homeScore, Integer awayScore) {

    public Score() {
        this(0, 0);
    }

    public Score {
        requireNonNull(homeScore, "homeScore cannot be null");
        requireNonNull(awayScore, "awayScore cannot be null");
        if (homeScore < 0 || awayScore < 0) {
            throw new NegativeScoreException();
        }
    }

    public Integer getTotal() {
        return homeScore + awayScore;
    }
}
