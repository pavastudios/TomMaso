package com.pavastudios.TomMaso.utility;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final Pattern STANDARD_NAME_FORMAT = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9-._]{7,19}$");

    public static boolean isStandardName(String str) {
        if (str == null) return false;
        Matcher matcher = STANDARD_NAME_FORMAT.matcher(str);
        return matcher.matches();
    }

}
