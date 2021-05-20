package com.pavastudios.TomMaso.servlets.blog;

import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class EditPageServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user=session.getUtente();
        String pathInfo=req.getPathInfo();
        Blog blog=Blog.fromPathInfo(pathInfo);
        File file = FileUtility.blogPathToFile(pathInfo);
        FileUtility.FileType type=FileUtility.getFileType(getServletContext(),file);
        if(blog==null||!blog.hasAccess(user)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Blog errato");
            return;
        }
        if(type != FileUtility.FileType.MARKDOWN){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Impossibile modificare non markdown");
            return;
        }
        if(!file.exists()&&!file.getParentFile().exists()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"File invalido");
            return;
        }
        req.setAttribute("file",file);

    }
}
