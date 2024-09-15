package com.sportradar.scoreboard.match;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;

import java.time.Clock;

public class MatchFactory {

    private final Clock clock;
    private final MatchIdGenerator idGenerator;

    public MatchFactory(Clock clock, MatchIdGenerator idGenerator) {
        this.clock = clock;
        this.idGenerator = idGenerator;
    }

    public Match create(Team home, Team away) {
        return new Match(idGenerator.generate(), home, away, new Score(), clock.instant());
    }
}
