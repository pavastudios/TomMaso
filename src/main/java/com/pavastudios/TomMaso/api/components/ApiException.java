package com.pavastudios.TomMaso.api.components;

import org.intellij.lang.annotations.MagicConstant;

import javax.servlet.http.HttpServletResponse;

public class ApiException extends RuntimeException {
    private final int statusCode;

    public ApiException(@MagicConstant(valuesFromClass = HttpServletResponse.class) int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
