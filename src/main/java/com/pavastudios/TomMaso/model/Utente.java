package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.utility.FileUtility;
import com.pavastudios.TomMaso.utility.Security;
import com.pavastudios.TomMaso.utility.tuple.Tuple2;
import org.intellij.lang.annotations.MagicConstant;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Utente implements GenericModel {
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

    public boolean userVerifyLogin(String password) {
        return Security.verify(this.password, password);
    }

    public File getUserFolder() {
        return new File(FileUtility.USER_FILES_FOLDER, username);
    }


    public static class Permessi {
        public static final int MANAGE_USER = 0x00000001;
        public static final int MOD_CHAT = 0x00000002;
        public static final int MOD_BLOG = 0x00000004;
        public static final int CAN_LOGIN = 0x00000008;

        public int permessi;

        public Permessi(@MagicConstant(flagsFromClass = Permessi.class) int permessi) {
            this.permessi = permessi;
        }

        public void addPermission(@MagicConstant(flagsFromClass = Permessi.class) int permission) {
            permessi |= permission;
        }

        public void removePermissions(@MagicConstant(flagsFromClass = Permessi.class) int permission) {
            permessi &= (~permission);
        }

        public boolean hasPermissions(@MagicConstant(flagsFromClass = Permessi.class) int permission) {
            return (permessi & permission) == permission;
        }

        public int getPermessi() {
            return permessi;
        }

        public ArrayList<Tuple2<String, Boolean>> getAsArray() {
            ArrayList<Tuple2<String, Boolean>> list = new ArrayList<>();
            Field[] fields = Permessi.class.getFields();
            int i = 0;
            for (Field field : fields) {
                String name = field.getName();
                if (!name.equals("permessi")) {
                    try {
                        list.add(new Tuple2<>(name, hasPermissions(field.getInt(null))));
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            return list;
        }
    }

}
