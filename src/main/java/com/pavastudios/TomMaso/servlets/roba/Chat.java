package com.pavastudios.TomMaso.servlets.roba;

import com.pavastudios.TomMaso.db.queries.Queries;
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

@WebServlet(name = "Chat", value = "/chat")
public class Chat extends MasterServlet {

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente u1 = session.getUtente();
        List<Utente> list = new ArrayList<>();
        List<com.pavastudios.TomMaso.model.Chat> chats = Queries.findUserChat(u1);
        for (com.pavastudios.TomMaso.model.Chat c : chats) {
            Utente nome;
            if (c.getUtente1().equals(u1))
                nome = c.getUtente2();
            else
                nome = c.getUtente1();
            list.add(nome);
        }
        req.setAttribute("listaContattati", list);


        getServletContext().getRequestDispatcher("/WEB-INF/jsp/chat/provachat.jsp").forward(req, resp);
    }
}
