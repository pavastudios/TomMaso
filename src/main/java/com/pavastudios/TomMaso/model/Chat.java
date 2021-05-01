package com.pavastudios.TomMaso.model;

import com.pavastudios.TomMaso.db.queries.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Chat {
    private int idChat;
    private Utente utente1,utente2;

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

    public static Chat getChat(ResultSet rs) throws SQLException {
        Chat c = new Chat();
        c.setIdChat(rs.getInt("id_chat"));
        c.setUtente1(Queries.findUserById(rs.getInt("utente1")));
        c.setUtente2(Queries.findUserById(rs.getInt("utente2")));
        return c;
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
}
