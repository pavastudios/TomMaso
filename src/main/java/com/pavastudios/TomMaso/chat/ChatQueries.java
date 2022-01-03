package com.pavastudios.TomMaso.chat;

import com.pavastudios.TomMaso.blog.visualization.CommentQueries;
import com.pavastudios.TomMaso.storage.*;
import com.pavastudios.TomMaso.storage.model.Chat;
import com.pavastudios.TomMaso.storage.model.Messaggio;
import com.pavastudios.TomMaso.storage.model.Utente;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * La classe ChatQueries contiene i metodi per l'interazione con il database relativi alle Chat
 */
public class ChatQueries {
    public static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    public static MasterPreparedStatement FIND_CHAT_BY_ID;
    static MasterPreparedStatement CREATE_CHAT;
    static MasterPreparedStatement SEND_MESSAGE;
    static MasterPreparedStatement FIND_USER_CHAT;
    static MasterPreparedStatement FIND_CHAT_BY_USERS;
    static MasterPreparedStatement FETCH_MESSAGE_FROM_ID;
    static MasterPreparedStatement DELETE_MESSAGE;

    /**
     * Inizializza le prepared statements contenenti le query relative alle chat
     * @throws SQLException Problemi con il database
     */
    @QueryInitializer
    public static void initQueries() throws SQLException {
        FETCH_MESSAGE_FROM_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_chat`=? AND `id_messaggio`>? ORDER BY `data_invio`");
        FIND_USER_CHAT = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? OR `utente2`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_CHAT_BY_USERS = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? AND `utente2`=? ");
        DELETE_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Messaggio` WHERE `id_messaggio` =?");
        CREATE_CHAT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Chat`(`utente1`,`utente2`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        SEND_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Messaggio`(`id_chat`,`mittente`,`testo`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Esegue la query che invia un messaggio in una chat
     * @param chat Chat in cui inviare il messaggio
     * @param mittente Mittente del messaggio
     * @param testo Contenuto del messaggio
     * @return Messaggio inviato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Messaggio sendTextToChat(Chat chat, Utente mittente, String testo) throws SQLException {
        if (chat == null || mittente == null || testo == null) return null;
        if (!chat.isPartecipant(mittente)) return null;
        SEND_MESSAGE.setInt(1, chat.getIdChat());
        SEND_MESSAGE.setInt(2, mittente.getIdUtente());
        SEND_MESSAGE.setString(3, testo);
        SEND_MESSAGE.executeUpdate();
        int id = Queries.getIdFromGeneratedKeys(SEND_MESSAGE);
        return CommentQueries.findMessageById(id);
    }

    /**
     * Esegue la query che crea una chat tra due utenti
     * @param u1 Primo utente
     * @param u2 Secondo utente
     * @return Chat appena creata
     * @throws SQLException Problemi con il database
     */
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
        int id = Queries.getIdFromGeneratedKeys(CREATE_CHAT);
        return findChatById(id);
    }

    /**
     * Esegue la query che trova una chat tramite id
     * @param idChat Id da cercare
     * @return Chat trovata
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Chat findChatById(int idChat) throws SQLException {
        return Queries.findById(Entities.CHAT, idChat);
    }

    /**
     * Esegue la query che trova un messaggio tramite id
     * @param idMessaggio Id da cercare
     * @return Messaggio trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Messaggio findMessageById(int idMessaggio) throws SQLException {
        return Queries.findById(Entities.MESSAGGIO, idMessaggio);
    }

    /**
     * Esegue la query che recupera le chat appartenenti ad un utente
     * @param user
     * @return
     * @throws SQLException Problemi con il database
     */
    public static @NotNull List<Chat> findUserChats(Utente user) throws SQLException {
        FIND_USER_CHAT.setInt(1, user.getIdUtente());
        FIND_USER_CHAT.setInt(2, user.getIdUtente());
        ResultSet rs = FIND_USER_CHAT.executeQuery();
        List<Chat> chats = Queries.resultSetToList(Entities.CHAT, rs);
        rs.close();
        return chats;
    }

    /**
     * Esegue la query che recupera la chat tra due utenti
     * @param u1 Primo utente
     * @param u2 Secondo utente
     * @return Chat tra i due utenti
     * @throws SQLException Problema con il database
     */
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

    /**
     * Esegue la query che recupera i messaggi di un chat a partire da un certo messaggio
     * @param chat Chat di cui recuperare i messaggi
     * @param fromId Id del messaggio di cui recuperare i successivi messaggi
     * @return Lista di messaggi recuperati
     * @throws SQLException Problema con il database
     */
    public static List<Messaggio> fetchMessageFromId(Chat chat, int fromId) throws SQLException {
        FETCH_MESSAGE_FROM_ID.setInt(1, chat.getIdChat());
        FETCH_MESSAGE_FROM_ID.setInt(2, fromId);
        ResultSet set = FETCH_MESSAGE_FROM_ID.executeQuery();
        List<Messaggio> messages = Queries.resultSetToList(Entities.MESSAGGIO, set);
        set.close();
        return messages;
    }

    /**
     * Esegue la query che elimina un messaggio
     * @param messaggio Messaggio da eliminare
     * @throws SQLException Problema con il database
     */
    public static void deleteMessage(Messaggio messaggio) throws SQLException {
        DELETE_MESSAGE.setInt(1, messaggio.getIdMessaggio());
        DELETE_MESSAGE.executeUpdate();
    }
}
