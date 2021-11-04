package com.pavastudios.TomMaso.api.components;

public @interface ApiParameter {
    String name();

    ApiParam.Type type();

    int defInt() default ApiParam.DEFAULT_INT;
}
