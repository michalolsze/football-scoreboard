package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.BlankTeamNameException;
import com.sportradar.scoreboard.model.testutils.InvalidInputTC;
import com.sportradar.scoreboard.model.testutils.ValidInputTC;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class TeamTest {

    @TestFactory
    public Stream<DynamicTest> shouldFailWhenInvalidParameters() {
        return of(
                new InvalidInputTC<>(
                        "missing name",
                        () -> new Team(null),
                        new NullPointerException("name cannot be null")
                ),
                new InvalidInputTC<>(
                        "empty name",
                        () -> new Team( ""),
                        new BlankTeamNameException()
                ),
                new InvalidInputTC<>(
                        "blank name",
                        () -> new Team(" "),
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
                new ValidInputTC<>("non blank team name", () -> new Team("A"))
        ).map(tc ->
                dynamicTest("should not fail when " + tc.name(), () -> assertDoesNotThrow(tc.given()::get))
        );
    }
}
