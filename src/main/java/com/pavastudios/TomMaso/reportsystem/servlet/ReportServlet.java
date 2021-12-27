package com.pavastudios.TomMaso.reportsystem.servlet;

import com.pavastudios.TomMaso.accesssystem.Session;
import com.pavastudios.TomMaso.reportsystem.ReportQueries;
import com.pavastudios.TomMaso.storagesystem.model.Report;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.MasterServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classe che mostra agli utenti con permessi da moderatore
 * i report senza review e consente loro di gestirli
 */
public class ReportServlet extends MasterServlet {

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Utente user = session.getUtente();
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Must be logged");
            return;
        }
        if (!user.getPermessi().hasPermissions(Utente.Permessi.MOD_BLOG) && !user.getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Only moderators can access");
            return;
        }
        List<Report> reports = new ArrayList<>();
        if (user.getPermessi().hasPermissions(Utente.Permessi.MOD_BLOG)) {
            reports.addAll(ReportQueries.fetchUnreviewedOfType(Report.Type.POST));
            reports.addAll(ReportQueries.fetchUnreviewedOfType(Report.Type.COMMENT));
        }
        if (user.getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) {
            reports.addAll(ReportQueries.fetchUnreviewedOfType(Report.Type.CHAT));
        }
        reports.sort(Comparator.comparingInt(Report::getIdReport));
        req.setAttribute("REPORTS", reports);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/bootstrap/admin/reportsViewer.jsp").forward(req, resp);
    }
}
