package com.pavastudios.TomMaso.chat.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.chat.ChatQueries;
import com.pavastudios.TomMaso.storage.model.Chat;
import com.pavastudios.TomMaso.storage.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * ChatServlet è una entità di controllo che permette di leggere i messaggi presenti in una chat
 * ed offer un controllo sui permessi per valutare la possibilità di un moderatore
 * di visualizzare chat non sue.
 *
 */
public class ChatServlet extends MasterServlet {



    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String[] pathInfo = req.getPathInfo().split("/");
        if (!session.isLogged()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Must be logged");
            return;
        }
        Utente u1 = null, u2 = null;
        if (pathInfo.length == 2) {
            u1 = session.getUtente();
            u2 = UserQueries.findUserByUsername(pathInfo[1]);
        } else if (pathInfo.length == 3 && session.getUtente().getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) {
            u1 = UserQueries.findUserByUsername(pathInfo[1]);
            u2 = UserQueries.findUserByUsername(pathInfo[2]);
        }
        System.out.println(req.getPathInfo());
        Chat chat = ChatQueries.findChatByUsers(u1, u2);
        if (chat == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid chat");
            return;
        }
        req.setAttribute("chat", chat);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/chat/chat.jsp").forward(req, resp);
    }
}
