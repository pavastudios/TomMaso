package com.pavastudios.TomMaso.servlets;

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

    private static boolean hasSessionCookie(HttpServletRequest req){
        Cookie[]cookies=req.getCookies();
        if(cookies==null)return false;
        for(Cookie c:cookies) {
            if("JSESSIONID".equals(c.getName()))return true;
        }
        return false;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        setDefaultHeader(resp);

        try {
            Session session = Session.loadSession(req);

            if(!hasSessionCookie(req))
                req.setAttribute("rewrite",";jsessionid="+req.getSession().getId());
            else
                req.setAttribute("rewrite","");

            switch (method) {
                case METHOD_GET:
                    doGet(session, req, resp);
                    break;
                case METHOD_HEAD:
                    doHead(session, req, resp);
                    break;
                case METHOD_POST:
                    doPost(session, req, resp);
                    break;
                case METHOD_PUT:
                    doPut(session, req, resp);
                    break;
                case METHOD_DELETE:
                    doDelete(session, req, resp);
                    break;
                case METHOD_OPTIONS:
                    doOptions(session, req, resp);
                    break;
                case METHOD_TRACE:
                    doTrace(session, req, resp);
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


    private void setDefaultHeader(HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html; charset=UTF-8");
    }

    protected void doTrace(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doTrace(req, resp);
    }

    protected void doOptions(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doOptions(req, resp);
    }

    protected void doDelete(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doDelete(req, resp);
    }

    protected void doHead(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doHead(req, resp);
    }

    protected void doPut(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPut(req, resp);
    }

    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(req, resp);
    }

    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(req, resp);
    }

}
