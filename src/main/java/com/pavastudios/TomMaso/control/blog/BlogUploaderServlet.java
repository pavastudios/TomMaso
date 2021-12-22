package com.pavastudios.TomMaso.control.blog;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe che consente l'uploading dei file sul server
 */
@MultipartConfig()
public class BlogUploaderServlet extends MasterServlet {

    private boolean createFileOnServer(Part part, String url) throws IOException {
        String submitted = part.getSubmittedFileName();
        if (submitted.contains("/") || submitted.contains("\\"))
            return false;
        File file = FileUtility.blogPathToFile(url);
        if (file == null) return false;
        file = new File(file, submitted);
        FileOutputStream out = new FileOutputStream(file);
        FileUtility.writeFile(part.getInputStream(), out);
        out.close();
        return true;
    }


    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user = session.getUtente();
        Part part = req.getPart("file");
        String url = req.getPathInfo();
        if (url == null || part == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti");
            return;
        }
        Blog blog = Blog.fromPathInfo(url);
        if (blog == null || !blog.hasAccess(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Utente non autorizzato!");
            return;
        }

        if (!createFileOnServer(part, url)) {
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Path traversal rilevato");
            return;
        }
        resp.sendRedirect(resp.encodeRedirectURL(req.getServletContext().getContextPath() + "/blog-manage/" + url.substring(1)));
    }
}

