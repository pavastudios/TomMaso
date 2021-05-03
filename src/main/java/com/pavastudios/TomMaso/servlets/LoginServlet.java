package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.Utility;

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
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        boolean remember="on".equals(req.getParameter("remember"));

        Utente u = Queries.findUserByUsername(username);
        if (u != null) {
            if (u.userVerifyLogin(password) == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //password errata
            } else {
                session.setAttribute(RememberMeUtility.SESSION_USER, u);
                if (remember) {
                    Cookie c = RememberMeUtility.createRememberMeCookie(u);
                    resp.addCookie(c);
                }
                Utility.returnHome(req,resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
        }
    }
}
