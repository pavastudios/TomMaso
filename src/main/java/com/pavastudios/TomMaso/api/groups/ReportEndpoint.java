package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Commento;

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

        String url = String.format(Locale.US, "/blogs%s#comment-%d", commento.getPagina(), commento.getIdCommento());
    };
}
