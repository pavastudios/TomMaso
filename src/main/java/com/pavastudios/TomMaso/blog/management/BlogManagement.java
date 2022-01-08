package com.pavastudios.TomMaso.blog.management;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.storage.FileUtility;
import com.pavastudios.TomMaso.storage.model.Blog;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class BlogManagement {
    public static boolean uploadFileOnServlet(Part part, String url) throws IOException {
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

    public static void uploadMdFileOnServlet(Session session, Part part, String path, HttpServletResponse resp) throws IOException, SQLException {
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
        FileOutputStream out = new FileOutputStream(file);
        FileUtility.writeFile(part.getInputStream(), out);
        out.close();
    }
}
