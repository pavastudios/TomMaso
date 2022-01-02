package com.pavastudios.TomMaso.access;

import com.pavastudios.TomMaso.blog.visualization.BlogVisualizationQueries;
import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.storage.model.Utente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashSet;

public class Session {

    public static final String SESSION_FIELD = "session";

    private final HashSet<Integer> visitedBlogs = new HashSet<>();
    private Utente loggedUser = null;


    private Session() {
    }

    public Session(Utente u) {
        loggedUser = u;
    }

    public static Session loadSession(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession(true);
        Session s = (Session) session.getAttribute(SESSION_FIELD);
        if (s == null) //create new session
            s = new Session();
        session.setAttribute(SESSION_FIELD, s);
        s.updateUser();//Propaga modifiche all'utente
        return s;
    }

    private void updateUser() throws SQLException {
        if (!isLogged()) return;
        int idUser = loggedUser.getIdUtente();
        Utente newUtente = UserQueries.findUserById(idUser);
        // User has been banned
        if (newUtente != null && !newUtente.getPermessi().hasPermissions(Utente.Permessi.CAN_LOGIN))
            newUtente = null;
        setUtente(newUtente);
    }

    private boolean hasVisitedBlog(Blog blog) {
        if (blog == null) return false;
        return visitedBlogs.contains(blog.getIdBlog());
    }

    public void visitedBlog(Blog blog) throws SQLException {
        if (blog == null) return;
        if (!hasVisitedBlog(blog))
            BlogVisualizationQueries.incrementVisit(blog);
        visitedBlogs.add(blog.getIdBlog());
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

    public Utente getUtente() {
        return loggedUser;
    }

    public Session setUtente(Utente utente) {
        this.loggedUser = utente;
        return this;
    }


}
