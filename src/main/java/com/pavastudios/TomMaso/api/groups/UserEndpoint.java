package com.pavastudios.TomMaso.api.groups;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.api.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;

import java.io.IOException;
import java.sql.SQLException;

public class UserEndpoint {
    public static final String GROUP_NAME = "user";
    private static final String FETCH_ENDPOINT_NAME = "find-user";
    private static final ApiEndpoint.Manage FIND_ACTION= (parser, writer, user) -> {
        String username=parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if(u==null){
            writer.name(ApiManager.ERROR_PROP).value("username non trovato");
            return;
        }
        writer.name("response");
        u.writeJson(writer);
    };
    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
            new ApiEndpoint(FETCH_ENDPOINT_NAME, true, FIND_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            )
    );
}