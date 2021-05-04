package com.pavastudios.TomMaso.servlets;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.api.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChatAPI",urlPatterns = {"/api/*"})
public class ApiServlet extends MasterServlet {

    @Override
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(session, req, resp);
    }

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        resp.setContentType("text/json");

        Utente user = (Utente) session.getAttribute(RememberMeUtility.SESSION_USER);
        JsonWriter writer = new JsonWriter(resp.getWriter());

        writer.beginObject();

        ApiManager.manageEndpoint(req, writer, user);

        writer.endObject();
        writer.flush();
    }


}
