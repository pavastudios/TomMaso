package com.pavastudios.TomMaso.servlets.roba;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InviaMessaggio", value = "/invia-messaggio")

public class InviaMessaggio extends MasterServlet {

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String mex=req.getParameter("mex");
        Utente loggato= Queries.findUserByUsername(req.getParameter("loggato"));
        Utente altro= Queries.findUserByUsername(req.getParameter("altro"));

        Queries.inviaMessaggio(loggato,altro,mex);
    }

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session,req,resp);
    }
}
