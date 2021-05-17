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

    private boolean createFileOnServer(Part part, String url) throws IOException {
        String submitted=part.getSubmittedFileName();
        if(submitted.contains("/")||submitted.contains("\\")) //l'utente è brutto e cattivo
            return false;
        File file = FileUtility.blogPathToFile(url);
        if(file==null)return false;
        file = new File(file,submitted);
        FileOutputStream out = new FileOutputStream(file);
        FileUtility.writeFile(part.getInputStream(),out);
        out.close();
        return true;
    }


    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user=session.getUtente();
        Part part = req.getPart("file");
        String url = req.getPathInfo();
        if(url==null||part==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Parametri mancanti");
            return;
        }
        Blog blog = Blog.fromPathInfo(url);
        if(blog==null||!blog.getProprietario().equals(user)){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Utente non autorizzato!");
            return;
        }

        if(!createFileOnServer(part,url)){
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,"Path traversal rilevato");
            return;
        }
        resp.sendRedirect(req.getServletContext().getContextPath()+"/blog-manage/"+url.substring(1));
    }
}

