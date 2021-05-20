package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ContactServlet extends MasterServlet{

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente receiver = Queries.findUserById(Integer.parseInt(req.getParameter("receiver")));
        Chat newChat = Queries.createChat(session.getUtente(),receiver);
        resp.sendRedirect(getServletContext().getContextPath()+"/create-chat?id="+newChat.getIdChat());
        return ;
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
