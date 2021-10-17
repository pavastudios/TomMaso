package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiException;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.Endpoint;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
public class ChatEndpoint {
    @Endpoint("/chat/send-message")
    public static final ApiEndpoint.Manage SEND_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        String message = parser.getValueString("message");
        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(400, "invalid chat-id");
        }
        Messaggio m = Queries.sendTextToChat(chat, user, message);
        if (m == null) {
            throw new ApiException(400, "impossibile inviare messaggio");
        }
        writer.name(ApiManager.OK_PROP);
        m.writeJson(writer);
    };
    @Endpoint("/chat/create-chat")
    public static final ApiEndpoint.Manage CREATE_ACTION = (parser, writer, user) -> {
        String otherUsername = parser.getValueString("with");
        Utente other = Queries.findUserByUsername(otherUsername);
        Chat chat;
        if (other == null) {
            throw new ApiException(400, "Invalid username");
        }
        try {
            chat = Queries.createChat(user, other);
        } catch (SQLException ignore) {
            throw new ApiException(400, "Chat già esistente");
        }
        writer.name(ApiManager.OK_PROP);
        chat.writeJson(writer);
    };
    @Endpoint("/chat/fetch-from-id")
    public static final ApiEndpoint.Manage FETCH_FROM_ID_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int fromId = parser.getValueInt("from-id");
        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(400, "invalid chat-id");
        }

        List<Messaggio> messaggi = Queries.fetchMessageFromId(chat, fromId);
        writer.name(ApiManager.OK_PROP);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };
    @Endpoint("/chat/fetch-chat")
    public static final ApiEndpoint.Manage FETCH_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int count = parser.getValueInt("count");
        int offset = parser.getValueInt("offset");

        Chat chat = Queries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(400, "invalid chat-id");
        }

        List<Messaggio> messaggi = Queries.fetchMessages(chat, count, offset);
        writer.name(ApiManager.OK_PROP);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };

}
