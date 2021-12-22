package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Security;
import com.pavastudios.TomMaso.utility.tuple.Tuple2;
import org.intellij.lang.annotations.MagicConstant;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che modella il concetto di User nel database
 */
public class Utente implements GenericModel {
    /**
     * Costante che indica la lunghezza minima dello username dell'utente
     */
    public static final int MINIMUM_USERNAME_LENGTH = 4;
    private int idUtente;
    private Permessi permessi;
    private String password;
    private String username;
    private Date dataIscrizione;

    public Utente() {
    }

    public static Utente fromResultSet(ResultSet rs) throws SQLException {
        Utente u = new Utente();
        u.setIdUtente(rs.getInt("id_utente"));
        u.setDataIscrizione(rs.getTimestamp("data_iscrizione"));
        u.setPassword(rs.getString("password"));
        u.setUsername(rs.getString("username"));
        u.setPermessi(new Permessi(rs.getInt("permessi")));
        return u;
    }

    public Permessi getPermessi() {
        return permessi;
    }


    public void setPermessi(Permessi permessi) {
        this.permessi = permessi;
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

    private void setPassword(String password) {
        this.password = password;
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
                ", permessi=" + permessi +
                ", password=" + password +
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
        writer.endObject();
    }

    /**
     * Metodo per verificare il login di un utente data la password
     * @param password password dell'utente
     * @return booleano che rappresenta la riuscita del login
     */
    public boolean userVerifyLogin(String password) {
        return Security.verify(this.password, password);
    }

    /**
     * Classe che modella i permessi che un utente può avere
     */
    public static class Permessi {
        /**
         * Costante che rappresenta il permesso di poter gestire gli utenti
         */
        public static final int MANAGE_USER = 0x00000001;
        /**
         * Costante che rappresenta il permesso di poter gestire le chat
         */
        public static final int MOD_CHAT = 0x00000002;
        /**
         * Costante che rappresenta il permesso di poter gestire i blog
         */
        public static final int MOD_BLOG = 0x00000004;
        /**
         * Costante che rappresenta il permesso di poter effettuare l'autenticazione
         */
        public static final int CAN_LOGIN = 0x00000008;

        /**
         * Variabile indicante i permessi di cui dispone lo user
         */
        public int permessi;

        /**
         * Costruttore per inizializzare i permessi di cui l'utente dispone
         * @param permessi permessi dell'utente
         */
        public Permessi(@MagicConstant(flagsFromClass = Permessi.class) int permessi) {
            this.permessi = permessi;
        }

        /**
         * Metodo per la verifica della disposizione di un permesso di un utente
         * @param permission permesso da verificare
         * @return booleano che rappresenta la presenza o assenza del permesso passato come parametro
         */
        public boolean hasPermissions(@MagicConstant(flagsFromClass = Permessi.class) int permission) {
            return (permessi & permission) == permission;
        }

        public int getPermessi() {
            return permessi;
        }

        /**
         * Metodo per ottenere la lista di permessi sottoforma di ArrayList
         * @return ArrayList contenente i permessi dell'utente
         */
        public ArrayList<Tuple2<String, Boolean>> getAsArray() {
            ArrayList<Tuple2<String, Boolean>> list = new ArrayList<>();
            Field[] fields = Permessi.class.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    try {
                        list.add(new Tuple2<>(field.getName(), hasPermissions(field.getInt(null))));
                    } catch (IllegalAccessException ignore) {
                    }
                }
            }
            return list;
        }
    }

}
