package com.pavastudios.TomMaso.blog.visualization;

import com.pavastudios.TomMaso.api.ApiEndpoint;
import com.pavastudios.TomMaso.api.ApiException;
import com.pavastudios.TomMaso.api.ApiParameter;
import com.pavastudios.TomMaso.api.Endpoint;
import com.pavastudios.TomMaso.general.MainListener;
import com.pavastudios.TomMaso.storage.FileUtility;
import com.pavastudios.TomMaso.storage.model.Commento;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
@SuppressWarnings("unused")

public class CommentEndpoint {
    @Endpoint(url = "/comment/send-comment", requireLogin = true, params = {
            @ApiParameter(name = "comment", type = ApiParameter.Type.STRING),
            @ApiParameter(name = "page", type = ApiParameter.Type.STRING)
    })
    private static final ApiEndpoint.Manage SEND_ACTION = (parser, writer, user) -> {
        String comment = parser.getValueString("comment");
        String page = URLDecoder.decode(parser.getValueString("page"), "UTF-8");
        String contextPath = MainListener.CONTEXT.getContextPath() + "/blogs";
        if (comment.length() < 3) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Messaggio troppo breve");
        }
        if (!page.startsWith(contextPath)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Percorso invalido");
        }
        page = page.substring(contextPath.length());
        File file = FileUtility.blogPathToFile(page);
        FileUtility.FileType fileType = FileUtility.getFileType(MainListener.CONTEXT, file);
        if (fileType == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Pagina invalida");
        }
        Commento com = CommentQueries.sendComment(user, comment, page);
        if (com == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Problema invio messaggio");
        }
        com.writeJson(writer);
    };

}
