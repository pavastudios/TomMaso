package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Utility {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final Pattern NORMAL_CHARS = Pattern.compile("^[a-zA-Z0-9-._]*$");
    public static final Random RANDOM = new Random(System.nanoTime());
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();


    public static int getIdFromGeneratedKeys(MasterPreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.first();
        int id = rs.getInt(1);
        rs.close();
        return id;
    }

    public static byte[] generateRememberMeCookie() {
        String uuid = UUID.randomUUID().toString();
        return Security.sha256(uuid);
    }

    public static boolean useOnlyNormalChars(String str) {
        if (str == null) return false;
        Matcher matcher = NORMAL_CHARS.matcher(str);
        return matcher.matches();
    }

    public static java.util.Date generateRememberMeExpireDate() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    @Contract("null -> null;!null -> !null")
    public static @Nullable String toHexString(byte[] bytes) {
        if (bytes == null) return null;
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            int i = Byte.toUnsignedInt(b);
            builder.append(Utility.HEX_CHARS[i >> 4]);
            builder.append(Utility.HEX_CHARS[i & 0xF]);
        }
        return builder.toString();
    }

    @Contract("null -> null;!null -> !null")
    public static @Nullable byte[] fromHexString(String str) {
        if (str == null || str.length() % 2 == 1) return null;
        int len = str.length() / 2;
        byte[] bytes = new byte[len];
        String s;
        for (int i = 0; i < len; i++) {
            s = "" + str.charAt(i * 2) + str.charAt(i * 2 + 1);
            bytes[i] = (byte) Short.parseShort(s, 16);
        }
        return bytes;
    }

    public static void returnHome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int tryParseInt(String str, int defValue, int radix) {
        if (str == null) return defValue;
        try {
            return Integer.parseInt(str, radix);
        } catch (NumberFormatException ignore) {
            return defValue;
        }
    }

    public static int tryParseInt(String str, int defValue) {
        return tryParseInt(str, defValue, 10);
    }
}
