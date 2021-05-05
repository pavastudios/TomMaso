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
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "Blog", urlPatterns = {"/blog/*"})
public class BlogViewerServlet extends MasterServlet {

    private File[] fetchFiles(Session session, HttpServletRequest req) throws SQLException {
        if (req.getPathInfo().length()<=1) return null;
        System.out.println(req.getPathInfo());
        String[] parts = req.getPathInfo().split("/",3);
        System.out.println(Arrays.toString(parts));
        String nomeBlog = parts[1];
        String fileSystem="";
        if(parts.length>2){
            for(int i=2;i<parts.length;i++){
                fileSystem+=parts[i];
            }
        }
        Blog blog = Queries.findBlogByName(nomeBlog);
        File blogDir =  new File(FileUtility.WEB_INF_PATH,"blogs");
        blogDir =  new File(blogDir,nomeBlog);
        req.setAttribute("blog", blog);
        req.setAttribute("fileSystem", fileSystem);
        return blogDir.listFiles();
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        File[] files = fetchFiles(session, req);
        Blog blog = (Blog) req.getAttribute("blog");
        Utente owner = blog.getProprietario();
        if(!session.isLogged()||!session.getUtente().equals(owner)){
            System.out.println("Crasho qui invece");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //utente non loggato o non proprietario
            return;
        }
        if (files == null) {
            System.out.println("Crasho qui");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //file non trovato
            return;
        }
        req.setAttribute("files",files);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/manageBlogs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }

}
