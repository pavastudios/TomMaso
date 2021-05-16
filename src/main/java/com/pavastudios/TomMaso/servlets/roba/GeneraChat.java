package com.pavastudios.TomMaso.servlets.roba;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
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
import java.util.List;


@WebServlet(name = "GeneraChat", value = "/genera-chat")
public class GeneraChat extends MasterServlet {

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String nome = req.getParameter("unto");
        Utente u2 = Queries.findUserByUsername(nome);
        Chat c = Queries.findChatByUsers(session.getUtente(), u2);

        List<Messaggio> messaggi = Queries.fetchMessages(c);
        messaggi=messaggi.subList(messaggi.size()-5,messaggi.size());

        req.setAttribute("messaggi", messaggi);
        req.setAttribute("loggato", session.getUtente());
        req.setAttribute("altro", u2);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/chat/generatedchat.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(session, req, resp);
    }
}
