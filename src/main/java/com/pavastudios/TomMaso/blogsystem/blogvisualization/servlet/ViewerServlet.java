package com.pavastudios.TomMaso.blogsystem.blogvisualization.servlet;

import com.pavastudios.TomMaso.accesssystem.Session;
import com.pavastudios.TomMaso.blogsystem.blogvisualization.CommentQueries;
import com.pavastudios.TomMaso.storagesystem.FileUtility;
import com.pavastudios.TomMaso.storagesystem.model.Blog;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * ViewerServlet è una entità di controllo che ci permette di visualizzare il contenuto
 * dei blog con gli opportuni commenti oppure di scaricare il file nel caso in cui
 * il contenuto sia un file
 */

public class ViewerServlet extends MasterServlet {

    /**
     * manageMarkdown prende in input una sessione per settare il blog visitato,
     * una richiesta per ottenete il path del contenuto da caricare, la risposta
     * per costruire la risposta con gli opportuni parametri e un file per caricare
     * un eventuale file
     *
     * @param session per settare il blog visitato
     * @param req per ottenete il path del contenuto da caricare
     * @param resp per costruire la risposta con gli opportuni parametri
     * @param file per caricare un eventuale file
     */

    private void manageMarkdown(Session session, HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException, SQLException {
        Blog blog = Blog.fromPathInfo(req.getPathInfo());
        session.visitedBlog(blog);
        req.setAttribute("file", file);
        req.setAttribute("blog", blog);
        req.setAttribute("comments", CommentQueries.fetchCommentsFromPage(req.getPathInfo()));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/blog/markdownViewer.jsp").forward(req, resp);
    }

    /**
     * Attraverso il mime si può capire quale tipo di file deve essere elaborato
     * e costruire l'opportuna risposta
     *
     * @param resp per costruire la risposta con gli oppoeruni parametri
     * @param file per capire quale tipo di file deve essere elaborato
     * @throws IOException
     * @throws ServletException
     */

    private void manageFile(HttpServletResponse resp, File file) throws IOException {
        OutputStream out = resp.getOutputStream();
        FileInputStream fr = new FileInputStream(file);
        resp.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
        FileUtility.writeFile(fr, out);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        File file = null;
        String pathInfo = req.getPathInfo();
        switch (req.getServletPath()) {
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
            manageFile(resp, file);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
