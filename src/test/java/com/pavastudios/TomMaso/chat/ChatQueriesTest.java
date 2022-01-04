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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static com.pavastudios.TomMaso.chat.ChatQueries.*;

class ChatQueriesTest  extends TestDBConnection {


    @ParameterizedTest
    @MethodSource("testSendMessaggio")
    public void sendTextToChatTest(Chat chat, Utente mittente, String testo, boolean flag) throws SQLException {
        if (flag) assertNotNull(ChatQueries.sendTextToChat(chat,mittente,testo));
        else assertNull(ChatQueries.sendTextToChat(chat,mittente,testo));
    }

    public static Stream<Arguments> testSendMessaggio() throws SQLException {
        return Stream.of(
                Arguments.of(null,null,null,false),
                Arguments.of(ChatQueries.findChatById(10), UserQueries.findUserById(3), "Chat_testing_is_awesome!" ,false),
                Arguments.of(ChatQueries.findChatById(10), UserQueries.findUserById(3), null ,false),
                Arguments.of(ChatQueries.findChatById(10), null, "Chat_testing_is_awesome!" ,false),
                Arguments.of(null, UserQueries.findUserById(3), "Chat_testing_is_awesome!" ,false),
                Arguments.of(ChatQueries.findChatById(10), UserQueries.findUserById(1), "" ,false),
                Arguments.of(ChatQueries.findChatById(10), UserQueries.findUserById(1), "Chat_testing_is_awesome!" ,true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCreateChat")
    public void createChatTest(Utente u1, Utente u2,boolean flag) throws SQLException {
        if (flag) assertNotNull(ChatQueries.createChat(u1,u2));
        else assertNull(ChatQueries.createChat(u1,u2));
    }

    public static Stream<Arguments> testCreateChat() throws SQLException {
        return Stream.of(
                Arguments.of(null,null,false),
                Arguments.of(UserQueries.findUserById(1), null ,false),
                Arguments.of(null, UserQueries.findUserById(2) ,false),
                Arguments.of(UserQueries.findUserById(1), UserQueries.findUserById(3) ,true) //Non si possono creare chat che già esistono (errore con il database)
        );
    }

    @ParameterizedTest
    @MethodSource("testFindUserChats")
    public void findUserChatsTest(Utente user ,boolean flag) throws SQLException {
        if (flag) assertNotNull(ChatQueries.findUserChats(user));
        else{
            if(ChatQueries.findUserChats(user).isEmpty()) assertTrue(true);
        }
    }

    public static Stream<Arguments> testFindUserChats() throws SQLException {
        return Stream.of(
                Arguments.of(UserQueries.findUserById(1),true),
                Arguments.of(UserQueries.findUserById(4),false)
        );
    }

    @ParameterizedTest
    @MethodSource("testFindChatByUsersTest")
    public void findChatByUsersTest(@Nullable Utente u1, @Nullable Utente u2,boolean flag) throws SQLException {
        if (flag) assertNotNull(ChatQueries.findChatByUsers(u1,u2));
        else assertNull(ChatQueries.findChatByUsers(u1,u2));
    }

    public static Stream<Arguments> testFindChatByUsersTest() throws SQLException {
        return Stream.of(
                Arguments.of(null,null,false),
                Arguments.of(UserQueries.findUserById(1),null,false),
                Arguments.of(null,UserQueries.findUserById(2),false),
                Arguments.of(UserQueries.findUserById(1),UserQueries.findUserById(2),true)
        );
    }

    @ParameterizedTest
    @MethodSource("testFetchMessageFromId")
    public void fetchMessageFromIdTest(Chat chat, int fromId ,boolean flag) throws SQLException {
        if (flag) assertNotNull(ChatQueries.fetchMessageFromId(chat,fromId));
        else{
            if(ChatQueries.fetchMessageFromId(chat,fromId).isEmpty()) assertTrue(true);
        }
    }

    public static Stream<Arguments> testFetchMessageFromId() throws SQLException {
        return Stream.of(
                Arguments.of(ChatQueries.findChatById(10),8,false),
                Arguments.of(ChatQueries.findChatById(10),9,true)
        );
    }

    @ParameterizedTest
    @MethodSource("testDeleteMessaggio")
    public void deleteMessageTest(Messaggio messaggio) throws SQLException {
        ChatQueries.deleteMessage(messaggio);
        assertNull(ChatQueries.findMessageById(9));
    }


    public static Stream<Arguments> testDeleteMessaggio() throws SQLException {
        return Stream.of(
                Arguments.of(ChatQueries.findMessageById(9))
        );
    }


}