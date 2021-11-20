package com.pavastudios.TomMaso.control.user;

import com.pavastudios.TomMaso.control.MasterServlet;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class LogoutServlet extends MasterServlet {
    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        req.getSession().invalidate();
        Utility.returnHome(req, resp);
    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        doGet(session, req, resp);
    }
}
