package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiGroup;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.ApiParam;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.listeners.MainListener;
import com.pavastudios.TomMaso.model.Commento;
import com.pavastudios.TomMaso.utility.FileUtility;

import java.io.File;

public class CommentEndpoint {
    public static final String GROUP_NAME = "comment";
    private static final String SEND_ACTION_NAME = "send-comment";
    private static final ApiEndpoint.Manage SEND_ACTION= (parser, writer, user) -> {
        String comment=parser.getValueString("comment");
        String page=parser.getValueString("page");
        String contextPath= MainListener.CONTEXT.getContextPath()+"/blogs";
        if(comment.length()<3){
            writer.name(ApiManager.ERROR_PROP).value("Messaggio troppo breve");
            return;
        }
        if(!page.startsWith(contextPath)){
            writer.name(ApiManager.ERROR_PROP).value("Percorso invalido");
            return;
        }
        page=page.substring(contextPath.length());
        File file= FileUtility.blogPathToFile(page);
        FileUtility.FileType fileType=FileUtility.getFileType(MainListener.CONTEXT,file);
        if(fileType==null){
            writer.name(ApiManager.ERROR_PROP).value("Pagina invalida");
            return;
        }
        Commento com= Queries.sendComment(user,comment,page);
        if(com==null){
            writer.name(ApiManager.ERROR_PROP).value("Problema invio messaggio");
            return;
        }
        writer.name(ApiManager.OK_PROP);
        com.writeJson(writer);
        return;
    };
    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
            new ApiEndpoint(SEND_ACTION_NAME, true, SEND_ACTION,
                    new ApiParam("comment", ApiParam.Type.STRING),
                    new ApiParam("page", ApiParam.Type.STRING)
            )
    );
}
