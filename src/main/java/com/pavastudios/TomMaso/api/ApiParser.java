package com.pavastudios.TomMaso.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public class ApiParser {
    private final ApiEndpoint endpoint;
    private final HashMap<String, Object> params = new HashMap<>();

    public ApiParser(ApiEndpoint endpoint, HttpServletRequest req) throws ApiException {
        this.endpoint = endpoint;
        parse(req.getParameterMap());
    }

    /**
     * @throws ApiException if parameter is missing or the wrong type
     */
    private void parse(Map<String, String[]> req) throws ApiException {
        for (String s : req.keySet()) {
            params.put(s, req.get(s)[0]);
        }
        for (ApiParameter param : endpoint.getParams()) {
            params.put(param.name(), parseParam(req, param));
        }
    }

    private Object parseParam(Map<String, String[]> req, ApiParameter param) throws ApiException {

        if (!req.containsKey(param.name())) { //handle param not found
            String errorString = String.format(Locale.US, "missing param '%s'", param.name());
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, errorString);
        }

        String value = req.get(param.name())[0];
        try {
            switch (param.type()) {//prova ad associare il valore
                case INT:
                    return Long.parseLong(value);
                case FLOAT:
                    return Double.parseDouble(value);
                case STRING:
                    return value;
                case BOOL:
                    if (!value.equals("false") && !value.equals("true")) {
                        throw new NumberFormatException();
                    }
                    return value.equals("true");
            }
        } catch (NumberFormatException ignore) {
            String errorString = String.format(Locale.US, "invalid value for param '%s'", param.name());
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, errorString);
        }
        return null;
    }

    public String getValueString(String s) {
        return getValueFromName(s).toString();
    }

    public long getValueLong(String s) {
        return (long) getValueFromName(s);
    }

    public int getValueInt(String s) {
        return (int) getValueLong(s);
    }

    public boolean getValueBool(String s) {
        return getValueString(s).equals("true");
    }

    private Object getValueFromName(String s) {
        return params.get(s);
    }

}
