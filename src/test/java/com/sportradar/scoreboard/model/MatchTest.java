package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.NonUniqueTeamsException;
import com.sportradar.scoreboard.model.testutils.InvalidInputTC;
import com.sportradar.scoreboard.model.testutils.ValidInputTC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

import static java.time.Instant.ofEpochMilli;
import static java.util.UUID.fromString;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MatchTest {

    private static final Team TEAM_A = new Team("A");
    private static final Team TEAM_B = new Team("B");
    private static final Score SCORE = new Score();
    private static final Instant START_TIME = ofEpochMilli(1);

    @TestFactory
    public Stream<DynamicTest> shouldFailWhenInvalidParameters() {
        return of(
                new InvalidInputTC<>(
                        "missing homeTeam",
                        () -> new Match( null, TEAM_B, SCORE, START_TIME),
                        new NullPointerException("homeTeam cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing awayTeam",
                        () -> new Match(TEAM_A, null, SCORE, START_TIME),
                        new NullPointerException("awayTeam cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing score",
                        () -> new Match(TEAM_A, TEAM_B, null, START_TIME),
                        new NullPointerException("score cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing startTime",
                        () -> new Match(TEAM_A, TEAM_B, SCORE, null),
                        new NullPointerException("startTime cannot be null")
                ),
                new InvalidInputTC<>(
                        "non unique teams - same references",
                        () -> new Match(TEAM_A, TEAM_A, SCORE, START_TIME),
                        new NonUniqueTeamsException()
                ),
                new InvalidInputTC<>(
                        "non unique teams - equal objects",
                        () -> new Match(new Team("A"), new Team("A"), SCORE, START_TIME),
                        new NonUniqueTeamsException()
                )
        ).map(tc ->
                dynamicTest("should fail when " + tc.name(), () -> {
                    Exception thrown = assertThrows(tc.expected().getClass(), tc.given()::get);
                    assertEquals(tc.expected().getMessage(), thrown.getMessage());
                })
        );
    }

    @TestFactory
    public Stream<DynamicTest> shouldNotFailWhenValidParameters() {
        return of(
                new ValidInputTC<>("valid match", () -> new Match(TEAM_A, TEAM_B, SCORE, START_TIME))
        ).map(tc ->
                dynamicTest("should not fail when " + tc.name(), () -> assertDoesNotThrow(tc.given()::get))
        );
    }

    @Test
    public void shouldReturnMatchId() {
        // given
        Match match = new Match(TEAM_A, TEAM_B, SCORE, START_TIME);

        // when
        MatchId actual = match.getMatchId();

        // then
        MatchId expected = new MatchId(TEAM_A, TEAM_B);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTotalScore() {
        // given
        Match match = new Match(TEAM_A, TEAM_B, new Score(2, 1), START_TIME);

        // when, then
        assertEquals(3, match.getTotalScore());
    }

    @Test
    public void shouldUpdateScore() {
        // given
        Match match = new Match(TEAM_A, TEAM_B, new Score(1,2), START_TIME);
        Score newScore = new Score(2,3);

        // when
        Match actual = match.updateScore(newScore);

        // then
        Match expected = new Match(TEAM_A, TEAM_B, newScore, START_TIME);
        assertEquals(expected, actual);
        assertNotSame(match, actual);
    }
}
