package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Profilo", urlPatterns = {"/user", "/user/*","/profile"})
public class ProfileServlet extends MasterServlet {

    private Utente fetchUser(Session session, HttpServletRequest req) throws SQLException {
        Utente userLogged = session.getUtente();
        if (req.getPathInfo() == null) return userLogged;
        String[] parts = req.getPathInfo().split("/");
        if (parts.length == 0) return userLogged;
        String username = parts[parts.length - 1];
        return Queries.findUserByUsername(username);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente u = fetchUser(session, req);
        if (u == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
            return;
        }
        List<Blog> blogs = Queries.getBlogsUser(u);
        req.setAttribute("user", u);
        req.setAttribute("blogs", blogs);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
