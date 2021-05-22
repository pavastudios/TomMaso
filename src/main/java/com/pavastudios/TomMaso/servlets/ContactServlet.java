package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ContactServlet extends MasterServlet{

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String user=req.getParameter("receiver");
        int userId= Utility.tryParseInt(user,-1);
        Utente receiver = Queries.findUserById(userId);
        if(receiver==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"receiver invalido");
            return;
        }

        Chat chat=Queries.findChatByUsers(session.getUtente(),receiver);
        if(chat!=null){
            resp.sendRedirect(resp.encodeRedirectURL(getServletContext().getContextPath()+"/chat?id="+chat.getIdChat()));
            return;
        }
        chat = Queries.createChat(session.getUtente(),receiver);
        if(chat==null){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Impossibile creare chat");
            return;
        }
        resp.sendRedirect(resp.encodeRedirectURL(getServletContext().getContextPath()+"/chat?id="+chat.getIdChat()));
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
