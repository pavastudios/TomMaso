package com.pavastudios.TomMaso.servlets.roba;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import java.util.*;

@WebServlet(name = "ChatList",value = "/chat-list")
public class GeneraChat extends MasterServlet {
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException,ServletException,IOException {
        String nome=(String)req.getParameter("unto");
        Utente u2= Queries.findUserByUsername(nome);
        Chat c= Queries.findChatByUsers((Utente)session.getAttribute(RememberMeUtility.SESSION_USER),u2);
        req.setAttribute("chat",c);
        getServletContext().getRequestDispatcher("/WEB-INF/jps/generatedchat.jsp").forward(req,resp);

    }

    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException,ServletException,IOException {
        doPost(session,req,resp);
    }
}
