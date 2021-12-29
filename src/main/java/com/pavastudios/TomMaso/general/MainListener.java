package com.pavastudios.TomMaso.general;

import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.FileUtility;
import com.pavastudios.TomMaso.storage.GlobalConnection;
import com.pavastudios.TomMaso.storage.model.Utente;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * Classe per l'inizializzazione e distruzione del context
 */
@WebListener
public class MainListener implements ServletContextListener {
    public static ServletContext CONTEXT;
    public static final String ADMIN_PASSWORD = "admin";
    public static final String ADMIN_USERNAME = "admin";

    /**
     * Metodo per l'inizializzazione del contesto
     * @param sce oggetto ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CONTEXT = sce.getServletContext();
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

    /**
     * Metodo per la creazione dell'account admin del sito
     */
    private void createAdminAccount() throws SQLException {
        Utente u = UserQueries.findUserByUsername(ADMIN_USERNAME);
        if (u == null) {
            Utente admin = UserQueries.registerUser(ADMIN_PASSWORD, ADMIN_USERNAME);
            UserQueries.changePermissions(admin, new Utente.Permessi(Utente.Permessi.MANAGE_USER|Utente.Permessi.CAN_LOGIN));
        }
    }

    /**
     * Metodo per la distruzione del contesto
     * @param sce oggetto ServletContextEvent
     */
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
