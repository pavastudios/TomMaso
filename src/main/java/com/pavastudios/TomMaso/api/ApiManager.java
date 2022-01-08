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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe che permette di eseguire richieste al servizio di API del sito
 */
public class ApiManager {
    private static final HashMap<String, Tuple2<Boolean, Method>> API = new HashMap<>();
    private static boolean initialized = false;

    /**
     * Trova tutte i campi annotati con Endpoint e genera una mappa contenente tutte gli endpoint
     *
     * @see Endpoint
     */
    private static void loadApiEndpoints() {
        Reflections reflections = new Reflections("com.pavastudios.TomMaso", Scanners.MethodsAnnotated);
        Set<Method> apiEndpoints = reflections.getMethodsAnnotatedWith(Endpoint.class);
        for (Method method : apiEndpoints) {
            method.setAccessible(true);
            Endpoint ann = method.getAnnotation(Endpoint.class);
            ApiManager.API.put(ann.url(), new Tuple2<>(ann.requireLogin(), method));
        }
        initialized = true;
    }

    private static @Nullable Tuple2<Boolean, Method> getEndpoint(HttpServletRequest req) {
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
     *
     * @param session sessione dell'utente che esegue la richiesta
     * @param req     richiesta HTTP dell'utente
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
        if (!initialized) {
            loadApiEndpoints();
        }
        try {
            return new Tuple2<>(200, manageEndpoint(session, req));
        } catch (ApiException e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ApiException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static String manageEndpoint(Session session, HttpServletRequest req) throws Throwable {
        ApiWriter writer = new ApiWriter();
        //Controlla esistenza endpoint
        Tuple2<Boolean, Method> endpoint = getEndpoint(req);
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
        try {
            endpoint.get2().invoke(null, parser, writer, user);
            return writer.commit();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
