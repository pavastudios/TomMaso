package com.pavastudios.TomMaso.storage.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.storage.FileUtility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe che modella il concetto di Blog nel database
 */
public class Blog implements GenericModel {
    /**
     * Costante che indica la lunghezza minima per il nome del blog
     */
    public static final int MINIMUM_NAME_LENGTH = 4;
    private int idBlog;
    private Utente proprietario;
    private String nome;
    private int visite;

    /**
     * Metodo per ottenere un blog partendo da un oggetto ResultSet
     * @param rs set di risultati del database
     * @return istanza del blog
     * @throws SQLException Problemi con il result set
     */
    public static Blog fromResultSet(ResultSet rs) throws SQLException {
        Blog b = new Blog();
        b.setIdBlog(rs.getInt("id_blog"));
        b.setNome(rs.getString("nome"));
        b.setVisite(rs.getInt("visite"));
        b.setProprietario(UserQueries.findUserById(rs.getInt("proprietario")));
        return b;
    }

    /**
     * Metodo per ottenere un blog partendo dal percorso
     * @param pathInfo path del blog
     * @return istanza del blog
     * @throws SQLException Problemi con il result set del database
     */
    public static Blog fromPathInfo(String pathInfo) throws SQLException {
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/", 3);
        if (parts.length < 2 || !parts[0].isEmpty()) return null;
        return BlogQueries.findBlogByName(parts[1]);
    }

    public int getVisite() {
        return visite;
    }

    public Blog setVisite(int visite) {
        this.visite = visite;
        return this;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public int getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(int idBlog) {
        this.idBlog = idBlog;
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

    public File getRootPath() {
        return new File(FileUtility.BLOG_FILES_FOLDER, getNome());
    }

    /**
     * Metodo per ottenere il grado di accesso di un utente al blog
     * @param user utente di cui si vuole conoscere il grado di accesso
     * @return booleano che indica se l'utente può accedere al blog
     */
    public boolean hasAccess(Utente user) {
        if (user == null) return false;
        if (user.getPermessi().hasPermissions(Utente.Permessi.MOD_BLOG)) return true;
        return proprietario.equals(user);
    }
}
