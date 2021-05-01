package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Queries {

    private static MasterPreparedStatement FIND_USER_BY_USERNAME;
    private static MasterPreparedStatement FIND_USER_BY_ID;
    private static MasterPreparedStatement FIND_PAGE_BY_ID;
    private static MasterPreparedStatement FIND_BLOG_BY_ID;
    private static MasterPreparedStatement FIND_COMMENT_BY_ID;
    private static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    private static MasterPreparedStatement FIND_CHAT_BY_ID;
    private static MasterPreparedStatement FIND_USER_BY_COOKIE;


    public static void initQueries()throws SQLException{
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_PAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Pagina` WHERE `id_pagina`=?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_USER_BY_COOKIE = GlobalConnection.CONNECTION.prepareStatement("SELECT `id_utente` FROM `RememberMe` WHERE `cookie`=?");
    }

    //Query Utente
    public static @Nullable
    Utente findUserByUsername(@NotNull String username)throws SQLException {
        FIND_USER_BY_USERNAME.setString(1,username);
        ResultSet rs=FIND_USER_BY_USERNAME.executeQuery();
        if(!rs.first())return null;
        Utente user = Utente.getUtente(rs);
        rs.close();
        return user;
    }

    public static @Nullable
    Utente findUserById(int idUtente)throws SQLException {
        FIND_USER_BY_ID.setInt(1,idUtente);
        ResultSet rs=FIND_USER_BY_ID.executeQuery();
        if(!rs.first())return null;
        Utente user = Utente.getUtente(rs);
        rs.close();
        return user;
    }

    //Query Pagina
    public static Pagina findPageById(int idPagina)throws SQLException {
        FIND_PAGE_BY_ID.setInt(1,idPagina);
        ResultSet rs=FIND_PAGE_BY_ID.executeQuery();
        if(!rs.first())return null;
        Pagina page = Pagina.getPagina(rs);
        rs.close();
        return page;
    }

    //Query Blog
    public static @Nullable
    Blog findBlogById(int idBlog)throws SQLException {
        FIND_BLOG_BY_ID.setInt(1,idBlog);
        ResultSet rs=FIND_BLOG_BY_ID.executeQuery();
        if(!rs.first())return null;
        Blog blog = Blog.getBlog(rs);
        rs.close();
        return blog;
    }

    //Query Commento
    public static @Nullable
    Commento findCommentById(int idCommento)throws SQLException {
        FIND_COMMENT_BY_ID.setInt(1,idCommento);
        ResultSet rs=FIND_COMMENT_BY_ID.executeQuery();
        if(!rs.first())return null;
        Commento comment = Commento.getCommento(rs);
        rs.close();
        return comment;
    }

    //Query Messaggio
    public static @Nullable
    Messaggio findMessageById(int idMessaggio)throws SQLException {
        FIND_MESSAGE_BY_ID.setInt(1,idMessaggio);
        ResultSet rs=FIND_MESSAGE_BY_ID.executeQuery();
        if(!rs.first())return null;
        Messaggio message = Messaggio.getMessaggio(rs);
        rs.close();
        return message;
    }

    //Query Chat
    public static @Nullable
    Chat findChatById(int idChat)throws SQLException {
        FIND_CHAT_BY_ID.setInt(1,idChat);
        ResultSet rs=FIND_CHAT_BY_ID.executeQuery();
        if(!rs.first())return null;
        Chat chat = Chat.getChat(rs);
        rs.close();
        return chat;
    }

    //Query RememberMe
    public static @Nullable
    Utente findUserByCookie(byte[] cookie)throws SQLException {
        FIND_USER_BY_COOKIE.setBytes(1,cookie);
        ResultSet rs=FIND_USER_BY_COOKIE.executeQuery();
        if(!rs.first())return null;
        int id = rs.getInt("id_utente");
        rs.close();
        return findUserById(id);
    }
}
