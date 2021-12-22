package com.pavastudios.TomMaso.control.blog;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.model.Blog;
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
 * Classe che consente il salvataggio dei file markdown a seguito di modifiche.
 * Se il file già esiste, viene salvato in esso il nuovo contenuto, altrimenti un nuovo
 * file è creato sul server scrivendo al suo interno il contenuto scritto nell'editor
 */
@MultipartConfig()
public class MDFileServlet extends MasterServlet {

    private void manageExistingFile(HttpServletRequest req, File file) throws IOException, ServletException {
        FileOutputStream out = new FileOutputStream(file);
        Part part = req.getPart("content");
        FileUtility.writeFile(part.getInputStream(), out);
        out.close();
    }


    private void convertToFile(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException {
        String path = req.getPathInfo();
        Blog blog = Blog.fromPathInfo(path);
        if (blog == null || !blog.hasAccess(session.getUtente())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Non proprietario");
            return;
        }
        File file = FileUtility.blogPathToFile(path);
        if (file == null || !file.getParentFile().exists()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Percorso non valido");
            return;
        }
        if (!path.endsWith(".md")) {
            file = new File(file.getParentFile(), file.getName() + ".md");
        }
        manageExistingFile(req, file);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        convertToFile(session, req, resp);
        String path = req.getPathInfo();
        if (!path.endsWith(".md")) {
            path += ".md";
        }
        resp.sendRedirect(resp.encodeRedirectURL(getServletContext().getContextPath() + "/blogs" + path));
    }

}
