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
import java.util.List;

public class UserQueries {
    public static MasterPreparedStatement FIND_USER_BY_ID;
    static MasterPreparedStatement FIND_USER_BY_USERNAME;
    static MasterPreparedStatement FIND_USER_BY_COOKIE;
    static MasterPreparedStatement REGISTER_USER;
    static MasterPreparedStatement REGISTER_REMEMBER_ME;
    static MasterPreparedStatement DELETE_REMEMBER_ME;
    static MasterPreparedStatement FIND_USER_BY_EMAIL;
    static MasterPreparedStatement CHANGE_PASSWORD;
    static MasterPreparedStatement DELETE_FORGET;
    static MasterPreparedStatement UPDATE_USER_DATA;
    static MasterPreparedStatement CHANGE_ROLE_USER;
    static MasterPreparedStatement FIND_ALL_USERS;
    static MasterPreparedStatement FETCH_ADMIN;
    static MasterPreparedStatement FIND_ALL_ADMINS;

    public static void initQueries() throws SQLException {
        FETCH_ADMIN = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `permessi`=1 ORDER BY `username`");
        FIND_ALL_ADMINS = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `permessi`=1");
        FIND_ALL_USERS = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente`");
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_EMAIL = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `email`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_USER_BY_COOKIE = GlobalConnection.CONNECTION.prepareStatement("SELECT `id_utente` FROM `RememberMe` WHERE `cookie`=?");
        REGISTER_USER = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Utente`(`email`,`password`,`username`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        REGISTER_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `RememberMe`(`id_utente`,`cookie`) VALUES (?,?)");
        DELETE_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `RememberMe` WHERE `cookie`=?");
        CHANGE_PASSWORD = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `password`=? WHERE `id_utente`=?");
        DELETE_FORGET = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `PasswordReset` WHERE `codice`=?");
        DELETE_FORGET = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `PasswordReset` WHERE `codice`=?");
        UPDATE_USER_DATA = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `username`=?,`bio`=? WHERE `id_utente`=?");
        CHANGE_ROLE_USER = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `permessi`=? WHERE `id_utente`=?");
    }


    public static void removeCookie(String cookie) throws SQLException {
        byte[] bytes = Utility.fromHexString(cookie);
        DELETE_REMEMBER_ME.setBytes(1, bytes);
        DELETE_REMEMBER_ME.executeUpdate();
    }

    //Query Utente
    public static @Nullable Utente findUserByUsername(@NotNull String username) throws SQLException {
        FIND_USER_BY_USERNAME.setString(1, username);
        ResultSet rs = FIND_USER_BY_USERNAME.executeQuery();
        Utente user = null;
        if (rs.first())
            user = Utente.fromResultSet(rs);
        rs.close();
        return user;
    }

    public static @Nullable Utente findUserById(int idUtente) throws SQLException {
        return Queries.findById(Entities.UTENTE, idUtente);
    }

    public static @Nullable Utente registerUser(String email, String password, String username) throws SQLException {
        REGISTER_USER.setString(1, email);
        REGISTER_USER.setString(2, Security.crypt(password));
        REGISTER_USER.setString(3, username);
        REGISTER_USER.executeUpdate();
        int newId = Utility.getIdFromGeneratedKeys(REGISTER_USER);
        return findUserById(newId);
    }

    //Query RememberMe
    public static @Nullable Utente findUserByCookie(String cookie) throws SQLException {
        if (cookie == null) return null;
        byte[] bytes = Utility.fromHexString(cookie);
        FIND_USER_BY_COOKIE.setBytes(1, bytes);
        ResultSet rs = FIND_USER_BY_COOKIE.executeQuery();
        int id = -1;
        if (rs.first())
            id = rs.getInt("id_utente");
        rs.close();
        return id == -1 ? null : findUserById(id);
    }

    public static @Nullable byte[] registerRememberMe(Utente u) throws SQLException {
        byte[] cookie = Utility.generateRememberMeCookie();
        REGISTER_REMEMBER_ME.setInt(1, u.getIdUtente());
        REGISTER_REMEMBER_ME.setBytes(2, cookie);
        REGISTER_REMEMBER_ME.executeUpdate();
        return cookie;
    }

    public static void changeRole(Utente u, Utente.Permessi permessi) throws SQLException {
        if (u == null) return;
        CHANGE_ROLE_USER.setInt(1, permessi.getPermessi());
        CHANGE_ROLE_USER.setInt(2, u.getIdUtente());
        CHANGE_ROLE_USER.executeUpdate();
    }

    public static List<Utente> findAllAdmins() throws SQLException {
        ResultSet set = FIND_ALL_ADMINS.executeQuery();
        List<Utente> u = Queries.resultSetToList(Entities.UTENTE, set);
        return u;
    }

    public static void changeRole2(Utente u, boolean admin) throws SQLException {
        if (u == null) return;
        CHANGE_ROLE_USER.setInt(1, admin ? 1 : 0);
        CHANGE_ROLE_USER.setInt(2, u.getIdUtente());
        CHANGE_ROLE_USER.executeUpdate();
    }
}
