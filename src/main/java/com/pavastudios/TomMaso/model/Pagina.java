package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pagina implements GenericModel {
    private int idPagina;
    private Blog blog;
    private String url;

    public static Pagina fromResultSet(ResultSet rs) throws SQLException {
        Pagina p = new Pagina();
        p.setIdPagina(rs.getInt("id_pagina"));
        p.setBlog(Queries.findBlogById(rs.getInt("blog")));
        p.setUrl(rs.getString("url"));
        return p;
    }

    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int id_pagina) {
        this.idPagina = id_pagina;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagina pagina = (Pagina) o;

        return idPagina == pagina.idPagina;
    }

    @Override
    public int hashCode() {
        return idPagina;
    }

    @Override
    public String toString() {
        return "Pagina{" +
                "idPagina=" + idPagina +
                ", blog=" + blog +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idPagina);
        writer.name("blog");
        blog.writeJson(writer);
        writer.name("url").value(url);
        writer.endObject();
    }
}
