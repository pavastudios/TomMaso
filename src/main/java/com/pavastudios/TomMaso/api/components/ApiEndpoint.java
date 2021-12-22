package com.pavastudios.TomMaso.api.components;

import com.pavastudios.TomMaso.model.Utente;

import java.io.IOException;
import java.sql.SQLException;

public class ApiEndpoint {

    private final Manage action;
    private final Endpoint endpoint;

    public ApiEndpoint(Manage action, Endpoint ann) {
        this.action = action;
        this.endpoint = ann;
    }


    public boolean requireLogin() {
        return endpoint.requireLogin();
    }

    public ApiParameter[] getParams() {
        return endpoint.params();
    }

    public void manage(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException {
        action.executeEndpoint(parser, writer, user);
    }

    public interface Manage {
        void executeEndpoint(ApiParser parser, ApiWriter writer, Utente user) throws SQLException, IOException;
    }

}
