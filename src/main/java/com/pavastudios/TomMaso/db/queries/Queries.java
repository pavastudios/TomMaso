package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Queries {

    static MasterPreparedStatement FIND_USER_BY_USERNAME;
    static MasterPreparedStatement FIND_USER_BY_ID;
    static MasterPreparedStatement FIND_PAGE_BY_ID;
    static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement FIND_COMMENT_BY_ID;
    static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    static MasterPreparedStatement FIND_CHAT_BY_ID;
    static MasterPreparedStatement FIND_USER_BY_COOKIE;


    public static void initQueries() throws SQLException {
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_PAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Pagina` WHERE `id_pagina`=?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_USER_BY_COOKIE = GlobalConnection.CONNECTION.prepareStatement("SELECT `id_utente` FROM `RememberMe` WHERE `cookie`=?");
    }

    @SuppressWarnings("all")
    private static <T> @Nullable T findSomethingById(Entities entity, int id) throws SQLException {
        entity.findByIdStmt.setInt(1, id);
        ResultSet rs = entity.findByIdStmt.executeQuery();
        Object result = null;
        if (rs.first()) {
            try {
                result = entity.entityClass.getMethod("fromResultSet", ResultSet.class).invoke(null, rs);
            } catch (Exception ignore) {
            }
        }
        rs.close();
        return (T) result;

    }

    //Query Utente
    public static @Nullable
    Utente findUserByUsername(@NotNull String username) throws SQLException {
        FIND_USER_BY_USERNAME.setString(1, username);
        ResultSet rs = FIND_USER_BY_USERNAME.executeQuery();
        Utente user = null;
        if (rs.first())
            user = Utente.fromResultSet(rs);
        rs.close();
        return user;
    }

    public static @Nullable
    Utente findUserById(int idUtente) throws SQLException {
        return findSomethingById(Entities.UTENTE, idUtente);
    }

    //Query Pagina
    public static Pagina findPageById(int idPagina) throws SQLException {
        return findSomethingById(Entities.PAGINA, idPagina);
    }

    //Query Blog
    public static @Nullable
    Blog findBlogById(int idBlog) throws SQLException {
        return findSomethingById(Entities.BLOG, idBlog);
    }

    //Query Commento
    public static @Nullable
    Commento findCommentById(int idCommento) throws SQLException {
        return findSomethingById(Entities.COMMENTO, idCommento);
    }

    //Query Messaggio
    public static @Nullable
    Messaggio findMessageById(int idMessaggio) throws SQLException {
        return findSomethingById(Entities.MESSAGGIO, idMessaggio);
    }

    //Query Chat
    public static @Nullable
    Chat findChatById(int idChat) throws SQLException {
        return findSomethingById(Entities.CHAT, idChat);
    }

    //Query RememberMe
    public static @Nullable
    Utente findUserByCookie(byte[] cookie) throws SQLException {
        FIND_USER_BY_COOKIE.setBytes(1, cookie);
        ResultSet rs = FIND_USER_BY_COOKIE.executeQuery();
        int id = -1;
        if (rs.first())
            id = rs.getInt("id_utente");
        rs.close();
        return id == -1 ? null : findUserById(id);
    }
}
