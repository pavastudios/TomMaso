package com.pavastudios.TomMaso.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final Pattern NORMAL_CHARS = Pattern.compile("^[a-zA-Z0-9-._]*$");
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static boolean useOnlyNormalChars(String str) {
        if (str == null) return false;
        Matcher matcher = NORMAL_CHARS.matcher(str);
        return matcher.matches();
    }

    public static void returnHome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
