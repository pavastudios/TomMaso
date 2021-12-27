package com.pavastudios.TomMaso.access;

import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.storage.model.Utente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashSet;

public class Session {

    public static final String SESSION_FIELD = "session";

    private final HashSet<Integer> blogs = new HashSet<>();
    private Utente utente = null;


    private Session() {
    }

    public static Session loadSession(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession(true);
        Session s = null;
        if (!session.isNew())//restore from httpsession
            s = (Session) session.getAttribute(SESSION_FIELD);
        if (s == null) //create new session
            s = createSession();
        session.setAttribute(SESSION_FIELD, s);
        s.updateUser();//Propaga modifiche all'utente
        return s;
    }

    private static Session createSession() {
        Session s = new Session();
        s.setUtente(null);
        return s;
    }

    private void updateUser() throws SQLException {
        if (!isLogged()) return;
        int idUser = utente.getIdUtente();
        Utente newUtente = UserQueries.findUserById(idUser);
        setUtente(newUtente);
    }

    public boolean hasVisitedBlog(Blog blog) {
        if (blog == null) return false;
        return blogs.contains(blog.getIdBlog());
    }

    public void visitedBlog(Blog blog) throws SQLException {
        if (blog == null) return;
        if (!hasVisitedBlog(blog))
            BlogQueries.incrementVisit(blog);
        blogs.add(blog.getIdBlog());
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


}
