package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.BlankTeamNameException;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Team(UUID id, String name) {

    public Team {
        requireNonNull(id, "id cannot be null");
        requireNonNull(name, "name cannot be null");
        if (name.isBlank()) {
            throw new BlankTeamNameException();
        }
    }
}
