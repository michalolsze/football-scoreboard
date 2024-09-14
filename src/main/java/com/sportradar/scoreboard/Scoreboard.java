package com.sportradar.scoreboard;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import com.sportradar.scoreboard.repository.MatchRepository;
import com.sportradar.scoreboard.util.MatchIdGenerator;

import java.time.Clock;
import java.util.UUID;

public class Scoreboard {

    private final Clock clock;
    private final MatchIdGenerator matchIdGenerator;
    private final MatchRepository repository;

    public Scoreboard(Clock clock, MatchIdGenerator matchIdGenerator, MatchRepository repository) {
        this.clock = clock;
        this.matchIdGenerator = matchIdGenerator;
        this.repository = repository;
    }

    public Match startMatch(Team home, Team away) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void update(UUID matchId, Score score) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void finishMatch(UUID matchId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public Iterable<Match> getSummary() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
