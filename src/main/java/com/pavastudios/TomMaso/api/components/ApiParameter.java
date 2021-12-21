package com.pavastudios.TomMaso.api.components;

public @interface ApiParameter {
    Type type();

    String name();

    enum Type {INT, FLOAT, STRING, BOOL}
}
