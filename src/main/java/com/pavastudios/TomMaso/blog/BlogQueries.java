package com.pavastudios.TomMaso.blog;

import com.pavastudios.TomMaso.storage.Entities;
import com.pavastudios.TomMaso.storage.GlobalConnection;
import com.pavastudios.TomMaso.storage.MasterPreparedStatement;
import com.pavastudios.TomMaso.storage.Queries;
import com.pavastudios.TomMaso.storage.model.Blog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe BlogQueries contiene i metodi per l'interazione con il database relativi ai Blog
 */
public class BlogQueries {
    public static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement FIND_BLOG_BY_NAME;

    /**
     * Inizializza le prepared statements contenenti le query relative ai blog
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_BLOG_BY_NAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `nome`=?");
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
     * Esegue la query che recupera il blog tramite id dato in input
     * @param idBlog Id da cercare
     * @return Blog trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Blog findBlogById(int idBlog) throws SQLException {
        return Queries.findById(Entities.BLOG, idBlog);
    }

}
