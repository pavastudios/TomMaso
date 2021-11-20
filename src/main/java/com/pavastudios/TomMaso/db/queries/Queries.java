package com.pavastudios.TomMaso.db.queries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Queries {

    public static String escapeLike(String toEscape) {
        //Evitare wildcard nella stringa e fare il corretto escape di \
        return toEscape
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }

    @SuppressWarnings("all")
    public static <T> @Nullable T resultSetToModel(Entities entity, ResultSet rs) {
        T result = null;
        try {
            result = (T) entity.entityClass.getMethod("fromResultSet", ResultSet.class).invoke(null, rs);
        } catch (Exception ignore) {
        }
        return result;
    }

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