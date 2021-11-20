package com.pavastudios.TomMaso.listeners;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.FileUtility;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class MainListener implements ServletContextListener {
    public static ServletContext CONTEXT;
    public static String ADMIN_PASSWORD = "admin";
    public static String ADMIN_USERNAME = "admin";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CONTEXT = sce.getServletContext();
        FileUtility.USER_FILES_FOLDER.mkdirs();
        FileUtility.BLOG_FILES_FOLDER.mkdir();
        FileUtility.TMP_FOLDER.mkdir();
        try {
            System.out.println("Connecting to DB...");
            Class.forName("org.mariadb.jdbc.Driver");
            GlobalConnection.init();
            createAdminAccount();
            System.out.println("Successfully connected to DB");
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

    private void createAdminAccount() throws SQLException {
        Utente u = UserQueries.findUserByUsername(ADMIN_USERNAME);
        if (u == null) {
            Utente admin = UserQueries.registerUser(ADMIN_PASSWORD, ADMIN_USERNAME);
            UserQueries.changeRole(admin, new Utente.Permessi(Utente.Permessi.MANAGE_USER));
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
