package com.sportradar.scoreboard.model.exception;

public class BlankTeamNameException extends IllegalArgumentException {
    public BlankTeamNameException() {
        super("Team name cannot be blank.");
    }
}
