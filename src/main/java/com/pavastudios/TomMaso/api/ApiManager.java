package com.pavastudios.TomMaso.api;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.storage.model.Utente;
import com.pavastudios.TomMaso.utility.Tuple2;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe che permette di eseguire richieste al servizio di API del sito
 */
public class ApiManager {
    private static final HashMap<String, Tuple2<Boolean, ApiAction>> API = new HashMap<>();

    static {
        try {
            loadApiEndpoints();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Trova tutte i campi annotati con Endpoint e genera una mappa contenente tutte gli endpoint
     *
     * @see ApiAction
     * @see Endpoint
     */
    private static void loadApiEndpoints() throws IllegalAccessException {
        Reflections reflections = new Reflections("com.pavastudios.TomMaso", Scanners.FieldsAnnotated);
        Set<Field> apiEndpoints = reflections.getFieldsAnnotatedWith(Endpoint.class);
        for (Field field : apiEndpoints) {
            field.setAccessible(true);
            Endpoint ann = field.getAnnotation(Endpoint.class);
            ApiAction manage = (ApiAction) field.get(null);
            ApiManager.API.put(ann.url(), new Tuple2<>(ann.requireLogin(), manage));
        }
    }

    private static @Nullable Tuple2<Boolean, ApiAction> getEndpoint(HttpServletRequest req) {
        return API.get(req.getPathInfo());
    }

    private static String writeApiError(ApiException e) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        try {
            writer.beginObject();
            writer.name(ApiWriter.ERROR_PROP).value(e.getMessage());
            writer.name(ApiWriter.ERROR_CODE_PROP).value(e.getStatusCode());
            writer.endObject();
        } catch (IOException ignore) {
        }//non può essere lanciato
        return stringWriter.toString();
    }

    /**
     * Esegue una chiamata al servizio di API del sito, se durante l'esecuzione di un endpoint viene lanciata
     * una ApiException la risposta diventerà di errore
     * @param session sessione dell'utente che esegue la richiesta
     * @param req richiesta HTTP dell'utente
     * @return una tupla in cui il primo elemento è un intero contenente lo status code della risposta HTTP,
     * mentre il secondo è una stringa contenente il body in formato JSON del della risposta HTTP
     * @see Tuple2
     * @see ApiException
     */
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
            throw new ApiException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static String manageEndpoint(Session session, HttpServletRequest req) throws ApiException, SQLException, IOException {
        ApiWriter writer = new ApiWriter();
        //Controlla esistenza endpoint
        Tuple2<Boolean, ApiAction> endpoint = getEndpoint(req);
        if (endpoint == null) {
            throw new ApiException(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "method not implemented");
        }
        //Controlla se l'utente è loggato
        if (endpoint.get1() && !session.isLogged()) {
            throw new ApiException(HttpServletResponse.SC_UNAUTHORIZED, "user not authenticated");
        }
        //Controlla i parametri passati
        ApiParser parser = new ApiParser(req);

        //Richieta valida, esegui l'endpoint
        Utente user = session.getUtente();
        endpoint.get2().executeEndpoint(parser, writer, user);
        return writer.commit();
    }
}
