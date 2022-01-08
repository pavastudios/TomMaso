package com.pavastudios.TomMaso.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApiWriterTest {
    @Test
    public void writeNothing() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            ApiWriter writer = new ApiWriter();
            writer.commit();
        });
    }

    @Test
    public void writeValue() {
        ApiWriter writer = new ApiWriter();
        writer.value("ok");
        Assertions.assertEquals("{\"response\":\"ok\"}", writer.commit());
        Assertions.assertThrows(IllegalStateException.class, () -> writer.value("ok"));
    }

    @Test
    public void close() {
        ApiWriter writer = new ApiWriter();
        writer.value("ok");
        writer.endObject();
        Assertions.assertDoesNotThrow(writer::close);
    }
}