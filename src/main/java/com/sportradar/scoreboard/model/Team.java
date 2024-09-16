package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.BlankTeamNameException;

import static java.util.Objects.requireNonNull;

public record Team(String name) {

    public Team {
        requireNonNull(name, "name cannot be null");
        if (name.isBlank()) {
            throw new BlankTeamNameException();
        }
    }
}
