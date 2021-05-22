package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;

public class UserEndpoint {
    public static final String GROUP_NAME = "user";
    private static final String FETCH_ENDPOINT_NAME = "find-user";
    private static final String ADMIN_ADD_ENDPOINT_NAME = "add-admin";
    private static final String ADMIN_DEL_ENDPOINT_NAME = "remove-admin";

    private static final ApiEndpoint.Manage ADMIN_ADD_ACTION= (parser, writer, user) -> {
        String username=parser.getValueString("username");
        Utente u=Queries.findUserByUsername(username);
        if(u==null){
            writer.name(ApiManager.ERROR_PROP).value("Utente non trovato");
            return;
        }
        Queries.changeRole(u,true);
        writer.name(ApiManager.OK_PROP).value("ok");
    };
    private static final ApiEndpoint.Manage ADMIN_DEL_ACTION= (parser, writer, user) -> {
        String username=parser.getValueString("username");
        Utente u=Queries.findUserByUsername(username);
        if(u==null){
            writer.name(ApiManager.ERROR_PROP).value("Utente non trovato");
            return;
        }
        Queries.changeRole(u,false);
        writer.name(ApiManager.OK_PROP).value("ok");
    };

    private static final ApiEndpoint.Manage FIND_ACTION= (parser, writer, user) -> {
        String username=parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if(u==null){
            writer.name(ApiManager.ERROR_PROP).value("username non trovato");
            return;
        }
        writer.name(ApiManager.OK_PROP);
        u.writeJson(writer);
    };
    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
            new ApiEndpoint(FETCH_ENDPOINT_NAME, true, FIND_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(ADMIN_ADD_ENDPOINT_NAME, true, ADMIN_ADD_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(ADMIN_DEL_ENDPOINT_NAME, true, ADMIN_DEL_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            )
    );
}
