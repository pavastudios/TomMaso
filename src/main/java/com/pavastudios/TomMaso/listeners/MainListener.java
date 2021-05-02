package com.pavastudios.TomMaso.listeners;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class MainListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            System.out.println("Connecting to DB...");
            Class.forName("org.mariadb.jdbc.Driver");
            GlobalConnection.init();
            System.out.println("Successfully connected to DB");
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("Disconnecting the DB...");
            GlobalConnection.closeConnection();
            System.out.println("Disconnected from DB");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
