package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.RememberMeUtility;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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


    private HttpSession loadSession(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession(true);
        if (session.isNew()) {
            Cookie[] cookies = req.getCookies();
            for (Cookie c : cookies) {
                if (RememberMeUtility.COOKIE_REMEMBER_ME.equals(c.getName())) {
                    Utente u = Queries.findUserByCookie(c.getValue());
                    if (u != null)
                        session.setAttribute(RememberMeUtility.SESSION_USER, u);
                    break;
                }
            }
        }
        return session;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        setDefaultHeader(resp);

        try {
            HttpSession session = loadSession(req);
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
        resp.setHeader("Content-Type", "text/html; charset=UTF-8");
    }

    protected void doTrace(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doTrace(req, resp);
    }

    protected void doOptions(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doOptions(req, resp);
    }

    protected void doDelete(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doDelete(req, resp);
    }

    protected void doHead(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doHead(req, resp);
    }

    protected void doPut(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPut(req, resp);
    }

    protected void doGet(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doGet(req, resp);
    }

    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        doPost(req, resp);
    }

}
