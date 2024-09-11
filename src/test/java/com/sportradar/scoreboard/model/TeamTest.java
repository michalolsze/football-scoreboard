package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.exception.BlankTeamNameException;
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

public class TeamTest {

    private static final UUID TEAM_ID = fromString("a542a631-4496-4efb-8834-fa155ad832fd");

    @TestFactory
    public Stream<DynamicTest> shouldFailWhenInvalidParameters() {
        return of(
                new InvalidInputTC<>(
                        "missing id",
                        () -> new Team(null, "A"),
                        new NullPointerException("id cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing name",
                        () -> new Team(TEAM_ID, null),
                        new NullPointerException("name cannot be null")
                ),
                new InvalidInputTC<>(
                        "empty name",
                        () -> new Team(TEAM_ID, ""),
                        new BlankTeamNameException()
                ),
                new InvalidInputTC<>(
                        "blank name",
                        () -> new Team(TEAM_ID, " "),
                        new BlankTeamNameException()
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
                new ValidInputTC<>("non blank team name", () -> new Team(TEAM_ID, "A"))
        ).map(tc ->
                dynamicTest("should not fail when " + tc.name(), () -> assertDoesNotThrow(tc.given()::get))
        );
    }
}
