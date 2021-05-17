package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.test.PersonalFileDir;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileUtility {
    public enum FileType{TEXT,MARKDOWN,AUDIO,DIRECTORY,VIDEO,IMAGE,UNKNOWN}
    private static final File TOMMASO_FOLDER = new File(PersonalFileDir.TOMMASO_DATA_FOLDER).getAbsoluteFile();
    public static final File BLOG_FILES_FOLDER = new File(TOMMASO_FOLDER,"blogs").getAbsoluteFile();
    public static final File USER_FILES_FOLDER = new File(TOMMASO_FOLDER,"users").getAbsoluteFile();
    public static final File TMP_FOLDER = new File(TOMMASO_FOLDER,"tmp").getAbsoluteFile();

    private static final int PATH_LENGTH = BLOG_FILES_FOLDER.getAbsolutePath().length();

    public static void writeFile(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
    }

    public static String relativeUrl(File file) {
        String name = file.getAbsolutePath();
        name = name.replace('\\', '/');//c'è gente che non conosce linux
        return name.substring(PATH_LENGTH);
    }

    public static FileType getFileType(ServletContext cont, File file){
        if(file==null||cont==null)return null;
        if(file.isDirectory())return FileType.DIRECTORY;
        String mime=cont.getMimeType(file.getAbsolutePath());
        System.out.println(file+": "+mime);
        if(mime==null)return FileType.UNKNOWN;
        if(mime.startsWith("image/"))return FileType.IMAGE;
        if(mime.startsWith("video/"))return FileType.VIDEO;
        if(mime.startsWith("audio/"))return FileType.AUDIO;
        if(mime.startsWith("text/markdown"))return FileType.MARKDOWN;
        if(mime.startsWith("text/"))return FileType.TEXT;
        return FileType.UNKNOWN;
    }
    /**
     * Converte un pathInfo in un file, non controlla l'esistenza del file
     * */
    private static File pathToFile(File base,String pathInfo){
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/");
        File file = base;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals("..")) return null;
            if (parts[i].isEmpty()) return null;
            file = new File(file, parts[i]);
        }
        file = file.getAbsoluteFile();
        if (!file.getAbsolutePath().startsWith(base.getAbsolutePath()))
            return null;
        return file;
    }
    public static File userPathToFile(String pathInfo) {
        return pathToFile(FileUtility.USER_FILES_FOLDER,pathInfo);
    }
    public static File blogPathToFile(String pathInfo) {
        return pathToFile(FileUtility.BLOG_FILES_FOLDER,pathInfo);
    }

    public static void recursiveDelete(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        File[] children = file.listFiles();
        if(children==null)return;
        for(File f : children){
            recursiveDelete(f);
        }
    }
}
