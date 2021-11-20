package com.pavastudios.TomMaso.api.components;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.util.Set;

public class ApiLoader {

    static void loadApi() throws IllegalAccessException {
        Reflections reflections = new Reflections("com.pavastudios.TomMaso", Scanners.FieldsAnnotated);
        Set<Field> apiEndpoints = reflections.getFieldsAnnotatedWith(Endpoint.class);
        for (Field field : apiEndpoints) {
            Endpoint ann = field.getAnnotation(Endpoint.class);
            ApiEndpoint.Manage manage = (ApiEndpoint.Manage) field.get(null);
            ApiEndpoint endpoint = new ApiEndpoint(ann.url(), ann.requireLogin(), manage, toApiParams(ann.params()));
            ApiManager.API.put(ann.url(), endpoint);

        }
    }

    private static ApiParam[] toApiParams(ApiParameter[] params) {
        ApiParam[] apiParams = new ApiParam[params.length];
        for (int i = 0; i < params.length; i++) {
            ApiParameter p = params[i];
            if (p.type() == ApiParam.Type.INT && p.defInt() != ApiParam.DEFAULT_INT) {
                apiParams[i] = new ApiParam(p.name(), p.type(), p.defInt());
            } else {
                apiParams[i] = new ApiParam(p.name(), p.type());
            }
        }
        return apiParams;
    }
}
