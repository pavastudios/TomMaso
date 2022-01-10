package com.pavastudios.TomMaso.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class Tuple2Test {

    private static Stream<Named<Arguments>> results1() {
        return Stream.of(
                Named.of("<Integer, String>", Arguments.arguments(new Tuple2<>(1, "test1"), 1)),
                Named.of("<Tuple2, Tuple2>", Arguments.arguments(new Tuple2<>(new Tuple2<>("inner", "tuple1"),
                        new Tuple2<>("inner", "tuple2")), new Tuple2<>("inner", "tuple1")))
        );
    }

    private static Stream<Named<Arguments>> results2() {
        return Stream.of(
                Named.of("<Integer, String>", Arguments.arguments(new Tuple2<>(1, "test2"), "test2")),
                Named.of("<Tuple2, Tuple2>", Arguments.arguments(new Tuple2<>(new Tuple2<>("inner", "tuple1"),
                        new Tuple2<>("inner", "tuple2")), new Tuple2<>("inner", "tuple2")))
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("results1")
    void get1(Arguments a) {
        Tuple2 tuple = (Tuple2) a.get()[0];
        Object result = a.get()[1];
        Assertions.assertEquals(tuple.get1(), result);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("results2")
    void get2(Arguments a) {
        Tuple2 tuple = (Tuple2) a.get()[0];
        Object result = a.get()[1];
        Assertions.assertEquals(tuple.get2(), result);
    }
}