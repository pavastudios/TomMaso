package com.pavastudios.TomMaso.api.components;

import com.pavastudios.TomMaso.api.groups.BlogEndpoint;
import com.pavastudios.TomMaso.api.groups.ChatEndpoint;
import com.pavastudios.TomMaso.api.groups.CommentEndpoint;
import com.pavastudios.TomMaso.api.groups.UserEndpoint;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

public class ApiManager {
    public static final String ERROR_PROP = "error";
    public static final String OK_PROP = "response";

    public static HashMap<String, ApiGroup> apiGroup = new HashMap<>();

    static {
        apiGroup.put(ChatEndpoint.GROUP_NAME, ChatEndpoint.ENDPOINTS);
        apiGroup.put(BlogEndpoint.GROUP_NAME, BlogEndpoint.ENDPOINTS);
        apiGroup.put(UserEndpoint.GROUP_NAME, UserEndpoint.ENDPOINTS);
        apiGroup.put(CommentEndpoint.GROUP_NAME, CommentEndpoint.ENDPOINTS);
    }

    public static @Nullable ApiEndpoint getEndpoint(HttpServletRequest req) {
        String[] path = req.getPathInfo().split("/");
        if (path.length != 3) return null;
        ApiGroup group = apiGroup.get(path[1]);
        if (group == null) return null;
        return group.getEndpoint(path[2]);
    }

    public static void manageEndpoint(Session session, HttpServletRequest req, ApiWriter writer) throws IOException, SQLException {
        //Controlla esistenza endpoint
        ApiEndpoint endpoint = getEndpoint(req);
        if (endpoint == null) {
            writer.name(ERROR_PROP).value("method not implemented");
            return;
        }
        //Controlla se l'utente è loggato
        Utente user = session.getUtente();
        if (endpoint.requireLogin() && user == null) {
            writer.name(ERROR_PROP).value("user not authenticated");
            return;
        }
        //Controlla i parametri passati
        ApiParser parser = new ApiParser(endpoint, req);
        parser.parse();
        if (parser.hasError()) {
            ApiParam param = parser.getError();
            String errorString = String.format(Locale.US, "invalid param '%s'", param.getName());
            writer.name(ERROR_PROP).value(errorString);
            return;
        }

        //Richieta valida
        try {
            endpoint.manage(parser, writer, user);
        } catch (SQLException e) {
            writer.name(ERROR_PROP).value(e.getLocalizedMessage());
        }

    }
}
