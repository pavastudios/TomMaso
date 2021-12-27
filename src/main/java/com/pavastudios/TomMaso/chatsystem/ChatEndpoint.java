package com.pavastudios.TomMaso.chatsystem;

import com.pavastudios.TomMaso.accesssystem.UserQueries;
import com.pavastudios.TomMaso.apisystem.ApiEndpoint;
import com.pavastudios.TomMaso.apisystem.ApiException;
import com.pavastudios.TomMaso.apisystem.ApiParameter;
import com.pavastudios.TomMaso.apisystem.Endpoint;
import com.pavastudios.TomMaso.storagesystem.model.Chat;
import com.pavastudios.TomMaso.storagesystem.model.Messaggio;
import com.pavastudios.TomMaso.storagesystem.model.Utente;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")

public class ChatEndpoint {
    @Endpoint(url = "/chat/send-message", requireLogin = true, params = {
            @ApiParameter(name = "chat-id", type = ApiParameter.Type.INT),
            @ApiParameter(name = "message", type = ApiParameter.Type.STRING),
    })
    private static final ApiEndpoint.Manage SEND_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        String message = parser.getValueString("message");
        Chat chat = ChatQueries.findChatById(chatId);
        if (chat == null || !chat.isPartecipant(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "invalid chat-id");
        }
        Messaggio m = ChatQueries.sendTextToChat(chat, user, message);
        if (m == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "impossibile inviare messaggio");
        }
        m.writeJson(writer);
    };
    @Endpoint(url = "/chat/create-chat", requireLogin = true, params = {
            @ApiParameter(name = "with", type = ApiParameter.Type.STRING)
    })
    private static final ApiEndpoint.Manage CREATE_ACTION = (parser, writer, user) -> {
        String otherUsername = parser.getValueString("with");
        Utente other = UserQueries.findUserByUsername(otherUsername);
        Chat chat;
        if (other == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Invalid username");
        }
        try {
            chat = ChatQueries.createChat(user, other);
        } catch (SQLException ignore) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Chat già esistente");
        }
        chat.writeJson(writer);
    };
    @Endpoint(url = "/chat/fetch-from-id", requireLogin = true, params = {
            @ApiParameter(name = "chat-id", type = ApiParameter.Type.INT),
            @ApiParameter(name = "from-id", type = ApiParameter.Type.INT),
    })
    private static final ApiEndpoint.Manage FETCH_FROM_ID_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int fromId = parser.getValueInt("from-id");
        Chat chat = ChatQueries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "invalid chat-id");
        }

        List<Messaggio> messaggi = ChatQueries.fetchMessageFromId(chat, fromId);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };
    @Endpoint(url = "/chat/fetch-chat", requireLogin = true, params = {
            @ApiParameter(name = "chat-id", type = ApiParameter.Type.INT),
            @ApiParameter(name = "count", type = ApiParameter.Type.INT),
            @ApiParameter(name = "offset", type = ApiParameter.Type.INT),
    })
    private static final ApiEndpoint.Manage FETCH_ACTION = (parser, writer, user) -> {
        int chatId = parser.getValueInt("chat-id");
        int count = parser.getValueInt("count");
        int offset = parser.getValueInt("offset");

        Chat chat = ChatQueries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "invalid chat-id");
        }

        List<Messaggio> messaggi = ChatQueries.fetchMessages(chat, count, offset);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };

}
