package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Blog implements GenericModel {
    public static final int MINIMUM_NAME_LENGTH = 4;
    private int idBlog;
    private Utente proprietario;
    private String nome;

    public static Blog fromResultSet(ResultSet rs) throws SQLException {
        Blog b = new Blog();
        b.setIdBlog(rs.getInt("id_blog"));
        b.setNome(rs.getString("nome"));
        b.setProprietario(Queries.findUserById(rs.getInt("proprietario")));
        return b;
    }

    public int getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(int idBlog) {
        this.idBlog = idBlog;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Blog blog = (Blog) o;

        return idBlog == blog.idBlog;
    }

    @Override
    public int hashCode() {
        return idBlog;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "idBlog=" + idBlog +
                ", proprietario=" + proprietario +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idBlog);
        writer.name("proprietario");
        proprietario.writeJson(writer);
        writer.name("nome").value(nome);
        writer.endObject();
    }

    public static Blog fromUrl(String url) throws SQLException {
        if(url==null)return null;
        String[]parts=url.split("/");
        if(parts.length<2)return null;
        return Queries.findBlogByName(parts[1]);
    }
}
