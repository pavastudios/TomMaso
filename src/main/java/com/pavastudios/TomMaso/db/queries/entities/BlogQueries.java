package com.pavastudios.TomMaso.db.queries.entities;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BlogQueries {
    public static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement CREATE_BLOG;
    static MasterPreparedStatement FIND_BLOGS_OWNED_BY;
    static MasterPreparedStatement FIND_BLOG_BY_NAME;
    static MasterPreparedStatement DELETE_BLOG;
    static MasterPreparedStatement UPDATE_BLOG_NAME;
    static MasterPreparedStatement BLOG_INCREMENT;
    static MasterPreparedStatement TOP_BLOG;


    public static void initQueries() throws SQLException {
        TOP_BLOG = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` ORDER BY `visite` DESC LIMIT ?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_BLOGS_OWNED_BY = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `proprietario`=? ORDER BY `nome`");
        CREATE_BLOG = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Blog`(`proprietario`,`nome`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        BLOG_INCREMENT = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `visite`=`visite`+1 WHERE `id_blog`=?");
        FIND_BLOG_BY_NAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `nome`=?");
        DELETE_BLOG = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Blog` WHERE `id_blog`=?");
        UPDATE_BLOG_NAME = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `nome`=? WHERE `id_blog`=?");
    }

    public static List<Blog> topBlogs(int count) throws SQLException {
        TOP_BLOG.setInt(1, count);
        ResultSet rs = TOP_BLOG.executeQuery();
        List<Blog> blogs = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogs;
    }

    public static List<Blog> getBlogsUser(Utente u) throws SQLException {
        FIND_BLOGS_OWNED_BY.setInt(1, u.getIdUtente());
        ResultSet rs = FIND_BLOGS_OWNED_BY.executeQuery();
        List<Blog> blogList = Queries.resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogList;
    }

    public static Blog createBlog(Utente utente, String nome) throws SQLException {
        CREATE_BLOG.setInt(1, utente.getIdUtente());
        CREATE_BLOG.setString(2, nome);
        CREATE_BLOG.executeUpdate();
        int idBlog = Utility.getIdFromGeneratedKeys(CREATE_BLOG);
        return findBlogById(idBlog);
    }

    public static void incrementVisit(Blog blog) throws SQLException {
        if (blog == null) return;
        BLOG_INCREMENT.setInt(1, blog.getIdBlog());
        BLOG_INCREMENT.executeUpdate();
    }

    public static @Nullable Blog findBlogByName(@NotNull String name) throws SQLException {
        FIND_BLOG_BY_NAME.setString(1, name);
        ResultSet rs = FIND_BLOG_BY_NAME.executeQuery();
        Blog blog = null;
        if (rs.first())
            blog = Blog.fromResultSet(rs);
        rs.close();
        return blog;
    }

    public static void deleteBlog(Blog blog) throws SQLException {
        DELETE_BLOG.setInt(1, blog.getIdBlog());
        DELETE_BLOG.executeUpdate();
    }

    //Query Blog
    public static @Nullable Blog findBlogById(int idBlog) throws SQLException {
        return Queries.findById(Entities.BLOG, idBlog);
    }


}
