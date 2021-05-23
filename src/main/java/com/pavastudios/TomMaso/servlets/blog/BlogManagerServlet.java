package com.pavastudios.TomMaso.servlets.blog;

import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class BlogManagerServlet extends MasterServlet {

    private void manageFolder(HttpServletRequest req, HttpServletResponse resp, File file) throws SQLException, ServletException, IOException {
        File[] files = file.listFiles();
        if (files == null) files = new File[0];
        Arrays.sort(files, (o1, o2) -> {
            if (o1.isDirectory() && !o2.isDirectory()) return -1;
            if (!o1.isDirectory() && o2.isDirectory()) return 1;
            return o1.compareTo(o2);
        });
        File parent = file.getParentFile();
        String uri = FileUtility.relativeUrl(parent);
        String urlParent = req.getContextPath() + "/blog-manage" + uri;

        String url = req.getRequestURI();
        req.setAttribute("pathInfo", req.getPathInfo());
        req.setAttribute("url", url);
        req.setAttribute("files", files);
        req.setAttribute("root", parent.equals(FileUtility.BLOG_FILES_FOLDER));
        req.setAttribute("parentUrl", urlParent);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/manageBlogs.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Blog blog = Blog.fromPathInfo(req.getPathInfo());
        File file = FileUtility.blogPathToFile(req.getPathInfo());
        if (blog==null||!session.isLogged() || !blog.hasAccess(session.getUtente())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "utente non loggato o non proprietario");
            return;
        }
        if (file == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File non trovato");
            return;
        }
        if(file.isFile()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Impossibile gestire un file");
            return;
        }
        manageFolder(req, resp, file);
    }


    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }

}
