package com.sportradar.scoreboard.match.exception;

import com.sportradar.scoreboard.model.MatchId;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(MatchId matchId) {
        this(matchId, null);
    }
    public MatchNotFoundException(MatchId matchId, Throwable cause) {
        super("Match with id: " + matchId + " was not found.", cause);
    }
}
