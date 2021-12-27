package com.pavastudios.TomMaso.chatsystem;

import com.pavastudios.TomMaso.accesssystem.Session;
import com.pavastudios.TomMaso.storagesystem.model.Chat;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * ChatListServlet è un entità di controllo che fornisce all'utente che ne fa richiesta
 * una lista delle chat a cui un utente partecipa, che siano esse cominciate dall'utente
 * stesso oppure cominciate da terzi.
 */
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
        List<Chat> chats = ChatQueries.findUserChat(login);
        req.setAttribute("chats", chats);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/chat/chatsList.jsp").forward(req, resp);
    }
}
