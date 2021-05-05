package com.pavastudios.TomMaso.servlets;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.mail.MailSender;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Session;
import com.pavastudios.TomMaso.utility.Utility;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ForgotPassword", value = "/forgot")
public class ForgotServlet extends MasterServlet {

    @Override
    protected void doGet(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'code' parameter");
            return;
        }
        try {
            Utility.fromHexString(code);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'code' parameter");
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/forgot.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        if (req.getParameter("code") != null) {
            changePassword(session, req, resp);
            return;
        }
        String email = req.getParameter("email");
        String code = Queries.forgetPassword(email);
        if (code == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email non valida");
            return;
        }
        try {
            MailSender.sendForgotPassword(email, code);
        } catch (MessagingException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile inviare email");
            return;
        }
        Utility.returnHome(req, resp);
    }

    private void changePassword(Session session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String code = req.getParameter("code");
        try {
            Utility.fromHexString(code);
        } catch (NumberFormatException ignore) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'code'");
            return;
        }
        if (password1 == null || !password1.equals(password2)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password diverse");
            return;
        }
        Utente user = Queries.findUserFromForgot(code);
        try {
            Queries.changePassword(user, password1);
            Queries.deleteForget(code);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile cambiare password");
            return;
        }
        Utility.returnHome(req, resp);
    }
}
