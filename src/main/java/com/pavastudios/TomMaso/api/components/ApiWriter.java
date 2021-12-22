package com.pavastudios.TomMaso.api.components;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;


public class ApiWriter extends JsonWriter {
    public static final String ERROR_PROP = "error";
    public static final String ERROR_CODE_PROP = "error-code";
    private static final String OK_PROP = "response";

    private final StringWriter stringWriter = new StringWriter();
    private final JsonWriter writer = new JsonWriter(stringWriter);

    /**
     * JsonWriter implementation written to create API responses, it automatically wrap the written json inside an object
     * and it doesn't need to catch IOException because underlying it uses a StringWriter which allows the retrieve
     * of the written JSON
     */
    public ApiWriter() {
        super(new StringWriter());
        this.beginObject();
        this.name(OK_PROP);
    }

    /**
     * This method indicates that the JSON generation has been completed and no further operation
     * will be made on this instance, using this method will close the writer
     *
     * @return The generated JSON until this point
     */
    public String commit() {
        try {
            writer.endObject();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        String result = stringWriter.toString();
        System.out.println(result);
        this.close();
        return result;
    }

    @Override
    public boolean isLenient() {
        return writer.isLenient();
    }

    @Override
    public ApiWriter beginArray() {
        try {
            writer.beginArray();
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter endArray() {
        try {
            writer.endArray();
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter beginObject() {
        try {
            writer.beginObject();
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter endObject() {
        try {
            writer.endObject();
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter name(String name) {
        try {
            writer.name(name);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(String value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter jsonValue(String value) {
        try {
            writer.jsonValue(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter nullValue() {
        try {
            writer.nullValue();
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(boolean value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(Boolean value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(double value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(long value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ApiWriter value(Number value) {
        try {
            writer.value(value);
            return this;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void close() {
        try {
            super.jsonValue("{}");
            writer.close();
            super.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
