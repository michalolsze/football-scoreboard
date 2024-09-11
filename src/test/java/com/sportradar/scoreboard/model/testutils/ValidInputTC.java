package com.sportradar.scoreboard.model.testutils;

import java.util.function.Supplier;

public record ValidInputTC<I>(String name, Supplier<I> given) {
}
