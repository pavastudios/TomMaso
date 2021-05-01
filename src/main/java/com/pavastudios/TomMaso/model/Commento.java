package com.pavastudios.TomMaso.model;

import com.pavastudios.TomMaso.db.queries.Queries;
import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Commento {
    private int idCommento;
    private Utente mittente;
    private Pagina pagina;
    private String testo;

    public int getIdCommento() {
        return idCommento;
    }

    public void setIdCommento(int idCommento) {
        this.idCommento = idCommento;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
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
        Commento commento = (Commento) o;
        return idCommento == commento.idCommento;
    }

    public static Commento getCommento(ResultSet rs) throws SQLException {
        Commento c = new Commento();
        c.setIdCommento(rs.getInt("id_commento"));
        c.setMittente(Queries.findUserById(rs.getInt("mittente")));
        c.setPagina(Queries.findPageById(rs.getInt("pagina")));
        c.setTesto(rs.getString("testo"));
        return c;
    }

}
