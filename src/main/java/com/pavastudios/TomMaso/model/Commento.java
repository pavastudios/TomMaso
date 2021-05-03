package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Commento implements GenericModel {
    private int idCommento;
    private Utente mittente;
    private Pagina pagina;
    private String testo;

    public static Commento fromResultSet(ResultSet rs) throws SQLException {
        Commento c = new Commento();
        c.setIdCommento(rs.getInt("id_commento"));
        c.setMittente(Queries.findUserById(rs.getInt("mittente")));
        c.setPagina(Queries.findPageById(rs.getInt("pagina")));
        c.setTesto(rs.getString("testo"));
        return c;
    }

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

    @Override
    public int hashCode() {
        return idCommento;
    }

    @Override
    public String toString() {
        return "Commento{" +
                "idCommento=" + idCommento +
                ", mittente=" + mittente +
                ", pagina=" + pagina +
                ", testo='" + testo + '\'' +
                '}';
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idCommento);
        writer.name("mittente");
        mittente.writeJson(writer);
        writer.name("pagina");
        pagina.writeJson(writer);
        writer.name("pagina").value(testo);
        writer.endObject();
    }
}
