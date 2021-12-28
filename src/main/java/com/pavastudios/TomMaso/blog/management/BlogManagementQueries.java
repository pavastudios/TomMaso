package com.pavastudios.TomMaso.blog.management;

import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.storage.GlobalConnection;
import com.pavastudios.TomMaso.storage.MasterPreparedStatement;
import com.pavastudios.TomMaso.storage.Queries;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.storage.model.Utente;

import java.sql.SQLException;
import java.sql.Statement;

public class BlogManagementQueries {
    static MasterPreparedStatement CREATE_BLOG;
    static MasterPreparedStatement DELETE_BLOG;

    /**
     * Inizializza le prepared statements contenenti le query relative ai blog
     *
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        CREATE_BLOG = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Blog`(`proprietario`,`nome`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        DELETE_BLOG = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Blog` WHERE `id_blog`=?");
    }


    /**
     * Esegue la query che crea un blog per un utente
     *
     * @param utente Utente proprietario del blog
     * @param nome   Nome del nuovo blog
     * @return Blog appena creato
     * @throws SQLException Problemi con il database
     */
    public static Blog createBlog(Utente utente, String nome) throws SQLException {
        CREATE_BLOG.setInt(1, utente.getIdUtente());
        CREATE_BLOG.setString(2, nome);
        CREATE_BLOG.executeUpdate();
        int idBlog = Queries.getIdFromGeneratedKeys(CREATE_BLOG);
        return BlogQueries.findBlogById(idBlog);
    }


    /**
     * Esegue la query che elimina il blog
     *
     * @param blog Blog da eliminare
     * @throws SQLException Problemi con il database
     */
    public static void deleteBlog(Blog blog) throws SQLException {
        DELETE_BLOG.setInt(1, blog.getIdBlog());
        DELETE_BLOG.executeUpdate();
    }


}
