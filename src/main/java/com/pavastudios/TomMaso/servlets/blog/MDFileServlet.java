package com.pavastudios.TomMaso.servlets.blog;

import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.test.PersonalFileDir;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static com.pavastudios.TomMaso.model.Blog.fromPathInfo;

public class MDFileServlet extends MasterServlet {

    private void manageExistingFile(Session session, HttpServletRequest req, HttpServletResponse resp, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        byte[] content = req.getParameter("content").getBytes(StandardCharsets.UTF_8);
        out.write(content);
        out.flush();
        out.close();
    }


    private void convertToFile(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String path  = req.getPathInfo();
        Blog blog = Blog.fromPathInfo(path);
        if(blog==null||!blog.hasAccess(session.getUtente())){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Non proprietario");
            return ;
        }
        File file = FileUtility.blogPathToFile(path);
        if(file==null||!file.getParentFile().exists()){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Percorso non valido");
            return ;
        }
        if(!path.endsWith(".md")){
            file=new File(file.getParentFile(),file.getName()+".md");
        }
        manageExistingFile(session,req,resp,file);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        convertToFile(session,req,resp);
        String path = req.getPathInfo();
        if(!path.endsWith(".md")){
            path+=".md";
        }
        resp.sendRedirect(resp.encodeRedirectURL(getServletContext().getContextPath()+"/blogs"+path));
        return ;
    }

}
