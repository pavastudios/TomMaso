package com.pavastudios.TomMaso.api;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.stream.Stream;

class ApiParserTest {

    private static HashMap<String, String[]> getMap() {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("A", new String[]{"false"});
        map.put("B", new String[]{"true"});
        map.put("C", new String[]{"123"});
        map.put("D", new String[]{"-1321"});
        map.put("E", new String[]{"0"});
        map.put("F", new String[]{"String"});
        map.put("G", new String[]{"Random"});
        map.put("H", new String[]{""});
        return map;
    }

    private static ApiParser mockParser() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getParameterMap()).thenReturn(getMap());
        return new ApiParser(req);
    }

    public static Stream<Named<Executable>> wrongParams() {
        ApiParser parser = mockParser();
        return Stream.of(
                Named.of("Bool instead of int", () -> parser.getValueInt("A")),
                Named.of("String instead of int", () -> parser.getValueInt("F")),
                Named.of("Empty instead of int", () -> parser.getValueInt("H")),
                Named.of("Int instead of bool", () -> parser.getValueBool("C")),
                Named.of("String instead of bool", () -> parser.getValueBool("F")),
                Named.of("Empty instead of bool", () -> parser.getValueBool("H"))
        );
    }

    @Test
    public void getValueString() {
        ApiParser parser = mockParser();
        Assertions.assertEquals("false", parser.getValueString("A"));
        Assertions.assertEquals("", parser.getValueString("H"));
        Assertions.assertEquals("-1321", parser.getValueString("D"));
    }

    @Test
    public void getValueInt() {
        ApiParser parser = mockParser();
        Assertions.assertEquals(123, parser.getValueInt("C"));
        Assertions.assertEquals(-1321, parser.getValueInt("D"));
        Assertions.assertEquals(0, parser.getValueInt("E"));
    }

    @Test
    public void getValueBool() {
        ApiParser parser = mockParser();
        Assertions.assertFalse(parser.getValueBool("A"));
        Assertions.assertTrue(parser.getValueBool("B"));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("wrongParams")
    public void wrongParams(Executable e) {
        Assertions.assertThrows(ApiException.class, e);
    }
}