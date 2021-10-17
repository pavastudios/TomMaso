package com.pavastudios.TomMaso.api.components;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.tuple.Tuple2;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class ApiManager {
    public static final String OK_PROP = "response";
    private static final String ERROR_PROP = "error";
    private static final String ERROR_CODE_PROP = "error-code";
    static HashMap<String, ApiEndpoint> API = new HashMap<>();

    static {
        ApiLoader.loadApi();
    }

    public static @Nullable ApiEndpoint getEndpoint(HttpServletRequest req) {
        return API.get(req.getPathInfo());
    }

    private static String writeApiError(ApiException e) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        try {
            writer.beginObject();
            writer.name(ApiManager.ERROR_PROP).value(e.getMessage());
            writer.name(ApiManager.ERROR_CODE_PROP).value(e.getStatusCode());
            writer.endObject();
        } catch (IOException ignore) {
        }//non può essere lanciato
        return stringWriter.toString();
    }

    public static Tuple2<Integer, String> apiCall(Session session, HttpServletRequest req) {
        try {
            return tryApiCall(session, req);
        } catch (ApiException e) {
            return new Tuple2<>(e.getStatusCode(), writeApiError(e));
        }
    }

    private static Tuple2<Integer, String> tryApiCall(Session session, HttpServletRequest req) throws ApiException {
        try {
            return new Tuple2<>(200, manageEndpoint(session, req));
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500, e.getMessage());
        }
    }

    private static String manageEndpoint(Session session, HttpServletRequest req) throws ApiException, SQLException, IOException {
        ApiWriter writer = new ApiWriter();
        //Controlla esistenza endpoint
        ApiEndpoint endpoint = getEndpoint(req);
        if (endpoint == null) {
            throw new ApiException(405, "method not implemented");
        }
        //Controlla se l'utente è loggato
        if (endpoint.requireLogin() && !session.isLogged()) {
            throw new ApiException(401, "user not authenticated");
        }
        //Controlla i parametri passati
        ApiParser parser = new ApiParser(endpoint, req);
        parser.parse();

        //Richieta valida, esegui l'endpoint
        Utente user = session.getUtente();
        endpoint.manage(parser, writer, user);
        return writer.commit();
    }
}
