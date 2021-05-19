package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.model.Utente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Session {

    public static final String NEW_TOKEN_ATTRIBUTE = "csrf";
    public static final String CSRF_VALID_ATTRIBUTE = "csrf-valid";
    public static final String SESSION_FIELD = "session";
    public static final String TOKEN_PARAM = "csrf-token";

    private static final int MAX_TOKENS_COUNT = 20;
    private static final int CSRF_TOKEN_LENGTH = 20;
    private final List<String> csrfTokens = new ArrayList<>(MAX_TOKENS_COUNT);
    private Utente utente = null;
    private HashSet<Integer> blogs = new HashSet<>();

    private Session() {
    }

    public static Session loadSession(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Session s = null;
        if (!session.isNew())//restore from httpsession
            s = (Session) session.getAttribute(SESSION_FIELD);
        if (s == null) //create new session
            s = createSession(req);
        session.setAttribute(SESSION_FIELD, s);
        s.initRequestAttributes(req); //inizializza roba come attributi nella request
        return s;
    }


    public boolean hasVisitedBlog(Blog blog) {
        if(blog==null)return false;
        return blogs.contains(blog.getIdBlog());
    }

    public void visitedBlog(Blog blog) throws SQLException {
        if(blog==null)return;
        if(!hasVisitedBlog(blog))
            Queries.incrementVisit(blog);
        blogs.add(blog.getIdBlog());
    }

    private static Session createSession(HttpServletRequest req) {
        Session s = new Session();
        try {
            Utente u = RememberMeUtility.getUserFromCookies(req);
            s.setUtente(u);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return s;
    }

    public boolean isLogged() {
        return utente != null;
    }

    public Utente getUtente() {
        return utente;
    }

    public Session setUtente(Utente utente) {
        this.utente = utente;
        return this;
    }

    public List<String> getCsrfTokens() {
        return csrfTokens;
    }

    private String addToken() {
        byte[] bytes = Security.generateRandomBytes(CSRF_TOKEN_LENGTH);
        String token = Utility.toHexString(bytes);
        if (csrfTokens.size() == MAX_TOKENS_COUNT)
            csrfTokens.remove(0);
        csrfTokens.add(token);
        return token;
    }

    private void initRequestAttributes(HttpServletRequest req) {
        String token = addToken();
        boolean validCsrf = verifyCsrf(req);
        req.setAttribute(NEW_TOKEN_ATTRIBUTE, token); //mette il token per questa richiesta nella request
        req.setAttribute(CSRF_VALID_ATTRIBUTE, validCsrf); //mette se il CSRF token è valido per questa richiesta nella request
    }

    private boolean verifyCsrf(HttpServletRequest req) {
        String token = req.getParameter(TOKEN_PARAM);
        return csrfTokens.remove(token);
    }

}
