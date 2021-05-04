package com.pavastudios.TomMaso.utility;

public class SessionUtility {
    private static String getToken() {
        byte[] token = Security.generateRandomBytes(20);
        return Utility.toHexString(token);
    }
}
