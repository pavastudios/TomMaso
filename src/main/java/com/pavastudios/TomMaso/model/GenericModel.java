package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GenericModel {
    static Object fromResultSet(ResultSet rs) throws SQLException {
        throw new RuntimeException("fromResultSet must be overwritten");
    }

    void writeJson(JsonWriter writer) throws IOException;
}
