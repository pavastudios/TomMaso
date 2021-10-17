package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiException;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.Endpoint;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.listeners.MainListener;
import com.pavastudios.TomMaso.model.Commento;
import com.pavastudios.TomMaso.utility.FileUtility;

import java.io.File;
import java.net.URLDecoder;

@SuppressWarnings("unused")
public class CommentEndpoint {
    @Endpoint("/comment/send-comment")
    public static final ApiEndpoint.Manage SEND_ACTION = (parser, writer, user) -> {
        String comment = parser.getValueString("comment");
        String page = URLDecoder.decode(parser.getValueString("page"), "UTF-8");
        String contextPath = MainListener.CONTEXT.getContextPath() + "/blogs";
        if (comment.length() < 3) {
            throw new ApiException(400, "Messaggio troppo breve");
        }
        if (!page.startsWith(contextPath)) {
            throw new ApiException(400, "Percorso invalido");
        }
        page = page.substring(contextPath.length());
        File file = FileUtility.blogPathToFile(page);
        FileUtility.FileType fileType = FileUtility.getFileType(MainListener.CONTEXT, file);
        if (fileType == null) {
            throw new ApiException(400, "Pagina invalida");
        }
        Commento com = Queries.sendComment(user, comment, page);
        if (com == null) {
            throw new ApiException(400, "Problema invio messaggio");
        }
        writer.name(ApiManager.OK_PROP);
        com.writeJson(writer);
    };

}
