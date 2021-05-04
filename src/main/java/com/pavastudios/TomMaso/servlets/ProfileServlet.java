package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Profilo", value = "/user-profile")
public class ProfileServlet extends MasterServlet {

    @Override
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String username = req.getParameter("username");
        if(username==null){
            Utente u= (Utente) session.getAttribute(RememberMeUtility.SESSION_USER);
            username=u==null?null:u.getUsername();
        }
        Utente u = username!=null?Queries.findUserByUsername(username):null;
        if (u != null) {
            req.setAttribute("user", u);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //username non trovato
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
