package com.pavastudios.TomMaso.api.components;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ApiParser {
    private final ApiEndpoint endpoint;
    private final HttpServletRequest req;
    private final HashMap<ApiParam, Object> params = new HashMap<>();
    private ApiParam error;

    public ApiParser(ApiEndpoint endpoint, HttpServletRequest req) {
        this.endpoint = endpoint;
        this.req = req;
    }

    public void parse() {
        for (ApiParam param : endpoint.getParams()) {
            if (error != null) break;
            if (param.isArray())
                params.put(param, arrayParse(param));
            else
                params.put(param, atomicParse(param));
        }
    }


    public boolean hasError() {
        return error != null;
    }

    public ApiParam getError() {
        return error;
    }

    private Object atomicParse(ApiParam param) throws NumberFormatException {
        String s = req.getParameter(param.getName());
        if (s == null) { //handle param not found
            if (!param.isOptional()) {
                error = param;
                return null;
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
            error = param;
        }
        return null;
    }

    private Object[] arrayParse(ApiParam param) {
        String[] values = req.getParameterValues(param.getName());
        if (values == null) {
            if (!param.isOptional()) {
                error = param;
                return null;
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
            error = param;
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
