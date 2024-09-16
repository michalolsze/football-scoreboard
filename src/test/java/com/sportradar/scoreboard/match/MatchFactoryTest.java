package com.sportradar.scoreboard.match;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.Clock.fixed;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchFactoryTest {

    private final MatchFactory factory = new MatchFactory(fixed(FIXED_START_TIME, UTC));

    @Test
    public void shouldCreateNewMatch() {
        // when
        Match actual = factory.create(HOME_TEAM, AWAY_TEAM);

        // then
        Match expected = new Match(HOME_TEAM, AWAY_TEAM, new Score(0,0), FIXED_START_TIME);
        assertEquals(expected, actual);
    }

    private static final Instant FIXED_START_TIME = ofEpochMilli(1);

    private static final Team HOME_TEAM = new Team("Home");
    private static final Team AWAY_TEAM = new Team("Away");

}
