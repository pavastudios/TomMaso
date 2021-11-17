package com.pavastudios.TomMaso.control.user;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends MasterServlet {


    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String email = req.getParameter("email");
        boolean remember = "on".equals(req.getParameter("remember"));
        if (username == null || password1 == null || password2 == null || email == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!password1.equals(password2)) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Password non uguali");
            return;
        }
        if (username.length() < Utente.MINIMUM_USERNAME_LENGTH || !Utility.useOnlyNormalChars(username)) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Nome utente non valido");
            return;
        }
        Utente utente;
        try {
            utente = UserQueries.registerUser(email, password1, username);
            if (utente == null) {
                resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Email o username già esistenti");
                return;
            }
            utente.getUserFolder().mkdir();//crea cartella per l'utente
            session.setUtente(utente);
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
