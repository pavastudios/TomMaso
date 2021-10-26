package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Security;
import org.intellij.lang.annotations.MagicConstant;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

public class Utente implements GenericModel {
    public static final int MINIMUM_USERNAME_LENGTH = 4;
    private int idUtente;
    private Permessi permessi;
    private String email;
    private String password;
    private String propicURL;
    private String username;
    private String bio;
    private Date dataIscrizione;

    public Utente() {
    }

    public static Utente fromResultSet(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setIdUtente(rs.getInt("id_utente"));
        u.setDataIscrizione(rs.getTimestamp("data_iscrizione"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setIsAdmin(rs.getBoolean("permessi"));
        u.setPropicURL(rs.getString("propic_url"));
        u.setUsername(rs.getString("username"));
        u.setBio(rs.getString("bio"));
        return u;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public boolean getIsAdmin() {
        return permessi.hasPermissions(Permessi.MANAGE_USER);
    }

    public void setIsAdmin(boolean isAdmin) {
        if(isAdmin)permessi=new Permessi(Permessi.MANAGE_USER);
        else permessi=new Permessi(0);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getPropicURL() {
        return propicURL;
    }

    public void setPropicURL(String propicURL) {
        this.propicURL = propicURL;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public String getBio() {
        return bio;
    }

    public Utente setBio(String bio) {
        this.bio = bio;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utente utente = (Utente) o;

        return idUtente == utente.idUtente;
    }

    @Override
    public int hashCode() {
        return idUtente;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "idUtente=" + idUtente +
                ", permessi=" + permessi +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", propicURL='" + propicURL + '\'' +
                ", username='" + username + '\'' +
                ", dataIscrizione=" + dataIscrizione +
                '}';
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idUtente);
        writer.name("isAdmin").value(permessi.getPermessi());
        writer.name("username").value(username);
        writer.name("data_iscrizione").value(dataIscrizione.getTime());
        writer.name("propic_url").value(propicURL);
        writer.endObject();
    }

    public boolean userVerifyLogin(String password) {
        return Security.verify(this.password, password);
    }

    public File getUserFolder() {
        return new File(FileUtility.USER_FILES_FOLDER, username);
    }

    public File getPropicFile() {
        return new File(getUserFolder(), "propic.png");
    }

    public String propicHtml(ServletContext context) {
        return propicHtml(context, "");
    }

    public String propicHtml(ServletContext context, String additional) {
        File propic = getPropicFile();

        if (propic.exists()) {
            return String.format(Locale.US, "<img class=\"w-100 propic rounded-circle\" src=\"%s/users/%s/propic.png\" %s >",
                    context.getContextPath(), getUsername(), additional);
        }
        return String.format(Locale.US, "<svg class=\"w-100 propic rounded-circle\" data-jdenticon-value=\"%s\" %s ></svg>",
                getUsername(), additional);
    }

    public static class Permessi{
        public static final int MANAGE_USER=0x00000001;
        public static final int MOD_CHAT=0x00000002;
        public static final int MOD_USER=0x00000004;

        public int permessi=0;

        public Permessi(@MagicConstant(flagsFromClass = Permessi.class) int permessi) {
            this.permessi = permessi;
        }

        public void addPermission(@MagicConstant(flagsFromClass = Permessi.class) int permission){
            permessi|=permission;
        }
        public void removePermissions(@MagicConstant(flagsFromClass = Permessi.class) int permission){
            permessi&=(~permission);
        }
        public boolean hasPermissions(@MagicConstant(flagsFromClass = Permessi.class) int permission){
            return (permessi&permission)==permission;
        }

        public int getPermessi() {
            return permessi;
        }
    }

}
