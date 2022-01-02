package com.pavastudios.TomMaso.storage;

import com.pavastudios.TomMaso.utility.Tuple2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

class FileUtilityTest {

    private static Stream<byte[]> workingWriteFile () {
        return Stream.of(
                "test".getBytes(StandardCharsets.UTF_8),
                "".getBytes(StandardCharsets.UTF_8),
                "somestring".getBytes(StandardCharsets.UTF_8),
                "a".getBytes(StandardCharsets.UTF_8)
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

    private static Stream<Tuple2<ByteArrayInputStream, ByteArrayOutputStream>> notWorkingWriteFile () {
        return Stream.of(
                new Tuple2<>(null, null),
                new Tuple2<>(null, new ByteArrayOutputStream()),
                new Tuple2<>(new ByteArrayInputStream("notworking".getBytes(StandardCharsets.UTF_8)), null)
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("notWorkingWriteFile")
    @DisplayName("Not working writeFile")
    void WriteFileNotWorking (Tuple2<ByteArrayInputStream, ByteArrayOutputStream> t) throws IOException {
        ByteArrayInputStream iS = t.get1();
        ByteArrayOutputStream oS = t.get2();

        Assertions.assertFalse(FileUtility.writeFile(iS, oS));
    }

    private static Stream<Arguments> workingRelativeUrl () {
        return Stream.of(
                Arguments.of(
                       new File(FileUtility.BLOG_FILES_FOLDER, "nomeblog1\\file.txt"), "/nomeblog1/file.txt"
                ),
                Arguments.of(
                        new File(FileUtility.BLOG_FILES_FOLDER, "blog2\\subdir\\file.txt"), "/blog2/subdir/file.txt"
                )
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("workingRelativeUrl")
    @DisplayName("Working relativeUrl")
    void relativeUrl(File f, String result) {

        Assertions.assertEquals(result, FileUtility.relativeUrl(f));
    }

    @Test
    void escapeForMarked() {
    }

    @Test
    void headFile() {
    }

    @Test
    void getPages() {
    }

    @Test
    void getFileType() {
    }

    @Test
    void blogPathToFile() {
    }

    @Test
    void recursiveDelete() {
    }

    @Test
    void escapeMDFile() {
    }
}