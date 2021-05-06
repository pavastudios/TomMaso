package com.pavastudios.TomMaso.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtility {
    public static final File WEB_INF_PATH = new File("C:\\Users\\pasqu\\IdeaProjects\\TomMaso\\src\\main\\webapp\\WEB-INF");

    public static void writeFile(InputStream input, OutputStream output) throws IOException {
        byte buffer[] = new byte[4096];
        int len;
        while((len=input.read(buffer))!=-1){
            output.write(buffer,0,len);
        }
    }
}
