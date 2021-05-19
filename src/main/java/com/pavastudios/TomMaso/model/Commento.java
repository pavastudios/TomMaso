package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Commento implements GenericModel {
    private int idCommento;
    private Utente mittente;
    private String pagina;
    private String testo;
    private Date dataInvio;
    public static Commento fromResultSet(ResultSet rs) throws SQLException {
        Commento c = new Commento();
        c.setIdCommento(rs.getInt("id_commento"));
        c.setMittente(Queries.findUserById(rs.getInt("mittente")));
        c.setPagina(rs.getString("url_pagina"));
        c.setTesto(rs.getString("testo"));
        c.setDataInvio(rs.getTimestamp("data_invio"));
        return c;
    }

    public void setDataInvio(Date dataInvio) {
        this.dataInvio = dataInvio;
    }

    public Date getDataInvio() {
        return dataInvio;
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

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
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
        writer.name("pagina").value(pagina);
        writer.name("pagina").value(testo);
        writer.endObject();
    }
}
