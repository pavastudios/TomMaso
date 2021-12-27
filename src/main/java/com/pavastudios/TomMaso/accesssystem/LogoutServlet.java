package com.pavastudios.TomMaso.accesssystem;

import com.pavastudios.TomMaso.utility.MasterServlet;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogoutServlet è una entità di controllo che si occupa di far effettuare il
 * logout dalla piattaforma ed invalidare la sessione dell'utente
 */

public class LogoutServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        Utility.returnHome(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) {
        doGet(session, req, resp);
    }
}
