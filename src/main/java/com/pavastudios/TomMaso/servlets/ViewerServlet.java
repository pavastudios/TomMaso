package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.servlets.MasterServlet;

import com.pavastudios.TomMaso.utility.*;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import static com.pavastudios.TomMaso.utility.FileUtility.getFileType;

@WebServlet(name = "NewViewer", urlPatterns = {"/blogs/*","/users/*"})
public class ViewerServlet extends MasterServlet {
    private void manageFile(HttpServletRequest req, HttpServletResponse resp, File file) throws IOException, ServletException {
        if(getFileType(getServletContext(), file)== FileUtility.FileType.MARKDOWN){
            req.setAttribute("file",file);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/markdownViewer.jsp").forward(req,resp);
            return ;
        }
        OutputStream out = resp.getOutputStream();
        FileInputStream fr = new FileInputStream(file);
        resp.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
        //resp.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        FileUtility.writeFile(fr, out);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        File file=null;
        switch(req.getServletPath()){
            case "/users":
                file=FileUtility.userPathToFile(req.getPathInfo());
                break;
            case "/blogs":
                file=FileUtility.blogPathToFile(req.getPathInfo());
                break;
        }

        if(file==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"File invalido");
            return;
        }
        if(!file.exists()){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"File non trovato");
            return;
        }
        if(file.isDirectory()){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Impossibile aprire cartelle");
            return;
        }
        manageFile(req,resp,file);
    }

}
