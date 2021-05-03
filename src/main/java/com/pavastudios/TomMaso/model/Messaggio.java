package com.pavastudios.TomMaso.model;

import com.pavastudios.TomMaso.db.queries.Queries;

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Messaggio {
    private int idMessaggio;
    private Utente mittente;
    private String testo;
    private Chat idChat;
    private Date dataInvio;

    public static Messaggio fromResultSet(ResultSet rs) throws SQLException {
        Messaggio m = new Messaggio();
        m.setIdMessaggio(rs.getInt("id_messaggio"));
        m.setIdChat(Queries.findChatById(rs.getInt("id_chat")));
        m.setTesto(rs.getString("testo"));
        m.setDataInvio(rs.getDate("data_invio"));
        m.setMittente(Queries.findUserById(rs.getInt("mittente")));
        return m;
    }

    public Date getDataInvio() {
        return dataInvio;
    }

    public void setDataInvio(Date dataInvio) {
        this.dataInvio = dataInvio;
    }

    public int getIdMessaggio() {
        return idMessaggio;
    }

    public void setIdMessaggio(int idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public Chat getIdChat() {
        return idChat;
    }

    public void setIdChat(Chat idChat) {
        this.idChat = idChat;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messaggio messaggio = (Messaggio) o;

        return idMessaggio == messaggio.idMessaggio;
    }

    @Override
    public int hashCode() {
        return idMessaggio;
    }

    @Override
    public String toString() {
        return "Messaggio{" +
                "idMessaggio=" + idMessaggio +
                ", mittente=" + mittente +
                ", dataInvio=" + dataInvio +
                ", testo='" + testo + '\'' +
                ", idChat=" + idChat +
                '}';
    }
}
