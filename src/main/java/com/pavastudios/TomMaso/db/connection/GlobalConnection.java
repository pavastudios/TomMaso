package com.pavastudios.TomMaso.db.connection;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.pavastudios.TomMaso.db.DatiConnessione.JDBC_STRING;

@SuppressWarnings("unused")
public class GlobalConnection {
    private static boolean initialized=false;
    @NotNull public final static MasterConnection CONNECTION;

    static {
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(JDBC_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        CONNECTION = new MasterConnection(conn);
    }
    public static void init()throws SQLException{
        if(initialized)return;
        /*Queries.initQueries();
        Utente.initQueries();
        FileUpload.initQueries();
        FileDownload.initQueries();
        Chat.initQueries();
        Media.initQueries();
        MessaggioSistema.initQueries();
        MessaggioUtente.initQueries();
        Sessione.initQueries();
        UtenteChat.initQueries();*/
        initialized=true;
    }


    public static void closeConnection()throws SQLException{
        CONNECTION.close();
    }

}
