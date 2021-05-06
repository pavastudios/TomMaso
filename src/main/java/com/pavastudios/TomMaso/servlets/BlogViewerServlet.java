package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name = "Blog", urlPatterns = {"/blog/*"})
public class BlogViewerServlet extends MasterServlet {

    private Blog resolveBlog(HttpServletRequest req) throws SQLException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/");
        if (parts.length < 2) return null;
        return Queries.findBlogByName(parts[1]);
    }

    private File resolveFile(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/");
        File file = FileUtility.BLOG_FILES_FOLDER;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals("..")) return null;
            if (parts[i].isEmpty()) continue;
            file = new File(file, parts[i]);
        }
        file = file.getAbsoluteFile();
        if (!file.exists() || !file.getAbsolutePath().startsWith(FileUtility.BLOG_FILES_FOLDER.getAbsolutePath()))
            return null;
        return file;
    }

    private void manageFile(HttpServletRequest req, HttpServletResponse resp, File file) throws IOException {
        OutputStream out = resp.getOutputStream();
        FileInputStream fr = new FileInputStream(file);
        resp.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
        //resp.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        FileUtility.writeFile(fr, out);
    }

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
        String urlParent = req.getContextPath() + "/blog" + uri;

        String url = req.getRequestURI();
        req.setAttribute("url", url);
        req.setAttribute("files", files);
        req.setAttribute("root", parent.equals(FileUtility.BLOG_FILES_FOLDER));
        req.setAttribute("parentUrl", urlParent);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/manageBlogs.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Blog blog = resolveBlog(req);
        File file = resolveFile(req);
        Utente owner = blog == null ? null : blog.getProprietario();
        if (!session.isLogged() || !session.getUtente().equals(owner)) {
            System.out.println("Crasho qui invece");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "utente non loggato o non proprietario");
            return;
        }
        if (file == null) {
            System.out.println("Crasho qui");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File non trovato");
            return;
        }

        if (file.isDirectory()) {
            manageFolder(req, resp, file);
        } else if (file.isFile()) {
            manageFile(req, resp, file);
        }
    }


    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }

}
