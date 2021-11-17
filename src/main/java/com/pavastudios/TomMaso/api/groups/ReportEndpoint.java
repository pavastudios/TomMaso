package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.entities.CommentQueries;
import com.pavastudios.TomMaso.db.queries.entities.ReportQueries;
import com.pavastudios.TomMaso.model.*;
import com.pavastudios.TomMaso.utility.FileUtility;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Locale;

public class ReportEndpoint {
    @Endpoint(url = "/report/comment", params = {
            @ApiParameter(name = "id-comment", type = ApiParam.Type.INT),
            @ApiParameter(name = "reason", type = ApiParam.Type.STRING),
    }, requireLogin = true)
    public static final ApiEndpoint.Manage COMMENT_REPORT = (parser, writer, user) -> {
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
        writer.name(ApiManager.OK_PROP);
        report.writeJson(writer);
    };

    @Endpoint(url = "/report/post", params = {
            @ApiParameter(name = "url-post", type = ApiParam.Type.STRING),
            @ApiParameter(name = "reason", type = ApiParam.Type.STRING),
    }, requireLogin = true)
    public static final ApiEndpoint.Manage POST_REPORT = (parser, writer, user) -> {
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
        writer.name(ApiManager.OK_PROP);
        report.writeJson(writer);
    };

    @Endpoint(url = "/report/message", params = {
            @ApiParameter(name = "id-message", type = ApiParam.Type.INT),
            @ApiParameter(name = "reason", type = ApiParam.Type.STRING),
    }, requireLogin = true)
    public static final ApiEndpoint.Manage MESSAGE_REPORT = (parser, writer, user) -> {
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
        writer.name(ApiManager.OK_PROP);
        report.writeJson(writer);
    };
}
