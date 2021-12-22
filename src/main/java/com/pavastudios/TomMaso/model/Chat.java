package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.entities.UserQueries;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che modella il concetto di Blog nel database
 */
public class Chat implements GenericModel {
    private int idChat;
    private Utente utente1, utente2;

    /**
     * Metodo per ottenere una chat partendo da un oggetto ResultSet
     * @param rs set di risultati del database
     * @return istanza della chat
     * @throws SQLException Problemi con il result set del database
     */
    public static Chat fromResultSet(ResultSet rs) throws SQLException {
        Chat c = new Chat();
        c.setIdChat(rs.getInt("id_chat"));
        c.setUtente1(UserQueries.findUserById(rs.getInt("utente1")));
        c.setUtente2(UserQueries.findUserById(rs.getInt("utente2")));
        return c;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public Utente getUtente1() {
        return utente1;
    }

    public void setUtente1(Utente utente1) {
        this.utente1 = utente1;
    }

    public Utente getUtente2() {
        return utente2;
    }

    public void setUtente2(Utente utente2) {
        this.utente2 = utente2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        return idChat == chat.idChat;
    }

    @Override
    public int hashCode() {
        return idChat;
    }

    public boolean hasAccess(Utente user) {
        if (user == null) return false;
        if (user.getPermessi().hasPermissions(Utente.Permessi.MOD_CHAT)) return true;
        return isPartecipant(user);
    }

    /**
     * Metodo per ottenere informazioni sulla partecipazione di un utente alla chat
     * @param user utente di cui si vogliono ottenere le informazioni sulla partecipazione
     * @return booleano che indica se l'utente partecipa alla chat
     */
    public boolean isPartecipant(Utente user) {
        return utente1.equals(user) || utente2.equals(user);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "idChat=" + idChat +
                ", utente1=" + utente1 +
                ", utente2=" + utente2 +
                '}';
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idChat);
        writer.name("user1");
        utente1.writeJson(writer);
        writer.name("user2");
        utente2.writeJson(writer);
        writer.endObject();
    }


    /**
     * Metodo per ottenere l'altro partecipante alla chat partendo da uno degli utenti
     * @param user utente di partenza
     * @return istanza della classe User indicante l'altro partecipante alla chat
     */
    public Utente otherUser(@Nullable Utente user) {
        if (utente1.equals(user)) return utente2;
        return utente1;
    }
}
