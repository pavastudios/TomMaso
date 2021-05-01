package com.pavastudios.TomMaso.model;

import com.pavastudios.TomMaso.db.queries.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Blog {
    private int idBlog;
    private Utente proprietario;
    private String nome;

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



    public static Blog getBlog(ResultSet rs) throws SQLException {
        Blog b = new Blog();
        b.setIdBlog(rs.getInt("id_blog"));
        b.setNome(rs.getString("nome"));
        b.setProprietario(Queries.findUserById(rs.getInt("proprietario")));
        return b;
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
}
