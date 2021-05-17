package com.pavastudios.TomMaso.servlets.roba;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "CreaChat", value = "/crea-chat")
public class CreaChat extends MasterServlet {

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id=Utility.tryParseInt(req.getParameter("id"),-1);
        Chat chat=Queries.findChatById(id);
        if(chat==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid chat");
            return;
        }
        if(!chat.hasAccess(session.getUtente())){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,"You can't access the chat");
            return;
        }
        req.setAttribute("chat",chat);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/chat/chat.jsp").forward(req, resp);
    }
}
