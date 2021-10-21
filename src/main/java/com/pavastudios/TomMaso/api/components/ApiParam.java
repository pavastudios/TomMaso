package com.pavastudios.TomMaso.api.components;

public class ApiParam {
    public static final int DEFAULT_INT = Integer.MIN_VALUE;
    private final String name;
    private final boolean optional;
    private final Type type;
    private final String defValue;
    private final String[] defValueArray;

    public ApiParam(String name, Type type, Object[] defValue) {
        this.name = name;
        this.type = type;
        this.defValue = null;
        this.defValueArray = toStringArray(defValue);
        this.optional = true;
    }

    public ApiParam(String name, Type type, Object defValue) {
        this.name = name;
        this.type = type;
        this.optional = defValue != null;
        this.defValue = defValue != null ? defValue.toString() : null;
        this.defValueArray = null;
    }

    public ApiParam(String name, Type type) {
        this(name, type, (Object) null);
    }

    private static String[] toStringArray(Object[] defValue) {
        String[] strs = new String[defValue.length];
        for (int i = 0; i < defValue.length; i++) strs[i] = defValue[i].toString();
        return strs;
    }

    public boolean isArray() {
        return this.defValueArray != null;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    public Type getType() {
        return type;
    }

    public String getDefValue() {
        return defValue;
    }

    public String[] getDefValueArray() {
        return defValueArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiParam apiParam = (ApiParam) o;

        return name.equals(apiParam.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public enum Type {INT, FLOAT, STRING}
}
