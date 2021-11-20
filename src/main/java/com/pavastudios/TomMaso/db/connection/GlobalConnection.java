package com.pavastudios.TomMaso.db.connection;

import com.pavastudios.TomMaso.db.queries.entities.*;
import org.jetbrains.annotations.NotNull;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class GlobalConnection {
    @NotNull
    public final static MasterConnection CONNECTION;
    private static boolean initialized = false;

    static {

        Connection conn = null;
        try {
            Context initCtx = new InitialContext();
            Context encCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) encCtx.lookup("jdbc/tommaso");
            conn = ds.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        CONNECTION = new MasterConnection(conn);
    }

    public static void init() throws SQLException {
        if (initialized) return;
        CommentQueries.initQueries();
        BlogQueries.initQueries();
        ChatQueries.initQueries();
        ReportQueries.initQueries();
        UserQueries.initQueries();
        initialized = true;
    }


    public static void closeConnection() throws SQLException {
        CONNECTION.close();
    }

}
