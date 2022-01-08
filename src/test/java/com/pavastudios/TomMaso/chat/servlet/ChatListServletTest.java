package com.pavastudios.TomMaso.chat.servlet;

import com.pavastudios.TomMaso.access.Session;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.storage.model.Utente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import utility.TestDBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Stream;


class ChatListServletTest extends TestDBConnection {

    private static Stream<Named<Integer>> working() {
        return Stream.of(
                Named.of("Utente esistente", 1)
        );
    }

    private static Stream<Named<Session>> not_working() throws SQLException {
        return Stream.of(
                Named.of("Utente non esistente", new Session(UserQueries.findUserById(2))),
                Named.of("Utente null", new Session(null))
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("working")
    void doGet(int id) throws SQLException {
        Utente u = UserQueries.findUserById(id);
        Session s = new Session(u);
        HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
        HttpSession httpS = Mockito.mock(HttpSession.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ChatListServlet servlet = new ChatListServlet();

        Mockito.when(r.getParameter("username")).thenReturn("admin");
        Mockito.when(r.getSession(true)).thenReturn(httpS);
        Mockito.when(httpS.getAttribute(Session.SESSION_FIELD)).thenReturn(s);

        Assertions.assertThrows(IllegalStateException.class, () -> servlet.doGet(s, r, response));

        Mockito.verify(r).setAttribute(Mockito.eq("chats"), Mockito.any());
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("not_working")
    void doGetFail(Session s) throws SQLException, ServletException, IOException {
        HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
        HttpSession httpS = Mockito.mock(HttpSession.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ChatListServlet servlet = new ChatListServlet();

        Mockito.when(r.getParameter("username")).thenReturn("admin");
        Mockito.when(r.getSession(true)).thenReturn(httpS);
        Mockito.when(httpS.getAttribute(Session.SESSION_FIELD)).thenReturn(s);

        servlet.doGet(s, r, response);

        Mockito.verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Utente non autorizzato");
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("working")
    void doPost(int id) throws SQLException {
        Utente u = UserQueries.findUserById(id);
        Session s = new Session(u);
        HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
        HttpSession httpS = Mockito.mock(HttpSession.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ChatListServlet servlet = new ChatListServlet();

        Mockito.when(r.getParameter("username")).thenReturn("admin");
        Mockito.when(r.getSession(true)).thenReturn(httpS);
        Mockito.when(httpS.getAttribute(Session.SESSION_FIELD)).thenReturn(s);

        Assertions.assertThrows(IllegalStateException.class, () -> servlet.doPost(s, r, response));

        Mockito.verify(r).setAttribute(Mockito.eq("chats"), Mockito.any());
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("not_working")
    void doPostFail(Session s) throws SQLException, ServletException, IOException {
        HttpServletRequest r = Mockito.mock(HttpServletRequest.class);
        HttpSession httpS = Mockito.mock(HttpSession.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        ChatListServlet servlet = new ChatListServlet();

        Mockito.when(r.getParameter("username")).thenReturn("admin");
        Mockito.when(r.getSession(true)).thenReturn(httpS);
        Mockito.when(httpS.getAttribute(Session.SESSION_FIELD)).thenReturn(s);

        servlet.doPost(s, r, response);

        Mockito.verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Utente non autorizzato");
    }
}