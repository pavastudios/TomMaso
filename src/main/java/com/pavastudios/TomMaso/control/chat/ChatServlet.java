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
            u2 = Queries.findUserByUsername(pathInfo[1]);
        } else if (pathInfo.length == 3 && session.getUtente().getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) {
            u1 = Queries.findUserByUsername(pathInfo[1]);
            u2 = Queries.findUserByUsername(pathInfo[2]);
        }
        System.out.println(req.getPathInfo());
        Chat chat = Queries.findChatByUsers(u1, u2);
        if (chat == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid chat");
            return;
        }
        req.setAttribute("chat", chat);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/chat/chat.jsp").forward(req, resp);
    }
}
