package com.sportradar.scoreboard.match.exception;

import com.sportradar.scoreboard.model.MatchId;

public class MatchAlreadyStartedException extends IllegalStateException {
    public MatchAlreadyStartedException(MatchId matchId) {
        this(matchId, null);
    }

    public MatchAlreadyStartedException(MatchId matchId, Throwable cause) {
        super("Match with id: " + matchId + " already started.", cause);
    }
}
