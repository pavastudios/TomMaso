package com.pavastudios.TomMaso.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return idBlog == blog.idBlog && Objects.equals(proprietario, blog.proprietario) && Objects.equals(nome, blog.nome);
    }

}
