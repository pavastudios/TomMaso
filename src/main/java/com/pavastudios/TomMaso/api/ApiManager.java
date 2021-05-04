package com.pavastudios.TomMaso.api;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.api.groups.ChatEndpoint;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;

public class ApiManager {
    public static final String ERROR_PROP = "error";

    public static HashMap<String,ApiGroup>apiGroup=new HashMap<>();

    public static @Nullable ApiEndpoint getEndpoint(HttpServletRequest req) {
        String[]path=req.getPathInfo().split("/");
        if(path.length!=3)return null;
        ApiGroup group=apiGroup.get(path[1]);
        if(group==null)return null;
        return group.getEndpoint(path[2]);
    }

    public static void manageEndpoint(HttpServletRequest req, JsonWriter writer, Utente user) throws IOException, SQLException {
        ApiEndpoint endpoint=getEndpoint(req);
        if (endpoint == null) {
            writer.name(ERROR_PROP).value("method not implemented");
            return;
        }
        if (endpoint.requireLogin() && user == null) {
            writer.name(ERROR_PROP).value("user not authenticated");
            return;
        }
        ApiParser parser = new ApiParser(endpoint, req);
        parser.parse();
        if (parser.hasError()) {
            ApiParam param = parser.getError();
            String errorString = String.format(Locale.US, "invalid param '%s'", param.getName());
            writer.name(ERROR_PROP).value(errorString);
            return;
        }
        endpoint.manage(parser,writer,user);
    }


    static{
        apiGroup.put(ChatEndpoint.GROUP_NAME,ChatEndpoint.ENDPOINTS);
    }
}
