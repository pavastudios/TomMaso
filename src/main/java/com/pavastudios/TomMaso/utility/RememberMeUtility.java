package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Utente;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RememberMeUtility {

    public static final String COOKIE_REMEMBER_ME = "TOKEN";
    private static final int SECONDS_IN_YEAR = 60 * 60 * 24 * 365;

    public static Cookie createRememberMeCookie(Utente utente) throws SQLException {
        byte[] bytes = Queries.registerRememberMe(utente);
        String val = Utility.toHexString(bytes);
        Cookie cookie = new Cookie(COOKIE_REMEMBER_ME, val);
        cookie.setMaxAge(SECONDS_IN_YEAR);
        return cookie;
    }

    public static Utente getUserFromCookies(HttpServletRequest req) throws SQLException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (COOKIE_REMEMBER_ME.equals(c.getName())) {
                return Queries.findUserByCookie(c.getValue());
            }
        }
        return null;
    }
}
