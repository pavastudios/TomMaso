package com.pavastudios.TomMaso.control.global;

import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.tuple.Tuple2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
