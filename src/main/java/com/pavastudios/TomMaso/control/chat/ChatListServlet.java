package com.pavastudios.TomMaso.control.chat;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ChatListServlet extends MasterServlet {

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(session, req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente login = session.getUtente();
        if (login == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Utente non autorizzato");
            return;
        }
        List<Chat> chats = Queries.findUserChat(login);
        req.setAttribute("chats", chats);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/chat/chatsList.jsp").forward(req, resp);
        return;
    }
}
