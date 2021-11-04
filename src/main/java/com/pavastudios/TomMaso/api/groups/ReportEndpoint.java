package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Commento;
import com.pavastudios.TomMaso.model.Messaggio;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ReportEndpoint {
    @Endpoint(url = "/report/comment", params = {
            @ApiParameter(name = "id-comment", type = ApiParam.Type.INT),
            @ApiParameter(name = "reason", type = ApiParam.Type.STRING),
    }, requireLogin = true)
    public static final ApiEndpoint.Manage COMMENT_REPORT = (parser, writer, user) -> {
        int idCommento = parser.getValueInt("id-comment");
        String reason = parser.getValueString("reason");

        Commento commento = Queries.findCommentById(idCommento);
        if (commento == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "commento non trovato");
        }

        String url = String.format(Locale.US, "/blogs%s#comment-%d",
                commento.getPagina(),
                commento.getIdCommento()
        );
    };
    @Endpoint(url = "/report/message", params = {
            @ApiParameter(name = "id-message", type = ApiParam.Type.INT),
            @ApiParameter(name = "reason", type = ApiParam.Type.STRING),
    }, requireLogin = true)
    public static final ApiEndpoint.Manage MESSAGE_REPORT = (parser, writer, user) -> {
        int idMessage = parser.getValueInt("id-message");
        String reason = parser.getValueString("reason");

        Messaggio messaggio = Queries.findMessageById(idMessage);
        if (messaggio == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "messaggio non trovato");
        }
        Chat chat = messaggio.getChat();
        if (!chat.isPartecipant(user)) {
            throw new ApiException(HttpServletResponse.SC_FORBIDDEN, "utente non partecipante");
        }
        String url = String.format(Locale.US, "/chat/%s/%s#m-%d",
                chat.getUtente1().getUsername(),
                chat.getUtente2().getUsername(),
                messaggio.getIdMessaggio()
        );
    };
}
