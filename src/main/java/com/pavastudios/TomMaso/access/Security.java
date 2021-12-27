package com.pavastudios.TomMaso.access;

import org.apache.commons.codec.digest.Sha2Crypt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Security {
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String crypt(String pwd) {
        return Sha2Crypt.sha512Crypt(pwd.getBytes(StandardCharsets.UTF_8), null, SECURE_RANDOM);
    }

    public static boolean verify(String cryped, String pwd) {
        if (cryped == null || pwd == null) return false;
        String salt = cryped.substring(0, cryped.indexOf('$', 3));
        String s2 = Sha2Crypt.sha512Crypt(pwd.getBytes(StandardCharsets.UTF_8), salt);
        return cryped.equals(s2);
    }


}
