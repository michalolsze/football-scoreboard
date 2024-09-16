package com.sportradar.scoreboard.match;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;

import java.time.Clock;

public class MatchFactory {

    private final Clock clock;

    public MatchFactory(Clock clock) {
        this.clock = clock;
    }

    public Match create(Team home, Team away) {
        return new Match(home, away, new Score(), clock.instant());
    }
}
