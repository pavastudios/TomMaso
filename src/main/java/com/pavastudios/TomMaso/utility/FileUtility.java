package com.pavastudios.TomMaso.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtility {
    public static final File BLOG_FILES_FOLDER = new File(BlogsDirectoryUrl.BLOGS_DIR_PERSONAL_PATH).getAbsoluteFile();
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
