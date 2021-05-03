package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.*;
import com.pavastudios.TomMaso.utility.Security;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Queries {

    static MasterPreparedStatement FIND_USER_BY_USERNAME;
    static MasterPreparedStatement FIND_USER_BY_ID;
    static MasterPreparedStatement FIND_PAGE_BY_ID;
    static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement FIND_COMMENT_BY_ID;
    static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    static MasterPreparedStatement FIND_CHAT_BY_ID;
    static MasterPreparedStatement FIND_USER_BY_COOKIE;
    static MasterPreparedStatement REGISTER_USER;
    static MasterPreparedStatement USER_VERIFY_LOGIN;
    static MasterPreparedStatement REGISTER_REMEMBER_ME;
    static MasterPreparedStatement DELETE_REMEMBER_ME;


    public static void initQueries() throws SQLException {
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_PAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Pagina` WHERE `id_pagina`=?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_USER_BY_COOKIE = GlobalConnection.CONNECTION.prepareStatement("SELECT `id_utente` FROM `RememberMe` WHERE `cookie`=?");
        REGISTER_USER = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Utente`(`email`,`password`,`salt`,`username`) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        REGISTER_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `RememberMe`(`id_utente`,`cookie`) VALUES (?,?)");


        DELETE_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `RememberMe` WHERE `cookie`=?");
    }

    public static void removeCookie(String cookie) throws SQLException {
        byte[] bytes = Utility.fromHexString(cookie);
        DELETE_REMEMBER_ME.setBytes(1, bytes);
        DELETE_REMEMBER_ME.executeUpdate();
    }

    @SuppressWarnings("all")
    private static <T> @Nullable T resultSetToModel(Entities entity, ResultSet rs) {
        T result = null;
        try {
            result = (T) entity.entityClass.getMethod("fromResultSet", ResultSet.class).invoke(null, rs);
        } catch (Exception ignore) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T findById(Entities entity, int id) throws SQLException {
        entity.findByIdStmt.setInt(1, id);
        ResultSet rs = entity.findByIdStmt.executeQuery();
        Object result = null;
        if (rs.first())
            result = resultSetToModel(entity, rs);
        rs.close();
        return (T) result;
    }

    @SuppressWarnings("all")
    public static <T> @NotNull List<T> resultSetToList(Entities entity, ResultSet rs) throws SQLException {
        ArrayList<T> list = new ArrayList<>();
        if (rs.first()) {
            do {
                T result = resultSetToModel(entity, rs);
                list.add(result);
            } while (rs.next());
        }
        list.trimToSize();
        return list;
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
        return findById(Entities.UTENTE, idUtente);
    }

    public static @Nullable Utente registerUser(String email, String password, String username) throws SQLException {
        byte[] salt = Security.generateSalt();
        byte[] pwd = Security.sha512(password, salt);
        REGISTER_USER.setString(1, email);
        REGISTER_USER.setBytes(2, pwd);
        REGISTER_USER.setBytes(3, salt);
        REGISTER_USER.setString(4, username);
        REGISTER_USER.executeUpdate();
        int newId = Utility.getIdFromGeneratedKeys(REGISTER_USER);
        return findUserById(newId);
    }

    public static @Nullable Utente login(String username, String password) throws SQLException {
        Utente u = findUserByUsername(username);
        if (u == null) return null;
        return u.userVerifyLogin(password);
    }

    //Query Pagina
    public static @Nullable Pagina findPageById(int idPagina) throws SQLException {
        return findById(Entities.PAGINA, idPagina);
    }

    //Query Blog
    public static @Nullable Blog findBlogById(int idBlog) throws SQLException {
        return findById(Entities.BLOG, idBlog);
    }

    //Query Commento
    public static @Nullable Commento findCommentById(int idCommento) throws SQLException {
        return findById(Entities.COMMENTO, idCommento);
    }

    //Query Messaggio
    public static @Nullable Messaggio findMessageById(int idMessaggio) throws SQLException {
        return findById(Entities.MESSAGGIO, idMessaggio);
    }

    //Query Chat
    public static @Nullable Chat findChatById(int idChat) throws SQLException {
        return findById(Entities.CHAT, idChat);
    }

    //Query RememberMe
    public static @Nullable Utente findUserByCookie(String cookie) throws SQLException {
        if(cookie==null)return null;
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

}
