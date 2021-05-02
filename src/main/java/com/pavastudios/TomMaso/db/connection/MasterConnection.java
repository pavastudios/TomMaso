package com.pavastudios.TomMaso.db.connection;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.SQLException;

public class MasterConnection {
    private final Connection connection;

    public MasterConnection(Connection connection) {
        this.connection = connection;
    }

    public MasterPreparedStatement prepareStatement(@Language("sql") String sql, int returnGeneratedKeys) throws SQLException {
        return new MasterPreparedStatement(connection, sql, returnGeneratedKeys);
    }

    public MasterPreparedStatement prepareStatement(@Language("sql") String sql) throws SQLException {
        return new MasterPreparedStatement(connection, sql);
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.connection.setAutoCommit(autoCommit);
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }
}
