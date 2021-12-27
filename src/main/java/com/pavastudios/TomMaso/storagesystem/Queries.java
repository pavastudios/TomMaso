package com.pavastudios.TomMaso.storagesystem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe Queries contiene i metodi utili alla gestione e all'esecuzione delle query
 */
public class Queries {

    /**
     * Modifica la query aggiungendo caratteri di escape
     * @param toEscape Query iniziale
     * @return Query sanificata
     */
    public static String escapeLike(String toEscape) {
        //Evitare wildcard nella stringa e fare il corretto escape di \
        return toEscape
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }

    /**
     * Crea un'istanza del model richiesto tramite un ResultSet
     * @param entity Enumerazione contenente le associazioni
     * @param rs Risultato di una query
     * @param <T> Tipo del model chiesto
     * @return Istanza del model creata
     */
    @SuppressWarnings("all")
    public static <T> @Nullable T resultSetToModel(Entities entity, ResultSet rs) {
        T result = null;
        try {
            result = (T) entity.entityClass.getMethod("fromResultSet", ResultSet.class).invoke(null, rs);
        } catch (Exception ignore) {
        }
        return result;
    }

    /**
     * Metodo generico per trovare un'istanza da un id
     * @param entity Enumerazione contenente le associazioni
     * @param id Id da cercare
     * @param <T> Tipo dell'istanza da cercare
     * @return Istanza trovata
     * @throws SQLException Problema con il database
     */
    @SuppressWarnings("unchecked")
    public static <T> @Nullable T findById(Entities entity, int id) throws SQLException {
        entity.findByIdStmt.setInt(1, id);
        ResultSet rs = entity.findByIdStmt.executeQuery();
        Object result = null;
        if (rs.first())
            result = resultSetToModel(entity, rs);
        rs.close();
        return (T) result;
    }

    /**
     * Crea una lista di istanze del model richiesto tramite un ResultSet
     * @param entity Enumerazione contenente le associazioni
     * @param rs Risultato di una query
     * @param <T> Tipo del model chiesto
     * @return Lista di istanze del model creata
     * @throws SQLException Problema con il database
     */
    @SuppressWarnings("all")
    public static <T> @NotNull List<T> resultSetToList(Entities entity, ResultSet rs) throws SQLException {
        ArrayList<T> list = new ArrayList<>();
        if (rs.first()) {
            do {
                T result = resultSetToModel(entity, rs);
                list.add(result);
            } while (rs.next());
        }
        list.trimToSize();
        return list;
    }

}