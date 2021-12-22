package com.pavastudios.TomMaso.db.queries.entities;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Security;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * La classe UserQueries contiene i metodi per l'interazione con il database relativi agli utenti
 */
public class UserQueries {
    public static MasterPreparedStatement FIND_USER_BY_ID;
    static MasterPreparedStatement FIND_USER_BY_USERNAME;
    static MasterPreparedStatement REGISTER_USER;
    static MasterPreparedStatement CHANGE_ROLE_USER;

    /**
     * Inizializza le prepared statements contenenti le query relative ai commenti
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        REGISTER_USER = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Utente`(`password`,`username`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        CHANGE_ROLE_USER = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `permessi`=? WHERE `id_utente`=?");
    }

    /**
     * Esegue la query che recupera un utente tramite l'username
     * @param username Username da cercare
     * @return Utente trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Utente findUserByUsername(@NotNull String username) throws SQLException {
        FIND_USER_BY_USERNAME.setString(1, username);
        ResultSet rs = FIND_USER_BY_USERNAME.executeQuery();
        Utente user = null;
        if (rs.first())
            user = Utente.fromResultSet(rs);
        rs.close();
        return user;
    }

    /**
     * Esegue la query che recupera un utente tramite l'id
     * @param idUtente Id da cercare
     * @return Utente trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Utente findUserById(int idUtente) throws SQLException {
        return Queries.findById(Entities.UTENTE, idUtente);
    }

    /**
     * Esegue la query che registra un utente
     * @param password Password del nuovo utente
     * @param username Username del password
     * @return Utente creato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Utente registerUser(String password, String username) throws SQLException {
        REGISTER_USER.setString(1, Security.crypt(password));
        REGISTER_USER.setString(2, username);
        REGISTER_USER.executeUpdate();
        int newId = Utility.getIdFromGeneratedKeys(REGISTER_USER);
        return findUserById(newId);
    }

    /**
     * Esegue la query che cambia i permessi di un utente
     * @param u Utente di cui cambiare i permessi
     * @param permessi Nuovi permessi
     * @throws SQLException Problemi con il database
     */
    public static void changeRole(Utente u, Utente.Permessi permessi) throws SQLException {
        if (u == null) return;
        CHANGE_ROLE_USER.setInt(1, permessi.getPermessi());
        CHANGE_ROLE_USER.setInt(2, u.getIdUtente());
        CHANGE_ROLE_USER.executeUpdate();
    }

    /**
     * Esegue la query che rende un gestore degli utenti
     * @param u Utente di cui cambiare i permessi
     * @param admin Valore booleano per impostare velocemente un amministratore
     * @throws SQLException Problemi con il database
     */
    public static void changeRole2(Utente u, boolean admin) throws SQLException {
        if (u == null) return;
        CHANGE_ROLE_USER.setInt(1, admin ? 1 : 0);
        CHANGE_ROLE_USER.setInt(2, u.getIdUtente());
        CHANGE_ROLE_USER.executeUpdate();
    }
}
