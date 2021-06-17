package com.pavastudios.TomMaso.control.blog;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MDEditorServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String pathInfo = req.getPathInfo();
        Blog blog = Blog.fromPathInfo(pathInfo);
        if (blog == null || !blog.hasAccess(session.getUtente())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Blog inaccessibile");
            return;
        }
        File file = FileUtility.blogPathToFile(pathInfo);
        if (!file.getParentFile().exists()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Percorso non valido");
            return;
        }
        if (file.exists() && FileUtility.getFileType(req.getServletContext(), file) != FileUtility.FileType.MARKDOWN) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File non valido");
            return;
        }
        req.setAttribute("file", file);
        req.setAttribute("path", pathInfo);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/markdownEditor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}