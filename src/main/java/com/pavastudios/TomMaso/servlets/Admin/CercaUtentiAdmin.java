package com.pavastudios.TomMaso.servlets.Admin;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pavastudios.TomMaso.db.queries.Queries;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CercaUtentiAdmin  extends MasterServlet {

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        if(session.getUtente().getIsAdmin()){
            List<Utente> u =new ArrayList<>();
            if(req.getParameter("nome")==null){
                u=Queries.resultSetToList(Entities.UTENTE,Queries.findAllUsers());
            }
            else u.add(Queries.findUserByUsername(req.getParameter("nome")));
            req.setAttribute("result",u);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/adminResult.jsp").forward(req, resp);
        } else resp.sendError(403);
    }

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session,req,resp);
    }
}
