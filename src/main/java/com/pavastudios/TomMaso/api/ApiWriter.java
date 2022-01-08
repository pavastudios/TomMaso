package com.pavastudios.TomMaso.api;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Rappresenta un oggetto in cui è possibile scrivere elementi JSON, ed ottenere la rappresentazione finale
 */
public class ApiWriter extends JsonWriter {
    public static final String ERROR_PROP = "error";
    public static final String ERROR_CODE_PROP = "error-code";
    private static final String OK_PROP = "response";

    private final StringWriter stringWriter = new StringWriter();
    private final JsonWriter writer = new JsonWriter(stringWriter);

    /**
     * Crea un nuovo ApiWriter, generando automaticamente il campo 'ok' all'interno di un oggetto JSON,
     * l'intero contenuto della risposta deve essere contenuto in questo campo
     */
    public ApiWriter() {
        super(new StringWriter());
        this.beginObject();
        this.name(OK_PROP);
    }

    /**
     * Questo metodo indica che la generazione del JSON è stata completata e non ci saranno ulteriori oeprazioni
     * su quest'istanza, il writer verrà chiuso dopo l'invocazione di questo metodo
     *
     * @return una stringa rappresentante il JSON generato fino a questo momento
     */
    public String commit() {
        try {
            writer.endObject();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        String result = stringWriter.toString();
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
