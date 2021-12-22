package com.pavastudios.TomMaso.control;

import com.pavastudios.TomMaso.utility.Session;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public abstract class MasterServlet extends HttpServlet {

    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";

    private static boolean hasSessionCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return false;
        for (Cookie c : cookies) {
            if ("JSESSIONID".equals(c.getName())) return true;
        }
        return false;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        setDefaultHeader(resp);

        try {
            Session session = Session.loadSession(req);
            setRewriteAttribute(req);

            switch (method) {
                case METHOD_GET:
                    doGet(session, req, resp);
                    break;
                case METHOD_POST:
                    doPost(session, req, resp);
                    break;
                default:
                    String errMsg = String.format(Locale.US, "The method '%s' was not implemented", method);
                    resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setRewriteAttribute(HttpServletRequest req){
        if (!hasSessionCookie(req))
            req.setAttribute("rewrite", ";jsessionid=" + req.getSession().getId());
        else
            req.setAttribute("rewrite", "");
    }

    private void setDefaultHeader(HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html; charset=UTF-8");
    }

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(req, resp);
    }

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(req, resp);
    }

}
