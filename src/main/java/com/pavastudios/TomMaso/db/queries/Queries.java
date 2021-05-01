package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Pagina;
import com.pavastudios.TomMaso.model.Utente;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Queries {

    private static MasterPreparedStatement FIND_USER_BY_USERNAME;
    private static MasterPreparedStatement FIND_USER_BY_ID;
    private static MasterPreparedStatement FIND_PAGE_BY_ID;
    private static MasterPreparedStatement FIND_BLOG_BY_ID;


    public static void initQueries()throws SQLException{
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_PAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Pagina` WHERE `id_pagina`=?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");

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
    public static @Nullable
    Pagina findPageById(int idPagina)throws SQLException {
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
}
