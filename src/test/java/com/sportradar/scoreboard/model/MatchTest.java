package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.exception.NonUniqueTeamsException;
import com.sportradar.scoreboard.model.testutils.InvalidInputTC;
import com.sportradar.scoreboard.model.testutils.ValidInputTC;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.UUID;
import java.util.stream.Stream;

import static java.util.UUID.fromString;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class MatchTest {

    private static final UUID MATCH_ID = fromString("dc630046-69c6-43ff-8e59-51299ac5a6a5");
    private static final Team TEAM_A = new Team(fromString("a01a6621-91a8-4597-8a36-9de4e4ccf203"), "A");
    private static final Team TEAM_B = new Team(fromString("24834bc1-6cc7-42bd-b601-18b1f65ac584"), "B");
    private static final Score SCORE = new Score();

    @TestFactory
    public Stream<DynamicTest> shouldFailWhenInvalidParameters() {
        return of(
                new InvalidInputTC<>(
                        "missing id",
                        () -> new Match(null, TEAM_A, TEAM_B, SCORE),
                        new NullPointerException("id cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing homeTeam",
                        () -> new Match(MATCH_ID, null, TEAM_B, SCORE),
                        new NullPointerException("homeTeam cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing awayTeam",
                        () -> new Match(MATCH_ID, TEAM_A, null, SCORE),
                        new NullPointerException("awayTeam cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing score",
                        () -> new Match(MATCH_ID, TEAM_A, TEAM_B, null),
                        new NullPointerException("score cannot be null")
                ),
                new InvalidInputTC<>(
                        "non unique teams",
                        () -> new Match(MATCH_ID, TEAM_A, TEAM_A, SCORE),
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
                new ValidInputTC<>("valid match", () -> new Match(MATCH_ID, TEAM_A, TEAM_B, SCORE))
        ).map(tc ->
                dynamicTest("should not fail when " + tc.name(), () -> assertDoesNotThrow(tc.given()::get))
        );
    }
}
