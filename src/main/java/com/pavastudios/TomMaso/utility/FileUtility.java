package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.test.PersonalFileDir;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {
    private static final File TOMMASO_FOLDER = new File(PersonalFileDir.TOMMASO_DATA_FOLDER).getAbsoluteFile();
    public static final File BLOG_FILES_FOLDER = new File(TOMMASO_FOLDER, "blogs").getAbsoluteFile();
    public static final File USER_FILES_FOLDER = new File(TOMMASO_FOLDER, "users").getAbsoluteFile();
    public static final File TMP_FOLDER = new File(TOMMASO_FOLDER, "tmp").getAbsoluteFile();
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

    public static String escapeForMarked(String s) {
        return s.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "")
                .replace("\"", "\\\"");
    }

    public static String headFile(File file, int numRows) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        if (lines.size() >= numRows) {
            lines = lines.subList(0, numRows);
        }
        StringBuilder builder = new StringBuilder();
        for (String s : lines) builder.append(s).append('\n');
        return builder.toString();
    }

    public static List<File> getPages(ServletContext context, Blog blog) {
        if (blog == null) return null;
        File main = blog.getRootPath();
        ArrayList<File> files = new ArrayList<>();
        if (main.exists())
            findMarkdownFiles(files, context, main);
        files.trimToSize();
        //Ordina per data di creazione
        files.sort((o1, o2) -> {
            try {
                BasicFileAttributes a1 = Files.readAttributes(o1.toPath(), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes a2 = Files.readAttributes(o2.toPath(), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                return a2.creationTime().compareTo(a1.creationTime());//Al contrario
            } catch (IOException ignore) {
            }
            return 0;
        });
        return files;
    }

    private static void findMarkdownFiles(ArrayList<File> list, ServletContext context, File file) {
        if (file.isFile()) {
            if (getFileType(context, file) == FileType.MARKDOWN) {
                list.add(file);
            }
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) return;
        for (File f : listFiles)
            findMarkdownFiles(list, context, f);
    }

    public static FileType getFileType(ServletContext cont, File file) {
        if (file == null || cont == null) return null;
        if (!file.exists()) return null;
        if (file.isDirectory()) return FileType.DIRECTORY;
        String mime = cont.getMimeType(file.getAbsolutePath());
        System.out.println(file + ": " + mime);
        if (mime == null) return FileType.UNKNOWN;
        if (mime.startsWith("image/")) return FileType.IMAGE;
        if (mime.startsWith("video/")) return FileType.VIDEO;
        if (mime.startsWith("audio/")) return FileType.AUDIO;
        if (mime.startsWith("text/markdown")) return FileType.MARKDOWN;
        if (mime.startsWith("text/")) return FileType.TEXT;
        return FileType.UNKNOWN;
    }

    /**
     * Converte un pathInfo in un file, non controlla l'esistenza del file
     */
    private static File pathToFile(File base, String pathInfo) {
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
        return pathToFile(FileUtility.USER_FILES_FOLDER, pathInfo);
    }

    public static File blogPathToFile(String pathInfo) {
        return pathToFile(FileUtility.BLOG_FILES_FOLDER, pathInfo);
    }

    public static void recursiveDelete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        File[] children = file.listFiles();
        if (children == null) return;
        for (File f : children) {
            recursiveDelete(f);
        }
        file.delete();
    }

    public static String escapeMDFile(File file) throws IOException {
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        StringBuilder content = new StringBuilder();
        int readChar;
        while ((readChar = fis.read()) != -1) {
            switch (readChar) {
                case '\r':
                    break;
                case '\n':
                    content.append("\\n");
                    break;
                case '\\':
                    content.append("\\\\");
                    break;
                case '"':
                    content.append("\\\"");
                    break;
                default:
                    content.append((char) readChar);
                    break;
            }

        }
        return content.toString();
    }

    public enum FileType {TEXT, MARKDOWN, AUDIO, DIRECTORY, VIDEO, IMAGE, UNKNOWN}
}
