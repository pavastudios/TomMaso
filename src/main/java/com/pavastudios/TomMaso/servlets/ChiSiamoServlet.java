package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChiSiamoServlet extends MasterServlet{
    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/chiSiamo.jsp").forward(req,resp);
        return ;
    }
}