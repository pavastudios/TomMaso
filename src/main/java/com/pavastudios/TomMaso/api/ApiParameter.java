package com.pavastudios.TomMaso.api;

/**
 * Rappresenta un parametro obbligatorio all'interno di un'ApiEndpoint
 * @see ApiEndpoint
 */
public @interface ApiParameter {
    /**
     * Il tipo del parametro
     */
    Type type();

    /**
     * Il nome del parametro
     */
    String name();

    enum Type {INT, FLOAT, STRING, BOOL}
}
