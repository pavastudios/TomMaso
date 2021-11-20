package com.pavastudios.TomMaso.control.global;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.entities.CommentQueries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class ViewerServlet extends MasterServlet {

    private void manageMarkdown(Session session, HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException, SQLException {
        Blog blog = Blog.fromPathInfo(req.getPathInfo());
        session.visitedBlog(blog);
        req.setAttribute("file", file);
        req.setAttribute("blog", blog);
        req.setAttribute("comments", CommentQueries.fetchCommentsFromPage(req.getPathInfo()));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/markdownViewer.jsp").forward(req, resp);
    }

    private void manageFile(HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException {

        OutputStream out = resp.getOutputStream();
        FileInputStream fr = new FileInputStream(file);
        resp.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
        //resp.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        FileUtility.writeFile(fr, out);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        File file = null;
        String pathInfo = req.getPathInfo();
        switch (req.getServletPath()) {
            case "/users":
                file = FileUtility.userPathToFile(pathInfo);
                break;
            case "/blogs":
                file = FileUtility.blogPathToFile(pathInfo);
                break;
        }

        if (file == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File invalido");
            return;
        }
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File non trovato");
            return;
        }
        if (file.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Impossibile aprire cartelle");
            return;
        }
        FileUtility.FileType type = FileUtility.getFileType(getServletContext(), file);
        if (type == FileUtility.FileType.MARKDOWN)
            manageMarkdown(session, req, resp, file);
        else
            manageFile(req, resp, file);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
