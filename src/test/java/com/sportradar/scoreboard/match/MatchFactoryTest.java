package com.sportradar.scoreboard.match;

import com.sportradar.scoreboard.model.Match;
import com.sportradar.scoreboard.model.Score;
import com.sportradar.scoreboard.model.Team;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static java.time.Clock.fixed;
import static java.time.Instant.ofEpochMilli;
import static java.time.ZoneOffset.UTC;
import static java.util.UUID.fromString;
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
    private static final UUID FIXED_MATCH_ID = fromString("38668d54-f151-4425-a11d-ef325c4e453c");

    private static final Team HOME_TEAM = new Team("Home");
    private static final Team AWAY_TEAM = new Team("Away");

}
