package com.pavastudios.TomMaso.access;

import com.pavastudios.TomMaso.api.ApiEndpoint;
import com.pavastudios.TomMaso.api.ApiException;
import com.pavastudios.TomMaso.api.ApiParameter;
import com.pavastudios.TomMaso.api.Endpoint;
import com.pavastudios.TomMaso.storage.model.Utente;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("unused")
public class UserEndpoint {
    @Endpoint(url = "/user/add-admin", requireLogin = true, params = {
            @ApiParameter(name = "username", type = ApiParameter.Type.STRING)
    })
    private static final ApiEndpoint.Manage ADMIN_ADD_ACTION = (parser, writer, user) -> {
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
    private static final ApiEndpoint.Manage ADMIN_DEL_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = UserQueries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Utente non trovato");
        }
        UserQueries.changeRole2(u, false);
        writer.value("ok");
    };

    @Endpoint(url = "/user/change-permissions", requireLogin = true, params = {
            @ApiParameter(name = "id-user", type = ApiParameter.Type.INT)
    })
    private static final ApiEndpoint.Manage USER_CHANGE_PERMISSIONS = (parser, writer, user) -> {
        if (!user.getPermessi().hasPermissions(Utente.Permessi.MANAGE_USER)) {
            throw new ApiException(HttpServletResponse.SC_FORBIDDEN, "Utente non autorizzato");
        }
        int userId = parser.getValueInt("id-user");
        Utente u = UserQueries.findUserById(userId);
        if (u == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Utente non trovato");
        }
        int permessi = 0;
        for (Field field : Utente.Permessi.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                if (parser.getValueBool(field.getName())) {
                    try {
                        permessi |= field.getInt(null);
                    } catch (IllegalAccessException e) {
                    }
                }
            }
        }
        Utente.Permessi newPermissions = new Utente.Permessi(permessi);
        UserQueries.changeRole(u, newPermissions);
        writer.value("ok");
    };

}
