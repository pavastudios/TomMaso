package com.pavastudios.TomMaso.blog.visualization;

import com.pavastudios.TomMaso.storage.*;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.storage.model.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class BlogVisualizationQueries {
    static MasterPreparedStatement FIND_BLOGS_OWNED_BY;
    static MasterPreparedStatement BLOG_INCREMENT;
    static MasterPreparedStatement TOP_BLOG;

    /**
     * Inizializza le prepared statements contenenti le query relative ai blog
     *
     * @throws SQLException Problemi con il database
     */
    @QueryInitializer
    public static void initQueries() throws SQLException {
        TOP_BLOG = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` ORDER BY `visite` DESC LIMIT ?");
        FIND_BLOGS_OWNED_BY = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `proprietario`=? ORDER BY `nome`");
        BLOG_INCREMENT = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `visite`=`visite`+1 WHERE `id_blog`=?");
    }

    /**
     * Esegue la query che restituisce una lista di blog ordinati per numero di visite
     *
     * @param count Lunghezza della lista resituita
     * @return Lista dei blog con più visite
     * @throws SQLException Problemi con il database
     */
    public static List<Blog> topBlogs(int count) throws SQLException {
        count = Math.max(0, count);
        TOP_BLOG.setInt(1, count);
        ResultSet rs = TOP_BLOG.executeQuery();
        List<Blog> blogs = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogs;
    }

    /**
     * Esegue la query che restituisce la lista dei blog appartenenti all'utente dato in input
     *
     * @param u Utente di cui si vuole recuperare i blog
     * @return Lista dei blog appartenenti all'utente u
     * @throws SQLException Problemi con il database
     */
    public static List<Blog> getBlogsUser(Utente u) throws SQLException {
        if (u == null) return Collections.emptyList();
        FIND_BLOGS_OWNED_BY.setInt(1, u.getIdUtente());
        ResultSet rs = FIND_BLOGS_OWNED_BY.executeQuery();
        List<Blog> blogList = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogList;
    }

    /**
     * Esegue la query che incrementa il numero di visite di un blog
     *
     * @param blog Blog di cui aggiornare le visite
     * @throws SQLException Problemi con il database
     */
    public static void incrementVisit(Blog blog) throws SQLException {
        if (blog == null) return;
        BLOG_INCREMENT.setInt(1, blog.getIdBlog());
        BLOG_INCREMENT.executeUpdate();
    }

}
