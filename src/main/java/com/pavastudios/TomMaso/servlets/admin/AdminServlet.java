package com.pavastudios.TomMaso.servlets.admin;

import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminServlet extends MasterServlet {

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        //servlet protetta da tomcat
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/admin/adminManager.jsp").forward(req, resp);
    }

}