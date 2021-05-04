package com.pavastudios.TomMaso.api;

import java.util.HashMap;

public class ApiGroup {
    private final String groupName;
    private final HashMap<String,ApiEndpoint> endpoints;

    public ApiGroup(String groupName,ApiEndpoint... endpointList) {
        endpoints=new HashMap<>(endpointList.length);
        this.groupName = groupName;
        for(ApiEndpoint point:endpointList)endpoints.put(point.getEndpoint(),point);
    }

    public String getGroupName() {
        return groupName;
    }

    public ApiEndpoint getEndpoint(String name) {
        return endpoints.get(name);
    }
}
