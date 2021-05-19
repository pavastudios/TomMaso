package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.utility.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MDEditorServlet extends MasterServlet{
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/markdownEditor.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(session, req, resp);
    }
}
