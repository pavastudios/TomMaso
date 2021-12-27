package com.pavastudios.TomMaso.utility;

import com.pavastudios.TomMaso.model.Blog;
import com.pavastudios.TomMaso.test.PersonalFileDir;
import org.jetbrains.annotations.Nullable;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per metodi di utilità sui file
 */
public class FileUtility {
    private static final File TOMMASO_FOLDER = new File(PersonalFileDir.TOMMASO_DATA_FOLDER).getAbsoluteFile();
    /**
     * Costante indicante il path in cui verranno conservati i blog sul server
     */
    public static final File BLOG_FILES_FOLDER = new File(TOMMASO_FOLDER, "blogs").getAbsoluteFile();
    private static final int PATH_LENGTH = BLOG_FILES_FOLDER.getAbsolutePath().length();
    /**
     * Costante indicante il path in cui verranno conservati i blog sul server
     */
    public static final File TMP_FOLDER = new File(TOMMASO_FOLDER, "tmp").getAbsoluteFile();
    private static final int BUFFER_SIZE = 4096;

    /**
     * Metodo per scrivere su un OutputStream partendo da un InputStream
     * @param input istanza di InputStream
     * @param output istanza di OutputStream
     * @throws IOException Problemi con la scrittura del file
     */
    public static void writeFile(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
    }

    /**
     * Metodo per ottenere l'URL relativo di un file
     * @param file File di cui si vuole ottenere l'URL
     * @return stringa contenente l'URL relativo del file
     */
    public static String relativeUrl(File file) {
        String name = file.getAbsolutePath();
        name = name.replace('\\', '/');//c'è gente che non conosce linux
        return name.substring(PATH_LENGTH);
    }

    /**
     * Metodo che effettua l'escape dei caratteri in una stringa
     * per essere compatibili con la libreria di rendering del MarkDown
     * @param s stringa su cui effetturare l'escape
     * @return stringa su cui è stato effettuato l'escape
     */
    public static String escapeForMarked(String s) {
        return s.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "")
                .replace("\"", "\\\"");
    }

    /**
     * Metodo che preleva le prime n righe da un file
     * @param file file da cui prelevare le righe
     * @param numRows numero di righe da prelevare
     * @return stringa contenente le prime n righe
     * @throws IOException Problemi con la lettura del file
     */
    public static String headFile(File file, int numRows) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        if (lines.size() >= numRows) {
            lines = lines.subList(0, numRows);
        }
        StringBuilder builder = new StringBuilder();
        for (String s : lines) builder.append(s).append('\n');
        return builder.toString();
    }

    /**
     * Metodo per ottenere la lista di file appartenenti ad un blog
     * @param context contesto della servlet
     * @param blog blog da cui prelevare i file
     * @return lista contenente la lista dei file
     */
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

    /**
     * Metodo che inserisce ricorsivamente tutti i file Markdown in una lista
     * @param list lista in cui inserire i file
     * @param context contesto della servlet
     * @param file file attuale
     */
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

    /**
     * Metodo per ottenere il tipo di un file
     * @param cont contesto della servlet
     * @param file file da ispezionare
     */
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
     * Metodo che concatena due path controllandone la correttezza (non verifica l'effettiva presenza del file)
     * @param base directory di partenza
     * @param pathInfo pathInfo della richiesta
     * @return File presente nel path costruito
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

    /**
     * Metodo che cerca un blog in una directory dato un path di partenza
     * @param pathInfo path di partenza
     * @return File trovato
     */
    public static File blogPathToFile(String pathInfo) {
        return pathToFile(FileUtility.BLOG_FILES_FOLDER, pathInfo);
    }

    /**
     * Metodo che elimina ricorsivamente una directory
     * @param file directory da eliminare
     */
    public static void recursiveDelete(@Nullable File file) {
        if (file == null) {
            return;
        }
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

    /**
     * Metodo che effettua l'escape dei caratteri su un file Markdown
     * @param file file su cui effettuare l'escape
     * @return contenuto del file dopo aver effettuato l'escape
     * @throws IOException Problemi con la lettura del file
     */
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

    /**
     * Enumerazione indicante i tipi di file
     */
    public enum FileType {TEXT, MARKDOWN, AUDIO, DIRECTORY, VIDEO, IMAGE, UNKNOWN}
}
