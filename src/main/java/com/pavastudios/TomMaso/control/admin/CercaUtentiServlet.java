package com.pavastudios.TomMaso.control.admin;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CercaUtentiServlet extends MasterServlet {

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

        List<Utente> admin = new ArrayList<>();
        if (req.getParameter("admin").equals("true")) {

            List<Utente> u = UserQueries.findAllAdmins();
            for (Utente x : u) if (x.getIsAdmin()) admin.add(x);

        } else
            admin.add(UserQueries.findUserByUsername(req.getParameter("nome")));

        req.setAttribute("result", admin);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/admin/adminResult.jsp").forward(req, resp);

    }

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
