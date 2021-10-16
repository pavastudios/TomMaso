package com.pavastudios.TomMaso.api.components;

import com.google.gson.stream.JsonReader;
import com.pavastudios.TomMaso.utility.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApiLoader {
    private static final String[] API_FILE_PATHS = new String[]{
            "/jsons/api/"
    };


    private static void parseApiFile(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            parseApiEndpoint(reader);
        }
    }

    private static void parseApiEndpoint(JsonReader reader) {
        ApiEndpoint e = new ApiEndpoint(reader);
        ApiManager.API.put(e.getEndpoint(), e);
    }

    static void loadApi() {
        ApiEndpoint.ApiJsonParser.startApiSearch();
        for (String file : API_FILE_PATHS) {
            try {
                InputStream apiFile = Utility.readResource(file);
                assert apiFile != null;
                InputStreamReader jsonFile = new InputStreamReader(apiFile);
                JsonReader reader = new JsonReader(jsonFile);
                parseApiFile(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ApiEndpoint.ApiJsonParser.stopApiSearch();
    }
}
