package com.pavastudios.TomMaso.api.components;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;

public class ApiParser {
    private final ApiEndpoint endpoint;
    private final HttpServletRequest req;
    private final HashMap<ApiParam, Object> params = new HashMap<>();

    public ApiParser(ApiEndpoint endpoint, HttpServletRequest req) {
        this.endpoint = endpoint;
        this.req = req;
    }

    /**
     * @throws ApiException if parameter is missing or the wrong type
     */
    public void parse() {
        for (ApiParam param : endpoint.getParams()) {
            if (param.isArray())
                params.put(param, arrayParse(param));
            else
                params.put(param, atomicParse(param));
        }
    }

    private Object atomicParse(ApiParam param) throws NumberFormatException {
        String s = req.getParameter(param.getName());
        if (s == null) { //handle param not found
            if (!param.isOptional()) {
                String errorString = String.format(Locale.US, "missing param '%s'", param.getName());
                throw new ApiException(400, errorString);
            }
            s = param.getDefValue();
        }
        try {
            switch (param.getType()) {//prova ad associare il valore
                case INT:
                    return Long.parseLong(s);
                case FLOAT:
                    return Double.parseDouble(s);
                case STRING:
                    return s;
            }
        } catch (NumberFormatException ignore) {
            String errorString = String.format(Locale.US, "invalid value for param '%s'", param.getName());
            throw new ApiException(400, errorString);
        }
        return null;
    }

    private Object[] arrayParse(ApiParam param) {
        String[] values = req.getParameterValues(param.getName());
        if (values == null) {
            if (!param.isOptional()) {
                String errorString = String.format(Locale.US, "missing param '%s'", param.getName());
                throw new ApiException(400, errorString);
            }
            values = param.getDefValueArray();
        }
        try {
            switch (param.getType()) {//prova ad associare il valore
                case INT:
                    return arrayInt(values);
                case FLOAT:
                    return arrayFloat(values);
                case STRING:
                    return values;
            }
        } catch (NumberFormatException ignore) {
            String errorString = String.format(Locale.US, "invalid value for param '%s'", param.getName());
            throw new ApiException(400, errorString);
        }
        return null;
    }

    private Double[] arrayFloat(String[] values) {
        Double[] arr = new Double[values.length];
        for (int i = 0; i < values.length; i++)
            arr[i] = Double.parseDouble(values[i]);
        return arr;
    }

    private Long[] arrayInt(String[] values) {
        Long[] arr = new Long[values.length];
        for (int i = 0; i < values.length; i++)
            arr[i] = Long.parseLong(values[i]);
        return arr;
    }

    public Object getValue(ApiParam param) {
        return params.get(param);
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

    public Object getValueFromName(String s) {
        return params.get(endpoint.getFromName(s));
    }

}
