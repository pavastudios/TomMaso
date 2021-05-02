package com.pavastudios.TomMaso.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class Utente {
    private int idUtente;
    private boolean isAdmin;
    private String email;
    private byte[] password;
    private String propicURL;
    private String username;
    private byte[] salt;
    private Date dataIscrizione;

    public Utente() {
    }

    public static Utente fromResultSet(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setIdUtente(rs.getInt("id_utente"));
        u.setDataIscrizione(rs.getDate("data_iscrizione"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getBytes("password"));
        u.setSalt(rs.getBytes("salt"));
        u.setIsAdmin(rs.getBoolean("is_admin"));
        u.setPropicURL(rs.getString("propic_url"));
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
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getPropicURL() {
        return propicURL;
    }

    public void setPropicURL(String propicURL) {
        this.propicURL = propicURL;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Date getDataIscrizione() {
        return dataIscrizione;
    }

    public void setDataIscrizione(Date dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
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
                ", isAdmin=" + isAdmin +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", propicURL='" + propicURL + '\'' +
                ", username='" + username + '\'' +
                ", salt=" + Arrays.toString(salt) +
                ", dataIscrizione=" + dataIscrizione +
                '}';
    }
}
