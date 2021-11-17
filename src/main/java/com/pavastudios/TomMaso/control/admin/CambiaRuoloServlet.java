package com.pavastudios.TomMaso.control.admin;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CambiaRuoloServlet extends MasterServlet {

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente u = UserQueries.findUserByUsername(req.getParameter("nome"));
        Utente.Permessi p = new Utente.Permessi(u.getPermessi().getPermessi() ^ Utente.Permessi.MANAGE_USER);
        UserQueries.changeRole(u, p);
        resp.sendRedirect(resp.encodeRedirectURL(req.getServletContext().getContextPath() + "/admin"));
    }
}