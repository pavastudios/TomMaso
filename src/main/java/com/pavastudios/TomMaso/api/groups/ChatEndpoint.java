package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiGroup;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.ApiParam;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;

import java.sql.SQLException;
import java.util.List;

public class ChatEndpoint {
    public static final String GROUP_NAME = "chat";
    private static final String FETCH_ENDPOINT_NAME = "fetch-chat";
    private static final String FETCH_FROM_ID_ENDPOINT_NAME = "fetch-from-id";
    private static final String CREATE_ENDPOINT_NAME = "create-chat";
    private static final String SEND_MESSAGE = "send-message";
    private static final ApiEndpoint.Manage SEND_ACTION= (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        String message = parser.getValueString("message");
        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            writer.name(ApiManager.ERROR_PROP).value("invalid chat-id");
            return;
        }
        Messaggio m=Queries.sendTextToChat(chat,user,message);
        if (m==null) {
            writer.name(ApiManager.ERROR_PROP).value("impossibile inviare messaggio");
            return;
        }
        writer.name(ApiManager.OK_PROP);
        m.writeJson(writer);
    };
    private static final ApiEndpoint.Manage CREATE_ACTION = (parser, writer, user) -> {
        String otherUsername=parser.getValueString("with");
        Utente other=Queries.findUserByUsername(otherUsername);
        Chat chat;
        if(other==null){
            writer.name(ApiManager.ERROR_PROP).value("Invalid username");
            return;
        }
        try {
            chat = Queries.createChat(user, other);
        }catch(SQLException ignore){
            writer.name(ApiManager.ERROR_PROP).value("Chat già esistente");
            return;
        }
        writer.name(ApiManager.OK_PROP);
        chat.writeJson(writer);
    };
    private static final ApiEndpoint.Manage FETCH_FROM_ID_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int fromId = parser.getValueInt("from-id");
        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            writer.name(ApiManager.ERROR_PROP).value("invalid chat-id");
            return;
        }

        List<Messaggio> messaggi = Queries.fetchMessageFromId(chat, fromId);
        writer.name(ApiManager.OK_PROP);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };
    private static final ApiEndpoint.Manage FETCH_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int count = parser.getValueInt("count");
        int offset = parser.getValueInt("offset");

        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            writer.name(ApiManager.ERROR_PROP).value("invalid chat-id");
            return;
        }

        List<Messaggio> messaggi = Queries.fetchMessages(chat, count, offset);
        writer.name(ApiManager.OK_PROP);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };

    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
        new ApiEndpoint(FETCH_ENDPOINT_NAME, true, FETCH_ACTION,
                new ApiParam("chat-id", ApiParam.Type.INT),
                new ApiParam("count", ApiParam.Type.INT, 25),
                new ApiParam("offset", ApiParam.Type.INT, 0)
        ),new ApiEndpoint(CREATE_ENDPOINT_NAME, true, CREATE_ACTION,
            new ApiParam("with", ApiParam.Type.STRING)
        ),new ApiEndpoint(FETCH_FROM_ID_ENDPOINT_NAME, true, FETCH_FROM_ID_ACTION,
            new ApiParam("chat-id", ApiParam.Type.INT),
            new ApiParam("from-id", ApiParam.Type.INT)
        ),new ApiEndpoint(SEND_MESSAGE, true, SEND_ACTION,
            new ApiParam("chat-id", ApiParam.Type.INT),
            new ApiParam("message", ApiParam.Type.STRING)
        )
    );

}
