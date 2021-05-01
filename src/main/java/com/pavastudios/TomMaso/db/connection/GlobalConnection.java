package com.pavastudios.TomMaso.db.connection;

import com.pavastudios.TomMaso.db.queries.Queries;
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
        }
        CONNECTION = new MasterConnection(conn);
    }
    public static void init() throws SQLException{
        if(initialized)return;
        Queries.initQueries();
        initialized=true;
    }


    public static void closeConnection()throws SQLException{
        CONNECTION.close();
    }

}
