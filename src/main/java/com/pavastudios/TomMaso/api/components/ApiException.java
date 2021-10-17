package com.pavastudios.TomMaso.api.components;

public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
