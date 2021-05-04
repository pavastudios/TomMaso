package com.pavastudios.TomMaso.api;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.model.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ApiEndpoint {

    public interface Manage{
        void executeEndpoint(ApiParser parser, JsonWriter writer, Utente user) throws SQLException, IOException;
    }

    private final Manage action;
    private final List<ApiParam> params;
    private final boolean requireLogin;
    private final String endpoint;

    public ApiEndpoint(String endpoint, boolean requireLogin,Manage action, ApiParam... params) {
        this.endpoint = endpoint;
        this.action = action;
        this.requireLogin = requireLogin;
        this.params = Arrays.asList(params);
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

    public void manage(ApiParser parser, JsonWriter writer, Utente user) throws SQLException, IOException {
        action.executeEndpoint(parser, writer, user);
    }

}
