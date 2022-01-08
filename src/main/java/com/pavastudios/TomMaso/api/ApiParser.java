package com.pavastudios.TomMaso.api;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 */
public class ApiParser {
    private final HashMap<String, String> params = new HashMap<>();

    public ApiParser(HttpServletRequest req) {
        parse(req.getParameterMap());
    }

    private void parse(Map<String, String[]> req) {
        for (String s : req.keySet()) {
            params.put(s, req.get(s)[0]);
        }
    }

    private ApiException invalidType(String param) {
        String errorString = String.format(Locale.US, "invalid value for param '%s'", param);
        return new ApiException(HttpServletResponse.SC_BAD_REQUEST, errorString);
    }

    @NotNull
    public String getValueString(String param) {
        if (!params.containsKey(param)) {
            String errorString = String.format(Locale.US, "missing param '%s'", param);
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, errorString);
        }
        return params.get(param);
    }

    public int getValueInt(String param) {
        try {
            return Integer.parseInt(getValueString(param));
        } catch (NumberFormatException ignore) {
            throw invalidType(param);
        }
    }

    public boolean getValueBool(String param) {
        String val = getValueString(param);
        if (!val.equals("true") && !val.equals("false")) {
            throw invalidType(param);
        }
        return getValueString(param).equals("true");
    }

}
