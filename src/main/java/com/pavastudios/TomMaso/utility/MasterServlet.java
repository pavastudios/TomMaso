package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.access.Session;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 * MasterServlet è la classe padre di tutte le servlet e serve ad impostare
 * le richieste e le risposte con gli opportuni parametri
 */

public abstract class MasterServlet extends HttpServlet {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    /**
     * hasSessionCookie controlla se tra i cookie ce n'è uno con che corrisponde
     * al jsession id
     *
     * @param req sere ad ottere un eventuale jsession id attraverso cookie
     *            se sono abilitati
     * @return boolean value (True o False) che corrispondono rispoettivamente
     * al caso se il cookie corrisponde al jession id oppure no
     */
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

    /**
     * Impostati l'attributo rewrite al corrispettivo jsession id se i cookie sono disabilitati
     * oppure imposta l'attributo rewrite ad una string vuota
     *
     * @param req ottiene informazioni riguardo la presenza di cookie
     */
    private void setRewriteAttribute(HttpServletRequest req) {
        if (!hasSessionCookie(req))
            req.setAttribute("rewrite", ";jsessionid=" + req.getSession().getId());
        else
            req.setAttribute("rewrite", "");
    }

    /**
     * setDefaultHeader imposta un header standard per tutte le risposte
     *
     * @param resp serve ad ottenere la risposta sulla quale settare l'header di default
     */
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
