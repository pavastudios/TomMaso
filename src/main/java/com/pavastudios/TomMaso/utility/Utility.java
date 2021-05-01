package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;

import java.security.SecureRandom;
import java.sql.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("unused")
public class Utility {
    public static final Random RANDOM=new Random(System.nanoTime());
    public static final SecureRandom SECURE_RANDOM=new SecureRandom();
    public static final char[] HEX_CHARS="0123456789ABCDEF".toCharArray();

    public static int getIdFromGeneratedKeys(MasterPreparedStatement stmt)throws SQLException {
        ResultSet rs= stmt.getGeneratedKeys();
        rs.first();
        int id=rs.getInt(1);
        rs.close();
        return id;
    }

    public static byte[] generateRememberMeCookie(){
        String uuid=UUID.randomUUID().toString();
        return Security.sha256(uuid);
    }

    public static java.util.Date generateRememberMeExpireDate(){
        Calendar cal=new GregorianCalendar();
        cal.add(Calendar.YEAR,1);
        return cal.getTime();
    }


}
