package com.pavastudios.TomMaso.db.connection;

import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class MasterPreparedStatement {

    private final PreparedStatement stmt;
    private final HashMap<Integer, String> parameters = new HashMap<>();
    private final String query;

    public MasterPreparedStatement(Connection connection, String sql, int returnGeneratedKeys) throws SQLException {
        this.stmt = connection.prepareStatement(sql, returnGeneratedKeys);
        this.query = sql.trim();
    }

    public MasterPreparedStatement(Connection connection, String sql) throws SQLException {
        this.stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        this.query = sql;
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        parameters.put(parameterIndex, String.valueOf(x));
        this.stmt.setInt(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        parameters.put(parameterIndex, x);
        this.stmt.setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        parameters.put(parameterIndex, Arrays.toString(x));
        this.stmt.setBytes(parameterIndex, x);
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        parameters.put(parameterIndex, null);
        this.stmt.setNull(parameterIndex, sqlType);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        parameters.put(parameterIndex, x.toString());
        this.stmt.setTimestamp(parameterIndex, x);
    }

    public void setBlob(int parameterIndex, InputStream x) throws SQLException {
        parameters.put(parameterIndex, x.toString());
        this.stmt.setBlob(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        parameters.put(parameterIndex, String.valueOf(x));
        this.stmt.setLong(parameterIndex, x);
    }

    public ResultSet executeQuery() throws SQLException {
        printQuery(true);
        ResultSet rs = this.stmt.executeQuery();
        System.out.format(Locale.US, "Q: %d rows\n\n", countRows(rs));
        return rs;
    }

    public void executeUpdate() throws SQLException {
        printQuery(false);
        int count = this.stmt.executeUpdate();
        System.out.format(Locale.US, "U: %d rows\n\n", count);
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return this.stmt.getGeneratedKeys();
    }

    private void printQuery(boolean isQuery) throws SQLException {
        System.out.format(Locale.US, "%c: %s\n", isQuery ? 'Q' : 'U', query);
        ParameterMetaData pmd = this.stmt.getParameterMetaData();
        for (int i = 0; i < pmd.getParameterCount(); i++) {
            System.out.format(Locale.US, "\tParam %d: %s\n", i + 1, parameters.get(i + 1));
        }
    }

    private int countRows(ResultSet rs) {
        int size = 0;
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        } catch (Exception ignore) {
        }
        return size;
    }

}
