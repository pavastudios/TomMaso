package com.pavastudios.TomMaso.servlets.user;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;


@WebServlet(name = "BlogFileUpload", urlPatterns = {"/upload-file/*"})
@MultipartConfig()
public class UserFileUploader extends MasterServlet {

    private void createFileOnServer(Part part, String url) throws IOException {
        File file = new File(FileUtility.BLOG_FILES_FOLDER,url);
        file = new File(file,part.getSubmittedFileName());
        FileOutputStream out = new FileOutputStream(file);
        FileUtility.writeFile(part.getInputStream(),out);
        out.close();
    }


    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user=session.getUtente();
        Part part = req.getPart("file");
        String url = req.getParameter("url");
        Blog blog = Queries.findBlogByName(url.split("/")[0]);
        if(!blog.getProprietario().equals(session.getUtente())){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Utente non autorizzato!");
            return ;
        }
        createFileOnServer(part,url);
        resp.sendRedirect(req.getServletContext().getContextPath()+"/blog-manage/"+url);
    }
}

