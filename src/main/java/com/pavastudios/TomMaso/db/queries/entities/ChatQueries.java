package com.pavastudios.TomMaso.db.queries.entities;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Chat;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class ChatQueries {
    public static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    public static MasterPreparedStatement FIND_CHAT_BY_ID;
    static MasterPreparedStatement CREATE_CHAT;
    static MasterPreparedStatement SEND_MESSAGE;
    static MasterPreparedStatement FETCH_CHAT_MESSAGE;
    static MasterPreparedStatement FIND_USER_CHAT;
    static MasterPreparedStatement FIND_CHAT_BY_USERS;
    static MasterPreparedStatement FETCH_MESSAGE_FROM_ID;


    public static void initQueries() throws SQLException {
        FETCH_CHAT_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_chat`=? ORDER BY `data_invio` DESC LIMIT ? OFFSET ?");
        FETCH_MESSAGE_FROM_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_chat`=? AND `id_messaggio`>? ORDER BY `data_invio`");
        FIND_USER_CHAT = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? OR `utente2`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_CHAT_BY_USERS = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? AND `utente2`=? ");
        CREATE_CHAT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Chat`(`utente1`,`utente2`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        SEND_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Messaggio`(`id_chat`,`mittente`,`testo`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
    }

    public static List<Messaggio> fetchMessages(Chat chat, int amount, int offset) throws SQLException {
        FETCH_CHAT_MESSAGE.setInt(1, chat.getIdChat());
        FETCH_CHAT_MESSAGE.setInt(2, amount);
        FETCH_CHAT_MESSAGE.setInt(3, offset);
        ResultSet rs = FETCH_CHAT_MESSAGE.executeQuery();
        List<Messaggio> messages = Queries.resultSetToList(Entities.MESSAGGIO, rs);
        Collections.reverse(messages);
        rs.close();
        return messages;
    }

    public static @Nullable Messaggio sendTextToChat(Chat chat, Utente mittente, String testo) throws SQLException {
        if (chat == null || mittente == null || testo == null) return null;
        SEND_MESSAGE.setInt(1, chat.getIdChat());
        SEND_MESSAGE.setInt(2, mittente.getIdUtente());
        SEND_MESSAGE.setString(3, testo);
        SEND_MESSAGE.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(SEND_MESSAGE);
        return CommentQueries.findMessageById(id);
    }

    public static Chat createChat(Utente u1, Utente u2) throws SQLException {
        Utente tmp;
        if (u1 == null || u2 == null) return null;

        if (u1.getIdUtente() > u2.getIdUtente()) {//in ordine di id
            tmp = u1;
            u1 = u2;
            u2 = tmp;
        }

        CREATE_CHAT.setInt(1, u1.getIdUtente());
        CREATE_CHAT.setInt(2, u2.getIdUtente());
        CREATE_CHAT.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(CREATE_CHAT);
        return findChatById(id);
    }

    //Query Chat
    public static @Nullable Chat findChatById(int idChat) throws SQLException {
        return Queries.findById(Entities.CHAT, idChat);
    }

    public static @NotNull List<Chat> findUserChat(Utente u1) throws SQLException {
        FIND_USER_CHAT.setInt(1, u1.getIdUtente());
        FIND_USER_CHAT.setInt(2, u1.getIdUtente());
        ResultSet rs = FIND_USER_CHAT.executeQuery();
        List<Chat> chats = Queries.resultSetToList(Entities.CHAT, rs);
        rs.close();
        return chats;
    }

    public static @Nullable Chat findChatByUsers(@Nullable Utente u1, @Nullable Utente u2) throws SQLException {
        if (u1 == null || u2 == null) return null;
        if (u1.getIdUtente() > u2.getIdUtente()) {
            Utente temp = u1;
            u1 = u2;
            u2 = temp;
        }
        FIND_CHAT_BY_USERS.setInt(1, u1.getIdUtente());
        FIND_CHAT_BY_USERS.setInt(2, u2.getIdUtente());
        ResultSet rs = FIND_CHAT_BY_USERS.executeQuery();
        rs.first();
        Chat chat = Queries.resultSetToModel(Entities.CHAT, rs);
        rs.close();
        return chat;
    }

    public static List<Messaggio> fetchMessageFromId(Chat chat, int fromId) throws SQLException {
        FETCH_MESSAGE_FROM_ID.setInt(1, chat.getIdChat());
        FETCH_MESSAGE_FROM_ID.setInt(2, fromId);
        ResultSet set = FETCH_MESSAGE_FROM_ID.executeQuery();
        List<Messaggio> messages = Queries.resultSetToList(Entities.MESSAGGIO, set);
        set.close();
        return messages;
    }
}
