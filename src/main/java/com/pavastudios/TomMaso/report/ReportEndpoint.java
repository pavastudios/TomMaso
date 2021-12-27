package com.pavastudios.TomMaso.report;

import com.pavastudios.TomMaso.api.ApiEndpoint;
import com.pavastudios.TomMaso.api.ApiException;
import com.pavastudios.TomMaso.api.Endpoint;
import com.pavastudios.TomMaso.blog.visualization.CommentQueries;
import com.pavastudios.TomMaso.storage.FileUtility;
import com.pavastudios.TomMaso.storage.model.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Locale;

@SuppressWarnings("unused")
public class ReportEndpoint {
    @Endpoint(url = "/report/comment", requireLogin = true)
    private static final ApiEndpoint.Manage COMMENT_REPORT = (parser, writer, user) -> {
        int idCommento = parser.getValueInt("id-comment");
        String reason = parser.getValueString("reason");

        Commento commento = CommentQueries.findCommentById(idCommento);
        if (commento == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "commento non trovato");
        }
        if (user.equals(commento.getMittente())) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Non è possibile segnalare un proprio commento");
        }

        String url = String.format(Locale.US, "/blogs%s#comment-%d",
                commento.getPagina(),
                commento.getIdCommento()
        );
        Report report = ReportQueries.report(Report.Type.COMMENT, user, url, reason, commento.getMittente());
        report.writeJson(writer);
    };

    @Endpoint(url = "/report/post", requireLogin = true)
    private static final ApiEndpoint.Manage POST_REPORT = (parser, writer, user) -> {
        String post = parser.getValueString("url-post");
        String reason = parser.getValueString("reason");
        String pathInfo = post.substring(6);
        if (!post.startsWith("/blogs/")) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid Url");
        }
        Blog blog = Blog.fromPathInfo(pathInfo);
        File file = FileUtility.blogPathToFile(pathInfo);
        if (blog == null || !file.exists()) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid Url");
        }
        if (user.equals(blog.getProprietario())) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Non è possibile segnalare un proprio post");
        }
        Report report = ReportQueries.report(Report.Type.POST, user, post, reason, blog.getProprietario());
        report.writeJson(writer);
    };


    @Endpoint(url = "/report/review", requireLogin = true)
    private static final ApiEndpoint.Manage REPORT_REVIEWED = (parser, writer, user) -> {
        Report report = ReportQueries.findReportById(parser.getValueInt("id-report"));
        Report.Status approved = parser.getValueBool("approved") ? Report.Status.ACCEPTED : Report.Status.REJECTED;
        boolean chatReport = report.getType() == Report.Type.CHAT;
        if (chatReport && !user.getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) {
            throw new ApiException(HttpServletResponse.SC_FORBIDDEN, "can't moderate this report");
        }
        if (!user.getPermessi().hasPermissions(Utente.Permessi.MOD_BLOG)) {
            throw new ApiException(HttpServletResponse.SC_FORBIDDEN, "can't moderate this report");
        }
        if (approved == Report.Status.ACCEPTED) {
            report.deleteContent();
        }
        ReportQueries.reviewReport(report, approved);
        writer.value("ok");
    };

    @Endpoint(url = "/report/message", requireLogin = true)
    private static final ApiEndpoint.Manage MESSAGE_REPORT = (parser, writer, user) -> {
        int idMessage = parser.getValueInt("id-message");
        String reason = parser.getValueString("reason");

        Messaggio messaggio = CommentQueries.findMessageById(idMessage);
        if (messaggio == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "messaggio non trovato");
        }
        Chat chat = messaggio.getChat();
        if (!chat.isPartecipant(user)) {
            throw new ApiException(HttpServletResponse.SC_FORBIDDEN, "utente non partecipante");
        }
        if (user.equals(messaggio.getMittente())) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Non è possibile segnalare un proprio messaggio");
        }
        String url = String.format(Locale.US, "/chat/%s/%s#m-%d",
                chat.getUtente1().getUsername(),
                chat.getUtente2().getUsername(),
                messaggio.getIdMessaggio()
        );
        Report report = ReportQueries.report(Report.Type.CHAT, user, url, reason, messaggio.getMittente());
        report.writeJson(writer);
    };
}
