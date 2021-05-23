package com.pavastudios.TomMaso.servlets.blog;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TopBlogServlet extends MasterServlet {
    public static final int TOP_COUNT=15;
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Blog> blogs= Queries.topBlogs(TOP_COUNT);
        req.setAttribute("blogs",blogs);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/topBlogs.jsp").forward(req,resp);
    }
}
