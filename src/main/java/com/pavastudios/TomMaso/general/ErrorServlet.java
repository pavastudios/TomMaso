package com.pavastudios.TomMaso.general;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ErrorServlet è una entità di control che reindirizza l'utente ad una pagina di errore
 * nel caso in cui qualche richiesta non rispecchia dei contratti oppure ci sia
 * qualche errore interno
 */

public class ErrorServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/global/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
