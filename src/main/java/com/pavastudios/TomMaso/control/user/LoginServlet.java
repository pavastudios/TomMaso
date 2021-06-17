package com.pavastudios.TomMaso.control.user;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends MasterServlet {

    private static final boolean PASSWORD_BYPASS = false;

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean remember = "on".equals(req.getParameter("remember"));

        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
            return;
        }
        if (!PASSWORD_BYPASS && !u.userVerifyLogin(password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //password errata
            return;
        }
        session.setUtente(u);
        if (remember) {
            Cookie c = RememberMeUtility.createRememberMeCookie(u);
            resp.addCookie(c);
        }
        Utility.returnHome(req, resp);

    }
}