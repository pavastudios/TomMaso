package com.pavastudios.TomMaso.servlets.blog;

import com.pavastudios.TomMaso.servlets.MasterServlet;
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

@WebServlet(name = "BlogViewer", urlPatterns = {"/blogs/*"})
public class BlogViewerServlet extends MasterServlet {
    private void manageFile(HttpServletRequest req, HttpServletResponse resp, File file) throws IOException {
        OutputStream out = resp.getOutputStream();
        FileInputStream fr = new FileInputStream(file);
        resp.setContentType(getServletContext().getMimeType(file.getAbsolutePath()));
        //resp.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        FileUtility.writeFile(fr, out);
    }
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        File file=FileUtility.blogPathToFile(req.getPathInfo());
        if(file==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"File invalido");
            return;
        }
        manageFile(req,resp,file);
    }

}
