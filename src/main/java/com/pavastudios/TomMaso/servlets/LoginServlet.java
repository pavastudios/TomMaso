package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Login", value = "/login")

public class LoginServlet extends MasterServlet {

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente u = Queries.findUserByUsername(req.getParameter("username"));
        if (u != null) {
            if (u.userVerifyLogin(req.getParameter("password")) == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //password errata
            } else {
                session.setAttribute("utente", u);
                if ("on".equals(req.getParameter("remember"))) {
                    Cookie c = RememberMeUtility.createRememberMeCookie(u);
                    resp.addCookie(c);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
        }
    }
}
