package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiException;
import com.pavastudios.TomMaso.api.components.ApiParameter;
import com.pavastudios.TomMaso.api.components.Endpoint;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import com.pavastudios.TomMaso.model.Utente;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
public class UserEndpoint {
    @Endpoint(url = "/user/add-admin", requireLogin = true, params = {
            @ApiParameter(name = "username", type = ApiParameter.Type.STRING)
    })
    public static final ApiEndpoint.Manage ADMIN_ADD_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = UserQueries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Utente non trovato");
        }
        UserQueries.changeRole2(u, true);
        writer.value("ok");
    };
    @Endpoint(url = "/user/remove-admin", requireLogin = true, params = {
            @ApiParameter(name = "username", type = ApiParameter.Type.STRING)
    })
    public static final ApiEndpoint.Manage ADMIN_DEL_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = UserQueries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Utente non trovato");
        }
        UserQueries.changeRole2(u, false);
        writer.value("ok");
    };
}
