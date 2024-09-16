package com.sportradar.scoreboard.model;

import com.sportradar.scoreboard.model.exception.NegativeScoreException;
import com.sportradar.scoreboard.model.testutils.InvalidInputTC;
import com.sportradar.scoreboard.model.testutils.ValidInputTC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ScoreTest {
    @TestFactory
    public Stream<DynamicTest> shouldFailWhenInvalidParameters() {
        return of(
                new InvalidInputTC<>(
                        "missing both scores",
                        () -> new Score(null, null),
                        new NullPointerException("homeScore cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing home score",
                        () -> new Score(null, 0),
                        new NullPointerException("homeScore cannot be null")
                ),
                new InvalidInputTC<>(
                        "missing away score",
                        () -> new Score(0, null),
                        new NullPointerException("awayScore cannot be null")
                ),
                new InvalidInputTC<>(
                        "home score is negative",
                        () -> new Score(-1, 0),
                        new NegativeScoreException()
                ),
                new InvalidInputTC<>(
                        "away score is negative",
                        () -> new Score(0, -1),
                        new NegativeScoreException()
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
                new ValidInputTC<>("both scores are zero", () -> new Score(0, 0)),
                new ValidInputTC<>("both scores are positive", () -> new Score(1, 1)),
                new ValidInputTC<>("home score is zero", () -> new Score(0, 1)),
                new ValidInputTC<>("away score is zero", () -> new Score(1, 1))
        ).map(tc ->
                dynamicTest("should not fail when " + tc.name(), () -> assertDoesNotThrow(tc.given()::get))
        );
    }

    @Test
    public void shouldReturnTotal() {
        // given
        Score score = new Score(1,2);

        // when
        Integer actual = score.getTotal();

        // then
        assertEquals(3, actual);
    }
}
