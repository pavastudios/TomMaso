package com.pavastudios.TomMaso.utility;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class Security {

    public static @NotNull byte[] generateRandomBytes(int len){
        byte[] bytes=new byte[len];
        Utility.SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }

    public static @NotNull byte[] generateSalt(){
        return generateRandomBytes(20);
    }

    @Contract("null -> null")
    public static @Nullable byte[] sha512(@Nullable InputStream stream){
        if(stream==null)return null;
        byte[]buffer=new byte[4096];
        int read;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            while((read=stream.read(buffer))!=-1)
                md.update(buffer,0,read);
            stream.close();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("null -> null")
    public static @Nullable byte[] sha512(File file){
        if(file==null)return null;
        try {
            return sha512(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("null, _ -> null;!null,_->!null")
    public static @Nullable byte[] sha512(String password, byte[]salt){
        if(password==null)return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            if(salt!=null)md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Contract("!null->!null;null->null")
    public static @Nullable byte[] sha256(String text){
        if(text==null)return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
