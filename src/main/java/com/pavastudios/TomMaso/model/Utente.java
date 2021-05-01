package com.pavastudios.TomMaso.model;

import java.util.Date;

public class Utente {
    private int idUtente;
    private short isAdmin;
    private String email;
    private String password;
    private String propicURL;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private static byte[] salt;
    private Date dataIscrizione;

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public void setIsAdmin(short isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPropicURL(String propicURL) {
        this.propicURL = propicURL;
    }

    public static void setSalt(byte[] salt) {
        Utente.salt = salt;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPropicURL() {
        return propicURL;
    }

    public static byte[] getSalt() {
        return salt;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public Utente() {
    }
}
