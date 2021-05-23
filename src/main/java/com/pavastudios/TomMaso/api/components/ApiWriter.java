package com.pavastudios.TomMaso.api.components;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Writer;

@SuppressWarnings("unused")
public class ApiWriter extends JsonWriter {
    private final JsonWriter writer;
    private boolean errorWritten = false;

    public ApiWriter(Writer writer) {
        super(writer);
        this.writer = new JsonWriter(writer);
    }

    public boolean isLenient() {
        return writer.isLenient();
    }

    public JsonWriter beginArray() throws IOException {
        return writer.beginArray();
    }

    public JsonWriter endArray() throws IOException {
        return writer.endArray();
    }

    public JsonWriter beginObject() throws IOException {
        return writer.beginObject();
    }

    public JsonWriter endObject() throws IOException {
        return writer.endObject();
    }

    public JsonWriter name(String name) throws IOException {
        if (ApiManager.ERROR_PROP.equals(name)) errorWritten = true;
        return writer.name(name);
    }

    public boolean isErrorWritten() {
        return errorWritten;
    }

    public JsonWriter value(String value) throws IOException {
        return writer.value(value);
    }

    public JsonWriter jsonValue(String value) throws IOException {
        return writer.jsonValue(value);
    }

    public JsonWriter nullValue() throws IOException {
        return writer.nullValue();
    }

    public JsonWriter value(boolean value) throws IOException {
        return writer.value(value);
    }

    public JsonWriter value(Boolean value) throws IOException {
        return writer.value(value);
    }

    public JsonWriter value(double value) throws IOException {
        return writer.value(value);
    }

    public JsonWriter value(long value) throws IOException {
        return writer.value(value);
    }

    public JsonWriter value(Number value) throws IOException {
        return writer.value(value);
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }
}
