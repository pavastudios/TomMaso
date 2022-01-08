package com.pavastudios.TomMaso.blog.management.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.blog.management.BlogManagement;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe che consente il salvataggio dei file markdown a seguito di modifiche.
 * Se il file già esiste, viene salvato in esso il nuovo contenuto, altrimenti un nuovo
 * file è creato sul server scrivendo al suo interno il contenuto scritto nell'editor
 */
@MultipartConfig()
public class BlogMDUploaderServlet extends MasterServlet {




    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Part content=req.getPart("content");
        String pathInfo=req.getPathInfo();
        BlogManagement.uploadMdFileOnServlet(session, content, pathInfo, resp);
        String path = req.getPathInfo();
        if (!path.endsWith(".md")) {
            path += ".md";
        }
        resp.sendRedirect(resp.encodeRedirectURL(getServletContext().getContextPath() + "/blogs" + path));
    }

}
