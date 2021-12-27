package com.pavastudios.TomMaso.accesssystem;

import com.pavastudios.TomMaso.blogsystem.BlogQueries;
import com.pavastudios.TomMaso.storagesystem.model.Blog;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * ProfileServlet è una sservel di controllo che permette la visita del profilo di un
 * utente con relativa lista dei suoi blog
 */

public class ProfileServlet extends MasterServlet {

    /**
     * Ottiene le informazioni su quale profilo caricare
     * @param session
     * @param req
     * @return
     * @throws SQLException
     */

    private Utente fetchUser(Session session, HttpServletRequest req) throws SQLException {
        Utente userLogged = session.getUtente();
        if (req.getPathInfo() == null) return userLogged;
        String[] parts = req.getPathInfo().split("/");
        if (parts.length == 0) return userLogged;
        String username = parts[parts.length - 1];
        return UserQueries.findUserByUsername(username);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente u = fetchUser(session, req);
        if (u == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "username non trovato"); //username non trovato
            return;
        }
        List<Blog> blogs = BlogQueries.getBlogsUser(u);
        req.setAttribute("user", u);
        req.setAttribute("blogs", blogs);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/global/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
