package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.Utility;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@WebServlet(name = "Logout", value = "/logout")
public class LogoutServlet extends MasterServlet {
    @Override
    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        session.invalidate();

        for (Cookie c : req.getCookies()) {
            if (RememberMeUtility.COOKIE_REMEMBER_ME.equals(c.getName())) {
                Queries.removeCookie(c.getValue());
                c.setMaxAge(0);
                c.setValue("");
                resp.addCookie(c);
                break;
            }
        }
        Utility.returnHome(req, resp);
    }

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        doGet(session, req, resp);
    }
}
