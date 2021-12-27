package com.pavastudios.TomMaso.accesssystem.servlet;

import com.pavastudios.TomMaso.accesssystem.Session;
import com.pavastudios.TomMaso.accesssystem.UserQueries;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * LoginServlet è una entità di controllo che permette all'utente di effettuare il login
 * nella piattaforma ed imposta la sessione all'utente. Inoltre vengono
 * controllati i permessi dell'utente per vedere de può effettuare il login
 * dopo un eventuale ban
 */

public class LoginServlet extends MasterServlet {

    private static final boolean PASSWORD_BYPASS = false;

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Utente u = UserQueries.findUserByUsername(username);
        if (u == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
            return;
        }
        if (!PASSWORD_BYPASS && !u.userVerifyLogin(password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //password errata
            return;
        }
        if (!u.getPermessi().hasPermissions(Utente.Permessi.CAN_LOGIN)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN); //l'utente non ha il permesso di loggare
            return;
        }
        session.setUtente(u);
        Utility.returnHome(req, resp);

    }
}
