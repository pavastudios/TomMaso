package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.api.*;

import java.util.List;

public class ChatEndpoint {
        public static final String GROUP_NAME="chat";
        private static final String FETCH_ENDPOINT_NAME = "fetch-chat";

        private static final ApiEndpoint.Manage FETCH_ACTION= (parser, writer, user) -> {
            int chatId = parser.getValueInt("chat_id");
            int count = parser.getValueInt("count");
            int offset = parser.getValueInt("offset");

            Chat chat = Queries.findChatById(chatId);
            if (chat==null||!chat.hasAccess(user)){
                writer.name(ApiManager.ERROR_PROP).value("invalid chat_id");
                return;
            }

            List<Messaggio> messaggi = Queries.fetchMessages(chat, count, offset);
            writer.name("response");
            writer.beginArray();
            for (Messaggio m : messaggi)
                m.writeJson(writer);
            writer.endArray();
        };

    public static final ApiGroup ENDPOINTS=new ApiGroup(GROUP_NAME,
                new ApiEndpoint(FETCH_ENDPOINT_NAME, true,FETCH_ACTION,
                        new ApiParam("chat_id", ApiParam.Type.INT),
                        new ApiParam("count", ApiParam.Type.INT, 25),
                        new ApiParam("offset", ApiParam.Type.INT, 0)
                )
        );

}
