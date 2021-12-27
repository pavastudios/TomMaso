package com.pavastudios.TomMaso.blog.visualization.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe per l'ottenimento e la visualizzazione dei blog più popolari
 */
public class TopBlogServlet extends MasterServlet {
    public static final int TOP_COUNT = 15;

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Blog> blogs = BlogQueries.topBlogs(TOP_COUNT);
        req.setAttribute("blogs", blogs);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/topBlogs.jsp").forward(req, resp);
    }
}
