package com.pavastudios.TomMaso.access;

import com.pavastudios.TomMaso.utility.Tuple2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SecurityTest {

    private static Stream<String> workingCrypt() {
        return Stream.of(
                "admin",
                "u.user123"
        );
    }

    private static Stream<String> notWorkingCrypt() {
        return Stream.of(
                new String[]{null}
        );
    }

    private static Stream<Tuple2<String, String>> workingVerify() {
        return Stream.of(
                new Tuple2<>("$6$cNwc6dPi$wNM2t4p9eMJgPTHvOnOXXKT204sI7F6Q4v1W4c2wyxHnRtnZQTA8wRIEVC84PjosKakKMC7KNhdRUBFE7U9Jn0", "admin"),
                new Tuple2<>("$6$RY8lcMrj$PuqAgvhM71HJY068stqEFk5oYc4mr2MQ78V/0w3sISQeq0dcW6gPiic7CI9bATqy9dP7APw/LADGyIfBtd6100", "u.user123")
        );
    }

    private static Stream<Tuple2<String, String>> notWorkingVerify() {
        return Stream.of(
                new Tuple2<>(null, null),
                new Tuple2<>(null, ""),
                new Tuple2<>("", null),
                new Tuple2<>("psw", "psw")
        );
    }

    @DisplayName("Working crypt")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingCrypt")
    void crypt(String s) {
        Assertions.assertTrue(Security.verify(Security.crypt(s), s));
    }

    @DisplayName("Not working crypt")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingCrypt")
    void cryptNotWorking(String s) {
        Assertions.assertFalse(Security.verify(Security.crypt(s), s));
    }

    @DisplayName("Working verify")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingVerify")
    void verify(Tuple2<String, String> t) {
        String s1 = t.get1();
        String s2 = t.get2();

        Assertions.assertTrue(Security.verify(s1, s2));
    }

    @DisplayName("Not working verify")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingVerify")
    void verifyNotWorking(Tuple2<String, String> t) {
        String s1 = t.get1();
        String s2 = t.get2();

        Assertions.assertFalse(Security.verify(s1, s2));
    }
}