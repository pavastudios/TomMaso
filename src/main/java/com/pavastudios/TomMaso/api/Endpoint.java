package com.pavastudios.TomMaso.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annota le informazioni dell'Endpoint
 * @see Endpoint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Endpoint {
    /**
     * L'url a cui deve rispondere queste endpoint
     */
    String url();

    /**
     * Richiede che questo endpoint può essere gestito solo per richieste da utenti che hanno eseguito
     */
    boolean requireLogin();
}
