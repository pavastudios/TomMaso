package com.pavastudios.TomMaso.access.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;
import org.jetbrains.annotations.Nullable;

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

    private String fetchUser(@Nullable Utente u, @Nullable String password) {
        if (u == null) {
            return "Username non trovato";
        }
        if (!PASSWORD_BYPASS && !u.userVerifyLogin(password)) {
            return "Password errata";
        }
        if (!u.getPermessi().hasPermissions(Utente.Permessi.CAN_LOGIN)) {
            return "Utente bananto";
        }
        return null;
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Utente u = UserQueries.findUserByUsername(username);
        String errorString = fetchUser(u, password);
        if (errorString != null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, errorString);
            return;
        }
        session.setUtente(u);
        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));
    }
}
