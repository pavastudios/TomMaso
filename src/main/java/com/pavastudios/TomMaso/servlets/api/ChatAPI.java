package com.pavastudios.TomMaso.servlets.api;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.servlets.MasterServlet;
import com.pavastudios.TomMaso.utility.RememberMeUtility;
import com.pavastudios.TomMaso.utility.api.ApiParam;
import com.pavastudios.TomMaso.utility.api.Endpoint;
import com.pavastudios.TomMaso.utility.api.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "ChatAPI", value = "/api/chat")
public class ChatAPI extends MasterServlet {
    private static final String ERROR_PROP = "error";
    private static final String METHOD_PROP = "method";
    private static final HashMap<String, Endpoint> ENDPOINTS = new HashMap<>();
    private static final String FETCH_ENDPOINT_NAME = "fetch_chat";
    private static final Endpoint FETCH_ENDPOINT = new Endpoint(FETCH_ENDPOINT_NAME, true,
            new ApiParam("chat_id", ApiParam.Type.INT),
            new ApiParam("count", ApiParam.Type.INT, 25),
            new ApiParam("offset", ApiParam.Type.INT, 0)
    );

    static {
        ENDPOINTS.put(FETCH_ENDPOINT_NAME, FETCH_ENDPOINT);
    }

    private static boolean isValidChat(Chat chat, Utente user) {
        return chat != null &&
                (chat.getUtente1().equals(user) || chat.getUtente2().equals(user));
    }

    @Override
    protected void doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        resp.setContentType("text/json");
        Utente user = (Utente) session.getAttribute(RememberMeUtility.SESSION_USER);
        JsonWriter writer = new JsonWriter(resp.getWriter());
        String method = req.getParameter(METHOD_PROP);

        writer.beginObject();
        Endpoint endpoint = ENDPOINTS.get(method);
        Parser parser = manageEndpoint(endpoint, req, writer, user);

        if (parser == null) {
            writer.endObject();
            return;
        }

        switch (endpoint.getEndpoint()) {
            case FETCH_ENDPOINT_NAME:
                manageFetchChat(parser, writer, user);
                break;
            default:
                break;
        }

        writer.endObject();
        writer.flush();
    }

    private Parser manageEndpoint(Endpoint endpoint, HttpServletRequest req, JsonWriter writer, Utente user) throws IOException, SQLException {
        if (endpoint == null) {
            writer.name(ERROR_PROP).value("method not implemented");
            return null;
        }
        if (endpoint.requireLogin() && user == null) {
            writer.name(ERROR_PROP).value("user not authenticated");
            return null;
        }
        Parser parser = new Parser(endpoint, req);
        parser.parse();
        if (parser.hasError()) {
            ApiParam param = parser.getError();
            String errorString = String.format(Locale.US, "invalid param '%s'", param.getName());
            writer.name(ERROR_PROP).value(errorString);
            return null;
        }
        return parser;
    }

    private void manageFetchChat(Parser parser, JsonWriter writer, Utente user) throws IOException, SQLException {
        Chat chat;
        int chatId = parser.getValueInt("chat_id");
        int count = parser.getValueInt("count");
        int offset = parser.getValueInt("offset");
        try {
            chat = Queries.findChatById(chatId);
            if (!isValidChat(chat, user))
                throw new NumberFormatException();
        } catch (NumberFormatException ignore) {
            writer.name(ERROR_PROP).value("invalid chat_id");
            return;
        }

        List<Messaggio> messaggi = Queries.fetchMessages(chat, count, offset);
        writer.name("response");
        writer.beginArray();
        for (Messaggio m : messaggi)
            m.writeJson(writer);
        writer.endArray();

    }
}
