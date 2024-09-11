package com.sportradar.scoreboard.model.testutils;

import java.util.function.Supplier;

public record InvalidInputTC<I, T extends Exception>(String name, Supplier<I> given, T expected){}