package com.pavastudios.TomMaso.utility.api;

import java.util.Arrays;
import java.util.List;

public class Endpoint {
    private final List<ApiParam> params;
    private final boolean requireLogin;
    private final String endpoint;

    public Endpoint(String endpoint, boolean requireLogin, ApiParam... params) {
        this.endpoint = endpoint;
        this.requireLogin = requireLogin;
        this.params = Arrays.asList(params);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public boolean requireLogin() {
        return requireLogin;
    }

    public List<ApiParam> getParams() {
        return params;
    }

    public ApiParam getFromName(String s) {
        for (ApiParam api : params) if (api.getName().equals(s)) return api;
        return null;
    }

}
