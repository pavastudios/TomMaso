package com.pavastudios.TomMaso.db.connection;

import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Classe per effettuare operazioni che coinvolgono le prepared statements
 * ed offrire più servizi che possono agevolare il debugging
 */

public class MasterPreparedStatement {

    private final PreparedStatement stmt;
    private final HashMap<Integer, String> parameters = new HashMap<>();
    private final String query;

    /**
     * @see PreparedStatement(Connection,String,int)
     */

    public MasterPreparedStatement(Connection connection, String sql, int returnGeneratedKeys) throws SQLException {
        this.stmt = connection.prepareStatement(sql, returnGeneratedKeys);
        this.query = sql.trim();
    }

    /**
     * @see PreparedStatement(Connection,String)
     */

    public MasterPreparedStatement(Connection connection, String sql) throws SQLException {
        this.stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        this.query = sql;
    }

    /**
     * @see PreparedStatement#setInt(int, int)
     */

    public void setInt(int parameterIndex, int x) throws SQLException {
        parameters.put(parameterIndex, String.valueOf(x));
        this.stmt.setInt(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#setString(int, String)
     */

    public void setString(int parameterIndex, String x) throws SQLException {
        parameters.put(parameterIndex, x);
        this.stmt.setString(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#setDate(int, java.sql.Date)
     */
    public void setDate(int parameterIndex, Date x) throws SQLException {
        java.sql.Date date = new java.sql.Date(x.getTime());
        parameters.put(parameterIndex, date.toString());
        this.stmt.setDate(parameterIndex, date);
    }

    /**
     * @see PreparedStatement#setBytes(int, byte[])
     */

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        parameters.put(parameterIndex, Arrays.toString(x));
        this.stmt.setBytes(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#setNull(int, int)
     */

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        parameters.put(parameterIndex, null);
        this.stmt.setNull(parameterIndex, sqlType);
    }

    /**
     * @see PreparedStatement#setTimestamp(int, Timestamp)
     */

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        parameters.put(parameterIndex, x.toString());
        this.stmt.setTimestamp(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#setBlob(int, InputStream)
     */

    public void setBlob(int parameterIndex, InputStream x) throws SQLException {
        parameters.put(parameterIndex, x.toString());
        this.stmt.setBlob(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#setLong(int, long)
     */

    public void setLong(int parameterIndex, long x) throws SQLException {
        parameters.put(parameterIndex, String.valueOf(x));
        this.stmt.setLong(parameterIndex, x);
    }

    /**
     * @see PreparedStatement#executeQuery()
     */

    public ResultSet executeQuery() throws SQLException {
        printQuery(true);
        ResultSet rs = this.stmt.executeQuery();
        System.out.format(Locale.US, "Q: %d rows\n\n", countRows(rs));
        return rs;
    }

    /**
     * @see PreparedStatement#executeUpdate()
     */

    public void executeUpdate() throws SQLException {
        printQuery(false);
        int count = this.stmt.executeUpdate();
        System.out.format(Locale.US, "U: %d rows\n\n", count);
    }

    /**
     * @see PreparedStatement#getGeneratedKeys()
     */

    public ResultSet getGeneratedKeys() throws SQLException {
        return this.stmt.getGeneratedKeys();
    }

    /**
     * Scrive la query effettuata nella console
     * @param isQuery cotrolla se la richiest è una query o una update
     * @throws SQLException
     */

    private void printQuery(boolean isQuery) throws SQLException {
        System.out.format(Locale.US, "%c: %s\n", isQuery ? 'Q' : 'U', query);
        ParameterMetaData pmd = this.stmt.getParameterMetaData();
        for (int i = 0; i < pmd.getParameterCount(); i++) {
            System.out.format(Locale.US, "\tParam %d: %s\n", i + 1, parameters.get(i + 1));
        }
    }

    /**
     * conta le righe della risposta di un result set
     * @param rs result set del quale vogliamo contare le righe
     * @return
     */

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
