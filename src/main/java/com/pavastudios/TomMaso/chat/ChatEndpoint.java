package com.pavastudios.TomMaso.chat;

import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.api.ApiException;
import com.pavastudios.TomMaso.api.ApiParser;
import com.pavastudios.TomMaso.api.ApiWriter;
import com.pavastudios.TomMaso.api.Endpoint;
import com.pavastudios.TomMaso.storage.model.Chat;
import com.pavastudios.TomMaso.storage.model.Messaggio;
import com.pavastudios.TomMaso.storage.model.Utente;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")

public class ChatEndpoint {
    @Endpoint(url = "/chat/send-message", requireLogin = true)
    private static void sendMessage(ApiParser parser, ApiWriter writer, Utente user) throws Exception {
        int chatId = parser.getValueInt("chat-id");
        String message = parser.getValueString("message");
        Chat chat = ChatQueries.findChatById(chatId);
        if (chat == null || !chat.isPartecipant(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "invalid chat-id");
        }
        if(message.isEmpty()){
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "messages can't be empty");
        }
        Messaggio m = ChatQueries.sendTextToChat(chat, user, message);
        if (m == null) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "impossibile inviare messaggio");
        }
        m.writeJson(writer);
    };

    @Endpoint(url = "/chat/create-chat", requireLogin = true)
    private static void createChat(ApiParser parser, ApiWriter writer, Utente user) throws Exception {
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

    @Endpoint(url = "/chat/fetch-from-id", requireLogin = true)
    private static void fetchMessagesFromId(ApiParser parser, ApiWriter writer, Utente user) throws Exception {
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

    @Endpoint(url = "/chat/fetch-chat", requireLogin = true)
    private static void getAllChatMessages(ApiParser parser, ApiWriter writer, Utente user) throws Exception {
        int chatId = parser.getValueInt("chat-id");

        Chat chat = ChatQueries.findChatById(chatId);
        if (chat == null || !chat.hasAccess(user)) {
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "invalid chat-id");
        }

        List<Messaggio> messaggi = ChatQueries.fetchMessageFromId(chat, 0);
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();
    };

}
