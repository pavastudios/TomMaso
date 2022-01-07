package com.pavastudios.TomMaso.storage.model;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaccia che rappresenta un'entità all'interno del database
 */
public interface GenericModel {

    /**
     * Metodo per ottenere un'istanza della classe che implementa l'interfaccia
     * partendo da un result set del database
     *
     * @param rs set di risultati del database
     * @return istanza della classe implementatrice
     * @throws SQLException Problemi con il result set del database
     */
    static Object fromResultSet(ResultSet rs) throws SQLException {
        throw new RuntimeException("fromResultSet must be overwritten");
    }

    /**
     * Metodo che scrive sul writer l'istanza della classe implementatrice
     *
     * @param writer writer su cui è possibile scrivere un JSON
     * @throws IOException Problemi con la lettura/scrittura del file
     */
    void writeJson(JsonWriter writer) throws IOException;
}
