package com.pavastudios.TomMaso.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Index", value = "")
public class IndexServlet extends MasterServlet {
    @Override
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
