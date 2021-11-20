package com.pavastudios.TomMaso.api.components;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Endpoint {
    String url();

    ApiParameter[] params();

    boolean requireLogin();
}
