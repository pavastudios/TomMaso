package com.pavastudios.TomMaso.storage;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * MasterConnection è un proxy che permette l'invocazione di metodi e di classi estenre
 * attraverso metodi d'istanza per avere strong typing
 */

public class MasterConnection {
    private final Connection connection;

    /**
     * MasterConnection imposta la connessione da utilizzare
     *
     * @param connection la connessione da utilizzare
     */

    public MasterConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Acquisisce una richiesta e la inoltra alla connessione che è in utilizzo
     *
     * @param sql                 è la richiesta effettuata al database
     * @param returnGeneratedKeys è un valore di ritorno ottenuto da un'operazione su database
     * @return una master prepared statement
     * @throws SQLException
     */

    public MasterPreparedStatement prepareStatement(@Language("sql") String sql, int returnGeneratedKeys) throws SQLException {
        return new MasterPreparedStatement(connection, sql, returnGeneratedKeys);
    }

    /**
     * Acquisisce una richiesta e la inoltra alla connessione che è in utilizzo
     * @param sql è la richiesta effettuata al database
     * @return una master prepared statement
     * @throws SQLException
     */

    /**
     * @param sql
     * @return
     * @throws SQLException
     */
    public MasterPreparedStatement prepareStatement(@Language("sql") String sql) throws SQLException {
        return new MasterPreparedStatement(connection, sql);
    }

    /**
     * Chiude la connessione attualmente in utilizzo
     *
     * @throws SQLException
     */

    public void close() throws SQLException {
        this.connection.close();
    }
}
