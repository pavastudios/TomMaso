package com.pavastudios.TomMaso.access.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LogoutServlet è una entità di controllo che si occupa di far effettuare il
 * logout dalla piattaforma ed invalidare la sessione dell'utente
 */

public class LogoutServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(session, req, resp);
    }
}
