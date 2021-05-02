package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;

import javax.servlet.http.Cookie;
import java.sql.SQLException;

public class RememberMeUtility {

    public static final String COOKIE_REMEMBER_ME = "TOKEN";
    public static final String SESSION_USER = "utente";
    private static final int SECONDS_IN_YEAR = 60 * 60 * 24 * 365;

    public static Cookie createRememberMeCookie(Utente utente) throws SQLException {
        byte[] bytes = Queries.registerRememberMe(utente);
        String val = Utility.toHexString(bytes);
        Cookie cookie = new Cookie(COOKIE_REMEMBER_ME, val);
        cookie.setMaxAge(SECONDS_IN_YEAR);
        return cookie;
    }
}
