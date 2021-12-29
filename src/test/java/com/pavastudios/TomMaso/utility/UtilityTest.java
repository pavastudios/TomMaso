package com.pavastudios.TomMaso.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class UtilityTest {

    private static Stream<Named<String>> validUsernames() {
        return Stream.of(
                Named.of("Min length", "AAAAAAAA"),
                Named.of("Max length", "AAAAAAAAAAAAAAAAAAAA"),
                Named.of("Only upper", "AAAAKKKKZZZZ"),
                Named.of("Only lower", "aaaakkkkzzzz"),
                Named.of("With number", "A0123456789"),
                Named.of("Only spacial start _", "_.-._.-._")
        );
    }

    private static Stream<Named<String>> invalidUsernames() {
        return Stream.of(
                Named.of("Too short", "AAAAAAA"),
                Named.of("Too long", "AAAAAAAAAAAAAAAAAAAAA"),
                Named.of("Start number", "00000000"),
                Named.of("Start special 1", "........"),
                Named.of("Start special 3", "--------"),
                Named.of("Special end", "AAAAAAAA!"),
                Named.of("null", null)
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("validUsernames")
    void standardName(String name) {
        Assertions.assertTrue(Utility.isStandardName(name));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("invalidUsernames")
    void notStandardName(String name) {
        Assertions.assertFalse(Utility.isStandardName(name));
    }
}