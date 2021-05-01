package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.Utente;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Queries {

    private static MasterPreparedStatement FIND_USER_BY_USERNAME;


    public static void initQueries()throws SQLException{
        FIND_USER_BY_USERNAME= GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
    }

    public static @Nullable
    Utente findUserByUsername(@NotNull String username)throws SQLException {
        FIND_USER_BY_USERNAME.setString(1,username);
        ResultSet rs=FIND_USER_BY_USERNAME.executeQuery();
        if(!rs.first())return null;
        Utente user = Utente.getUtente(rs);
        rs.close();
        return user;
    }
}
