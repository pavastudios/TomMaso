package com.pavastudios.TomMaso.blogsystem;

import com.pavastudios.TomMaso.storagesystem.Entities;
import com.pavastudios.TomMaso.storagesystem.GlobalConnection;
import com.pavastudios.TomMaso.storagesystem.MasterPreparedStatement;
import com.pavastudios.TomMaso.storagesystem.Queries;
import com.pavastudios.TomMaso.storagesystem.model.Blog;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * La classe BlogQueries contiene i metodi per l'interazione con il database relativi ai Blog
 */
public class BlogQueries {
    public static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement CREATE_BLOG;
    static MasterPreparedStatement FIND_BLOGS_OWNED_BY;
    static MasterPreparedStatement FIND_BLOG_BY_NAME;
    static MasterPreparedStatement DELETE_BLOG;
    static MasterPreparedStatement BLOG_INCREMENT;
    static MasterPreparedStatement TOP_BLOG;

    /**
     * Inizializza le prepared statements contenenti le query relative ai blog
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        TOP_BLOG = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` ORDER BY `visite` DESC LIMIT ?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_BLOGS_OWNED_BY = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `proprietario`=? ORDER BY `nome`");
        CREATE_BLOG = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Blog`(`proprietario`,`nome`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        BLOG_INCREMENT = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `visite`=`visite`+1 WHERE `id_blog`=?");
        FIND_BLOG_BY_NAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `nome`=?");
        DELETE_BLOG = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Blog` WHERE `id_blog`=?");
    }

    /**
     * Esegue la query che restituisce una lista di blog ordinati per numero di visite
     * @param count Lunghezza della lista resituita
     * @return Lista dei blog con più visite
     * @throws SQLException Problemi con il database
     */
    public static List<Blog> topBlogs(int count) throws SQLException {
        TOP_BLOG.setInt(1, count);
        ResultSet rs = TOP_BLOG.executeQuery();
        List<Blog> blogs = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogs;
    }

    /**
     * Esegue la query che restituisce la lista dei blog appartenenti all'utente dato in input
     * @param u Utente di cui si vuole recuperare i blog
     * @return Lista dei blog appartenenti all'utente u
     * @throws SQLException Problemi con il database
     */
    public static List<Blog> getBlogsUser(Utente u) throws SQLException {
        FIND_BLOGS_OWNED_BY.setInt(1, u.getIdUtente());
        ResultSet rs = FIND_BLOGS_OWNED_BY.executeQuery();
        List<Blog> blogList = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogList;
    }

    /**
     * Esegue la query che crea un blog per un utente
     * @param utente Utente proprietario del blog
     * @param nome Nome del nuovo blog
     * @return Blog appena creato
     * @throws SQLException Problemi con il database
     */
    public static Blog createBlog(Utente utente, String nome) throws SQLException {
        CREATE_BLOG.setInt(1, utente.getIdUtente());
        CREATE_BLOG.setString(2, nome);
        CREATE_BLOG.executeUpdate();
        int idBlog = Utility.getIdFromGeneratedKeys(CREATE_BLOG);
        return findBlogById(idBlog);
    }

    /**
     * Esegue la query che incrementa il numero di visite di un blog
     * @param blog Blog di cui aggiornare le visite
     * @throws SQLException Problemi con il database
     */
    public static void incrementVisit(Blog blog) throws SQLException {
        if (blog == null) return;
        BLOG_INCREMENT.setInt(1, blog.getIdBlog());
        BLOG_INCREMENT.executeUpdate();
    }

    /**
     * Esegue la query che recupera un blog tramite nome
     * @param name Nome da cercare
     * @return Blog trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Blog findBlogByName(@NotNull String name) throws SQLException {
        FIND_BLOG_BY_NAME.setString(1, name);
        ResultSet rs = FIND_BLOG_BY_NAME.executeQuery();
        Blog blog = null;
        if (rs.first())
            blog = Blog.fromResultSet(rs);
        rs.close();
        return blog;
    }

    /**
     * Esegue la query che elimina il blog
     * @param blog Blog da eliminare
     * @throws SQLException Problemi con il database
     */
    public static void deleteBlog(Blog blog) throws SQLException {
        DELETE_BLOG.setInt(1, blog.getIdBlog());
        DELETE_BLOG.executeUpdate();
    }

    /**
     * Esegue la query che recupera il blog tramite id dato in input
     * @param idBlog Id da cercare
     * @return Blog trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Blog findBlogById(int idBlog) throws SQLException {
        return Queries.findById(Entities.BLOG, idBlog);
    }


}
