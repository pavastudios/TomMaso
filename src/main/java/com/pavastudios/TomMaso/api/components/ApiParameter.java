package com.pavastudios.TomMaso.api.components;

public @interface ApiParameter {
    String value();

    ApiParam.Type type();

    int defInt() default ApiParam.DEFAULT_INT;
}
