package com.pavastudios.TomMaso.access.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ProfileServlet è la servet di controllo per permettere ad un utente di registrarsi
 * nella piattaforma con controlli sulla lunghezza dell'username e della
 *  password al fine di avere più sicurezza e protezione per l'account dell'utente
 */

public class RegisterServlet extends MasterServlet {

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        if (username == null || password1 == null || password2 == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri mancanti");
            return;
        }
        if (!password1.equals(password2)) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Password non uguali");
            return;
        }
        if (password1.length() < Utente.PASSWORD_MIN_LENGTH) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Password troppo corta");
            return;
        }
        if (!Utility.isStandardName(username)) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Nome utente non valido");
            return;
        }
        Utente utente;
        try {
            utente = UserQueries.registerUser(password1, username);
            if (utente == null) {
                resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Username già esistenti");
                return;
            }
            session.setUtente(utente);
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));
        } catch (SQLException throwables) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, throwables.getErrorCode() + ": " + throwables.getLocalizedMessage());
            throwables.printStackTrace();
        }
    }

}
