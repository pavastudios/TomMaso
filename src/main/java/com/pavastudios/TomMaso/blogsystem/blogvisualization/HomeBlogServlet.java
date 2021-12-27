package com.pavastudios.TomMaso.blogsystem.blogvisualization;

import com.pavastudios.TomMaso.accesssystem.Session;
import com.pavastudios.TomMaso.storagesystem.FileUtility;
import com.pavastudios.TomMaso.storagesystem.model.Blog;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * CLasse che preleva i file di un blog e li renderizza in una homepage
 */
public class HomeBlogServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Blog blog = Blog.fromPathInfo(req.getPathInfo());
        List<File> markdowns = FileUtility.getPages(getServletContext(), blog);
        if (markdowns == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Blog invalido");
            return;
        }
        req.setAttribute("pages", markdowns);
        req.setAttribute("blog", blog);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/lastPosts.jsp").forward(req, resp);
    }
}
