package com.pavastudios.TomMaso.api.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.api.ApiManager;
import com.pavastudios.TomMaso.utility.MasterServlet;
import com.pavastudios.TomMaso.utility.Tuple2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * La classe ApiServlet è una entità di controllo che offre la possibilità di invocare
 * le API richieste dall'utente
 */

public class ApiServlet extends MasterServlet {



    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        Tuple2<Integer, String> apiResponse = ApiManager.apiCall(session, req);
        resp.setStatus(apiResponse.get1());
        resp.getWriter().write(apiResponse.get2());
    }


}