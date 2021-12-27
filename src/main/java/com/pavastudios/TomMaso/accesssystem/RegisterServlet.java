package com.pavastudios.TomMaso.accesssystem;

import com.pavastudios.TomMaso.storagesystem.model.Utente;
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
            utente = UserQueries.registerUser(password1, username);
            if (utente == null) {
                resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Username già esistenti");
                return;
            }
            session.setUtente(utente);
            Utility.returnHome(req, resp);
        } catch (SQLException throwables) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, throwables.getErrorCode() + ": " + throwables.getLocalizedMessage());

            throwables.printStackTrace();
        }
    }

}
