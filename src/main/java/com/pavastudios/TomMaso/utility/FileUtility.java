package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.test.PersonalFileDir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtility {

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

    public static File userPathToFile(String pathInfo) {
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/");
        File file = FileUtility.USER_FILES_FOLDER;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals("..")) return null;
            if (parts[i].isEmpty()) return null;
            file = new File(file, parts[i]);
        }
        file = file.getAbsoluteFile();
        if (!file.getAbsolutePath().startsWith(FileUtility.USER_FILES_FOLDER.getAbsolutePath()))
            return null;
        return file;
    }
    public static File blogPathToFile(String pathInfo) {
        if (pathInfo == null) return null;
        String[] parts = pathInfo.split("/");
        File file = FileUtility.BLOG_FILES_FOLDER;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals("..")) return null;
            if (parts[i].isEmpty()) return null;
            file = new File(file, parts[i]);
        }
        file = file.getAbsoluteFile();
        if (!file.getAbsolutePath().startsWith(FileUtility.BLOG_FILES_FOLDER.getAbsolutePath()))
            return null;
        return file;
    }

    public static void deleteDir(File file){
        if(file.isDirectory()){
            File[] children = file.listFiles();
            if(children!=null)
                for(File f : children){
                    deleteDir(f);
                }
        }
        file.delete();
    }
}
