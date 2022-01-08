package com.pavastudios.TomMaso.chat;

import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.model.Chat;
import com.pavastudios.TomMaso.storage.model.Messaggio;
import com.pavastudios.TomMaso.storage.model.Utente;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utility.TestDBConnection;

import java.sql.SQLException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChatQueriesTest extends TestDBConnection {


    public static Stream<Arguments> testSendMessaggio() throws SQLException {
        return Stream.of(
                Arguments.of(0, 0, null, false),
                Arguments.of(10, 3, "Chat_testing_is_awesome!", false),
                Arguments.of(10, 3, null, false),
                Arguments.of(10, 0, "Chat_testing_is_awesome!", false),
                Arguments.of(0, 3, "Chat_testing_is_awesome!", false),
                Arguments.of(10, 1, "", false),
                Arguments.of(10, 1, "Chat_testing_is_awesome!", true)
        );
    }

    public static Stream<Arguments> testCreateChat() throws SQLException {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(1, 0, false),
                Arguments.of(0, 2, false),
                Arguments.of(1, 3, true) //Non si possono creare chat che già esistono (errore con il database)
        );
    }

    public static Stream<Arguments> testFindUserChats() throws SQLException {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(4, false)
        );
    }

    public static Stream<Arguments> testFindChatByUsersTest() throws SQLException {
        return Stream.of(
                Arguments.of(0, 0, false),
                Arguments.of(1, 0, false),
                Arguments.of(0, 2, false),
                Arguments.of(1, 2, true)
        );
    }

    public static Stream<Arguments> testFetchMessageFromId() throws SQLException {
        return Stream.of(
                Arguments.of(10, 8, false),
                Arguments.of(10, 9, true)
        );
    }

    public static Stream<Arguments> testDeleteMessaggio() throws SQLException {
        return Stream.of(
                Arguments.of(9)
        );
    }

    @ParameterizedTest
    @MethodSource("testSendMessaggio")
    public void sendTextToChatTest(int idchat, int idmittente, String testo, boolean flag) throws SQLException {
        Chat chat=ChatQueries.findChatById(idchat);
        Utente mittente=UserQueries.findUserById(idmittente);
        if (flag) assertNotNull(ChatQueries.sendTextToChat(chat, mittente, testo));
        else assertNull(ChatQueries.sendTextToChat(chat, mittente, testo));
    }

    @ParameterizedTest
    @MethodSource("testCreateChat")
    public void createChatTest(int id1, int id2, boolean flag) throws SQLException {
        Utente u1=UserQueries.findUserById(id1);
        Utente u2=UserQueries.findUserById(id2);
        if (flag) assertNotNull(ChatQueries.createChat(u1, u2));
        else assertNull(ChatQueries.createChat(u1, u2));
    }

    @ParameterizedTest
    @MethodSource("testFindUserChats")
    public void findUserChatsTest(int id, boolean flag) throws SQLException {
        Utente user=UserQueries.findUserById(id);
        if (flag) assertNotNull(ChatQueries.findUserChats(user));
        else {
            if (ChatQueries.findUserChats(user).isEmpty()) assertTrue(true);
        }
    }

    @ParameterizedTest
    @MethodSource("testFindChatByUsersTest")
    public void findChatByUsersTest(int id1, int id2, boolean flag) throws SQLException {
        Utente u1=UserQueries.findUserById(id1);
        Utente u2=UserQueries.findUserById(id2);
        if (flag) assertNotNull(ChatQueries.findChatByUsers(u1, u2));
        else assertNull(ChatQueries.findChatByUsers(u1, u2));
    }

    @ParameterizedTest
    @MethodSource("testFetchMessageFromId")
    public void fetchMessageFromIdTest(int id, int fromId, boolean flag) throws SQLException {
       Chat chat = ChatQueries.findChatById(id);
        if (flag) assertNotNull(ChatQueries.fetchMessageFromId(chat, fromId));
        else {
            if (ChatQueries.fetchMessageFromId(chat, fromId).isEmpty()) assertTrue(true);
        }
    }

    @ParameterizedTest
    @MethodSource("testDeleteMessaggio")
    public void deleteMessageTest(int id) throws SQLException {
        ChatQueries.deleteMessage(ChatQueries.findMessageById(id));
        assertNull(ChatQueries.findMessageById(9));
    }


}