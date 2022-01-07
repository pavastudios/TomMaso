package com.pavastudios.TomMaso.access;

import org.apache.commons.codec.digest.Sha2Crypt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Security {
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String crypt(String pwd) {
        if (pwd == null) {
            return null;
        }
        return Sha2Crypt.sha512Crypt(pwd.getBytes(StandardCharsets.UTF_8), null, SECURE_RANDOM);
    }

    public static boolean verify(String crypted, String pwd) {
        if (crypted == null || pwd == null) return false;
        if (crypted.length() < 4) {
            return false;
        }
        String salt = crypted.substring(0, crypted.indexOf('$', 3));
        String s2 = Sha2Crypt.sha512Crypt(pwd.getBytes(StandardCharsets.UTF_8), salt);
        return crypted.equals(s2);
    }


}
