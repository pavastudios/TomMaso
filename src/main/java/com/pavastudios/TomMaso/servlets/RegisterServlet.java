package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Registrazione", value = "/sign-up")

public class RegisterServlet extends MasterServlet {

    @Override
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("mail");
        boolean remember = "on".equals(req.getParameter("remember"));
        if (username == null || password == null || email == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Utente utente;
        try {
            utente = Queries.registerUser(email, password, username);
            if (utente == null) {
                resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Email o username già esistenti");
                return;
            }
            session.setAttribute(RememberMeUtility.SESSION_USER, utente);
            if (remember) {
                resp.addCookie(RememberMeUtility.createRememberMeCookie(utente));
            }
            Utility.returnHome(req, resp);
        } catch (SQLException throwables) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, throwables.getErrorCode() + ": " + throwables.getLocalizedMessage());

            throwables.printStackTrace();
        }
    }

}
