package com.pavastudios.TomMaso.access.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ProfileServlet è la servet di controllo per permettere ad un utente di registrarsi
 * nella piattaforma con controlli sulla lunghezza dell'username e della
 * password al fine di avere più sicurezza e protezione per l'account dell'utente
 */

public class RegisterServlet extends MasterServlet {

    @Nullable
    private String checkParams(@Nullable String username, @Nullable String password1, @Nullable String password2) {
        if (username == null || password1 == null || password2 == null) {
            return "Parametri mancanti";
        }
        if (password1.length() < Utente.PASSWORD_MIN_LENGTH) {
            return "Password troppo corta";
        }
        if (!password1.equals(password2)) {
            return "Password non uguali";
        }

        if (!Utility.isStandardName(username)) {
            return "Nome utente non valido";
        }
        return null;
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String errorString = checkParams(username, password1, password2);
        if (errorString != null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, errorString);
            return;
        }
        Utente utente = UserQueries.registerUser(password1, username);
        if (utente == null) {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Username già esistenti");
            return;
        }
        session.setUtente(utente);
        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));

    }

}
