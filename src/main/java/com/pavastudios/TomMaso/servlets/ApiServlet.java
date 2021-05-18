package com.pavastudios.TomMaso.servlets;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.ApiWriter;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChatAPI", urlPatterns = {"/api/*"})
public class ApiServlet extends MasterServlet {

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(session, req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        resp.setContentType("text/json");

        ApiWriter writer = new ApiWriter(resp.getWriter());

        writer.beginObject();

        ApiManager.manageEndpoint(session, req, writer);

        writer.endObject();
        writer.flush();
        if(writer.isErrorWritten())
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


}
