package com.sportradar.scoreboard;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;

import java.util.List;
import java.util.UUID;

public class Scoreboard {

    public UUID startMatch(Team home, Team away) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void update(UUID matchId, Score score) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void finishMatch(UUID matchId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List<Match> getSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
