package com.pavastudios.TomMaso.storage;

import com.pavastudios.TomMaso.storage.model.Blog;
import com.pavastudios.TomMaso.utility.Tuple2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FileUtilityTest {

    private static Stream<byte[]> workingWriteFile() {
        return Stream.of(
                "test".getBytes(StandardCharsets.UTF_8),
                "".getBytes(StandardCharsets.UTF_8),
                "somestring".getBytes(StandardCharsets.UTF_8),
                "a".getBytes(StandardCharsets.UTF_8)
        );
    }

    private static Stream<Tuple2<ByteArrayInputStream, ByteArrayOutputStream>> notWorkingWriteFile() {
        return Stream.of(
                new Tuple2<>(null, null),
                new Tuple2<>(null, new ByteArrayOutputStream()),
                new Tuple2<>(new ByteArrayInputStream("notworking".getBytes(StandardCharsets.UTF_8)), null)
        );
    }

    private static Stream<Arguments> workingRelativeUrl() {
        return Stream.of(
                Arguments.of(
                        new File(FileUtility.BLOG_FILES_FOLDER, "nomeblog1\\file.txt"), "/nomeblog1/file.txt"
                ),
                Arguments.of(
                        new File(FileUtility.BLOG_FILES_FOLDER, "blog2\\subdir\\file.txt"), "/blog2/subdir/file.txt"
                ),
                Arguments.of(
                        null, null
                )
        );
    }

    private static Stream<Arguments> workingEscapeForMarked() {
        return Stream.of(
                Arguments.of(
                        "path\\subdir\\file.txt", "path\\\\subdir\\\\file.txt"
                ),
                Arguments.of(
                        "lorem ipsum\ndolor sit amet", "lorem ipsum\\ndolor sit amet"
                ),
                Arguments.of(
                        "lorem ipsum dolor sit amet\r", "lorem ipsum dolor sit amet"
                ),
                Arguments.of(
                        "lorem \" ipsum \" dolor \" sit \" amet \"", "lorem \\\" ipsum \\\" dolor \\\" sit \\\" amet \\\""
                )
        );
    }

    private static Stream<Arguments> workingHeadFile() {
        return Stream.of(
                Arguments.of(
                        "1\n2\n3\n4\n5", 3, "1\n2\n3\n"
                ),
                Arguments.of(
                        "1\n2\n3\n4\n5\n6\n7\n8", 2, "1\n2\n"
                ),
                Arguments.of(
                        "1\n", 4, "1\n"
                )
        );
    }

    private static Stream<Arguments> notWorkingHeadFile() {
        return Stream.of(
                Arguments.of(
                        "1\n2\n", -1
                ),
                Arguments.of(
                        "1\n", 0
                )
        );
    }

    private static Stream<Arguments> workingGetPages() {
        ArrayList<String> l1 = new ArrayList<>();
        l1.add("file.md");
        l1.add("file2.txt");
        l1.add("file3.md");
        ArrayList<String> r1 = new ArrayList<>();
        r1.add("file.md");
        r1.add("file3.md");

        ArrayList<String> l2 = new ArrayList<>();
        l2.add("file.md");
        l2.add("file2.md");
        l2.add("file3.md");
        l2.add("file4.txt");
        ArrayList<String> r2 = new ArrayList<>();
        r2.add("file.md");
        r2.add("file2.md");
        r2.add("file3.md");

        return Stream.of(
                Arguments.of(
                        l1, r1
                ),
                Arguments.of(
                        l2, r2
                )
        );
    }

    private static Stream<String> recursiveDelete() {
        return Stream.of(
                "file1.txt",
                "file2.md",
                "file3.mp3",
                "file4.pdf",
                "file5.png"
        );
    }

    private static Stream<Arguments> escapeMD() {
        return Stream.of(
                Arguments.of(
                        "test\rtest", "testtest"
                ),
                Arguments.of(
                        "test\ntest", "test\\ntest"
                ),
                Arguments.of(
                        "test\\test", "test\\\\test"
                ),
                Arguments.of(
                        "test\"test", "test\\\"test"
                )
        );
    }

    private static Stream<Arguments> workingPathToFile() {
        return Stream.of(
                Arguments.of(
                        "/file.txt", "file.txt"
                ),
                Arguments.of(
                        "/files/file.txt", "files/file.txt"
                ),
                Arguments.of(
                        "/dir/subdir/file.txt", "dir/subdir/file.txt"
                )
        );
    }

    private static Stream<String> notWorkingPathToFile() {
        return Stream.of(
                "/..",
                "/",
                "///x",
                null
        );
    }

    @DisplayName("Working writeFile")
    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingWriteFile")
    void writeFile(byte[] byteArr) throws IOException {
        ByteArrayInputStream iS = new ByteArrayInputStream(byteArr);
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        FileUtility.writeFile(iS, oS);
        Assertions.assertArrayEquals(byteArr, oS.toByteArray());
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingWriteFile")
    @DisplayName("Not working writeFile")
    void writeFileNotWorking(Tuple2<ByteArrayInputStream, ByteArrayOutputStream> t) throws IOException {
        ByteArrayInputStream iS = t.get1();
        ByteArrayOutputStream oS = t.get2();

        Assertions.assertFalse(FileUtility.writeFile(iS, oS));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingRelativeUrl")
    @DisplayName("Working relativeUrl")
    void relativeUrl(File f, String result) {

        Assertions.assertEquals(result, FileUtility.relativeUrl(f));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingEscapeForMarked")
    @DisplayName("Working escapeForMarked")
    void escapeForMarked(String input, String output) {
        Assertions.assertEquals(FileUtility.escapeForMarked(input), output);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingHeadFile")
    @DisplayName("Working headFile")
    void headFile(String content, int numrows, String result) throws IOException {
        File f = File.createTempFile("tmp", "file");
        f.deleteOnExit();
        FileUtility.writeFile(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), new FileOutputStream(f));
        Assertions.assertEquals(FileUtility.headFile(f, numrows), result);
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingHeadFile")
    @DisplayName("Not working headFile")
    void headFileNotWorking(String content, int numrows) throws IOException {
        File f = File.createTempFile("tmp", "file");
        f.deleteOnExit();
        FileUtility.writeFile(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), new FileOutputStream(f));
        Assertions.assertNull(FileUtility.headFile(f, numrows));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingGetPages")
    @DisplayName("Working getPages")
    void getPages(List<String> files, List<String> results) throws IOException {
        Blog b = Mockito.mock(Blog.class);
        ServletContext ctx = Mockito.mock(ServletContext.class);

        File f = new File("blog/files/");
        f.mkdirs();
        Mockito.when(b.getRootPath()).thenReturn(f);
        Mockito.when(ctx.getMimeType(Mockito.anyString())).then(invocation -> {
            if (invocation.getArgument(0).toString().endsWith(".md")) {
                return "text/markdown";
            }
            return null;
        });

        for (String s : files) {
            File tmp = new File(f, s);
            tmp.createNewFile();
            //System.out.println(URLConnection.guessContentTypeFromName(s));
        }

        Assertions.assertEquals(results, FileUtility.getPages(ctx, b).stream().map(File::getName).sorted().collect(Collectors.toList()));
        FileUtility.recursiveDelete(f.getParentFile());

    }

    @Test
    void blogPathToFile() {
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("recursiveDelete")
    @DisplayName("Recursive delete")
    void recursiveDelete(String file) throws IOException {
        File f = new File("blog/files/");
        f.mkdirs();

        File tmp = new File(f, file);
        tmp.createNewFile();

        FileUtility.recursiveDelete(f.getParentFile());

        Assertions.assertTrue(!f.exists() && !tmp.exists());
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("escapeMD")
    @DisplayName("Escape MD File")
    void escapeMDFile(String content, String result) throws IOException {
        File f = File.createTempFile("tmp", ".md");
        f.deleteOnExit();
        FileUtility.writeFile(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), new FileOutputStream(f));

        Assertions.assertEquals(result, FileUtility.escapeMDFile(f));
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingPathToFile")
    @DisplayName("Working pathToFile")
    void pathToFileWorking(String input, String result) throws IOException {
        File f = new File("blogs/files/");
        f.mkdirs();
        File resultFile = new File(f, result).getAbsoluteFile();

        Assertions.assertEquals(resultFile, FileUtility.pathToFile(f, input));
        FileUtility.recursiveDelete(f.getParentFile());
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingPathToFile")
    @DisplayName("Not working pathToFile")
    void pathToFileNotWorking(String input) throws IOException {
        File f = new File("blogs/files/");
        f.mkdirs();

        Assertions.assertNull(FileUtility.pathToFile(f, input));
        FileUtility.recursiveDelete(f.getParentFile());
    }
}