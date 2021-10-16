package com.pavastudios.TomMaso.api.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.pavastudios.TomMaso.model.Utente;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

public class ApiEndpoint {

    private final Manage action;
    private final List<ApiParam> params;
    private final boolean requireLogin;
    private final String endpoint;

    public ApiEndpoint(String endpoint, boolean requireLogin, Manage action, ApiParam... params) {
        this(endpoint, requireLogin, action, Arrays.asList(params));
    }

    private ApiEndpoint(ApiEndpoint endpoint) {
        this(endpoint.endpoint, endpoint.requireLogin, endpoint.action, endpoint.params);
    }

    public ApiEndpoint(JsonReader reader) {
        this(ApiJsonParser.build(reader));
    }

    public ApiEndpoint(String endpoint, boolean requireLogin, Manage action, Collection<ApiParam> params) {
        this.endpoint = endpoint;
        this.action = action;
        this.requireLogin = requireLogin;
        this.params = new ArrayList<>(params);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public boolean requireLogin() {
        return requireLogin;
    }

    public List<ApiParam> getParams() {
        return params;
    }

    public ApiParam getFromName(String s) {
        for (ApiParam api : params) if (api.getName().equals(s)) return api;
        return null;
    }

    public void manage(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException {
        action.executeEndpoint(parser, writer, user);
    }

    public interface Manage {
        void executeEndpoint(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException;
    }

    public static class ApiJsonParser {
        public static Set<Field> ANNOTATED_FIELDS = null;

        public static void startApiSearch() {
            Reflections reflections = new Reflections("com.pavastudios.TomMaso", Scanners.FieldsAnnotated);
            ANNOTATED_FIELDS = reflections.getFieldsAnnotatedWith(Endpoint.class);
        }

        public static void stopApiSearch() {
            ANNOTATED_FIELDS = null;
        }

        private static String getString(JsonObject obj, String name) {
            JsonElement e = obj.get(name);
            if (e == null) return null;
            return e.getAsString();
        }

        private static List<ApiParam> readParams(JsonArray array) {
            ArrayList<ApiParam> params = new ArrayList<>(array.size());
            for (JsonElement obj : array) {
                JsonObject object = obj.getAsJsonObject();
                String name = object.get("name").getAsString();
                ApiParam.Type type = readType(object.get("type").getAsString());
                String defaultValue = getString(object, "default");
                params.add(new ApiParam(name, type, defaultValue));
            }
            return params;
        }

        private static ApiParam.Type readType(String nextString) {
            switch (nextString) {
                case "string":
                    return ApiParam.Type.STRING;
                case "float":
                    return ApiParam.Type.FLOAT;
                case "int":
                    return ApiParam.Type.INT;
            }
            throw new IllegalArgumentException(nextString + "can't be used as type");
        }

        private static Manage readAction(String url) {
            try {
                for (Field field : ANNOTATED_FIELDS) {
                    Endpoint ann = field.getAnnotation(Endpoint.class);
                    if (ann.value().equals(url)) {
                        field.setAccessible(true);
                        return (Manage) field.get(null);
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException("No action defined for: " + url);
        }

        public static ApiEndpoint build(JsonReader reader) {
            JsonObject object = (JsonObject) JsonParser.parseReader(reader);
            String endpoint = object.get("endpoint").getAsString();
            boolean requireLogin = object.get("require-login").getAsBoolean();
            Manage action = readAction(endpoint);
            List<ApiParam> params = readParams(object.get("params").getAsJsonArray());
            return new ApiEndpoint(endpoint, requireLogin, action, params);
        }
    }

}
