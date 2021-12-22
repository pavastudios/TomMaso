package com.pavastudios.TomMaso.api.components;

import org.intellij.lang.annotations.MagicConstant;

import javax.servlet.http.HttpServletResponse;

/**
 * ApiException rappresenta un'eccezione generata durante l'elaborazione di una richiesta API
 * */
public class ApiException extends RuntimeException {
    private final int statusCode;

    /**
     * Crea una nuova ApiException da lanciare in modo da generare un messaggio di errore nella risposta HTTP del
     * client se lanciata all'interno di una gestione di un'API
     *
     * @param statusCode statusCode della risposta HTTP
     * @param message messaggio di errore da mandare al client nella risposta HTTP
     */
    public ApiException(@MagicConstant(valuesFromClass = HttpServletResponse.class) int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
