package com.pavastudios.TomMaso.storage;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class GlobalConnection {
    public static MasterConnection CONNECTION;
    private static boolean initialized = false;

    private static void startConnection(Connection fakeDB) {
        Connection conn = fakeDB;
        if (conn == null) {
            try {
                Context initCtx = new InitialContext();
                Context encCtx = (Context) initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource) encCtx.lookup("jdbc/tommaso");
                conn = ds.getConnection();
            } catch (SQLException | NamingException e) {
                e.printStackTrace();
            }
        }
        CONNECTION = new MasterConnection(conn);
        initialized = true;
    }

    /**
     * questo metodo inizializza le queries
     */

    public static void init(Connection fakeDB) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (initialized) return;
        startConnection(fakeDB);
        Reflections reflections = new Reflections("com.pavastudios.TomMaso", Scanners.MethodsAnnotated);
        Set<Method> apiEndpoints = reflections.getMethodsAnnotatedWith(QueryInitializer.class);
        for (Method method : apiEndpoints) {
            method.setAccessible(true);
            method.invoke(null);
        }
    }


    /**
     * chiede la connesisone al database
     *
     * @throws SQLException
     */

    public static void closeConnection() throws SQLException {
        CONNECTION.close();
    }

}
