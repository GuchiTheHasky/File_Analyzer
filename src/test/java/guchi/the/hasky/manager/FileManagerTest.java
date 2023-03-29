package guchi.the.hasky.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


public class FileManagerTest {

    private final String FILE_FOR_COPY = "src/test/resources/manager/dirFrom/fileForCopy.txt";
    private final String FILE_FOR_MOVE = "src/test/resources/manager/dirFrom/fileForMove.txt";
    private final String STACK_OVER_FLOW = "src/test/resources/manager/stackOverflow.txt";
    private final String PATH_FOR_COUNTING = "src/test/resources/manager";
    private final String THIS_PATH_DOES_NOT_EXIST = "a/baba/gala/maga";

    @BeforeEach
    void init() throws IOException {
        createContentForStackOverflowErrorExceptionThrow();
        createContentTreeForFilesCountMethods();
        createContentForCopyAndMove();
    }

    @AfterEach
    void delete() {
        deleteContentForStackOverflowErrorExceptionThrow();
        deleteContentTreeForFilesCountMethods();
        deleteContentForCopyAndMove();
    }

    @Test
    @DisplayName("Test, try to process an extremely large file throw StackOverflowErrorException.")
    public void testTryToProcessAnExtremelyLargeFileThrowStackOverflowErrorException() {
        Throwable thrown = assertThrows(StackOverflowError.class, () -> {
            FileManager.validateFileSize(new File(STACK_OVER_FLOW));
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, try to validate not existing file path ThrowIllegalArgumentException.")
    public void testTryToValidateNotExistingFilePathThrowIllegalArgumentException() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            FileManager.validatePath(THIS_PATH_DOES_NOT_EXIST);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, try to validate path if file path is null ThrowNullPointerException.")
    public void testTryToValidatePathIfFilePathIsNullThrowNullPointerException() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            FileManager.validatePath(null);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, try to validate path if file path and directory path is null throw NullPointerException.")
    public void testTryToValidatePathIfFilePathAndDirectoryPathIsNullThrowNullPointerException() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            FileManager.validatePath(null, null);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, count files in some directory check expected result and actual.")
    public void testCountFilesInSomeDirectoryCheckExpectedResultAndActual() {
        int expected = 13;
        int actual = FileManager.filesCount(PATH_FOR_COUNTING);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test, count dirs in some directory check expected result and actual.")
    public void testCountDirsInSomeDirectoryCheckExpectedResultAndActual() {
        int expected = 8;
        int actual = FileManager.directoriesCount(PATH_FOR_COUNTING);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test, copy file from source directory to destination directory check size and content.")
    public void testCopyFileFromSourceDirectoryToDestinationDirectoryCheckSizeAndContent() throws IOException {
        String destination = "src/test/resources/manager/dirTo";
        String expected = getContent(FILE_FOR_COPY);
        FileManager.copy(FILE_FOR_COPY, destination);
        String actual = getContent(destination + File.separator + new File(FILE_FOR_COPY).getName());

        assert expected != null;
        assert actual != null;
        assertEquals(expected.length(), actual.length());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test, move file from source directory to destination directory check size and content.")
    public void testMoveFileFromSourceDirectoryToDestinationDirectoryCheckSizeAndContent() throws IOException {
        String destination = "src/test/resources/manager/dirTo/";
        String expected = getContent(FILE_FOR_MOVE);
        FileManager.move(FILE_FOR_MOVE, destination);
        String actual = getContent(destination + new File(FILE_FOR_MOVE).getName());

        assert expected != null;
        assert actual != null;
        assertEquals(expected.length(), actual.length());
        assertEquals(expected, actual);
    }

    private void createContentForStackOverflowErrorExceptionThrow() {
        File file = new File(STACK_OVER_FLOW);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            if (!file.createNewFile()) {
                String content = " <<< Mortal Combat >>> ";
                while (file.length() < 8193) {
                    outputStream.write(content.getBytes());
                    outputStream.write(System.lineSeparator().getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteContentForStackOverflowErrorExceptionThrow() {
        new File(STACK_OVER_FLOW).delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createContentTreeForFilesCountMethods() throws IOException {
        new File("src/test/resources/manager/dir1").mkdir();
        new File("src/test/resources/manager/dir2").mkdir();
        new File("src/test/resources/manager/dir3").mkdir();
        new File("src/test/resources/manager/dir3/dir4").mkdir();
        new File("src/test/resources/manager/dir3/dir5/dir6").mkdirs();

        new File("src/test/resources/manager/dir1/file1.txt").createNewFile();
        new File("src/test/resources/manager/dir1/file2.txt").createNewFile();
        new File("src/test/resources/manager/dir1/file3.txt").createNewFile();
        new File("src/test/resources/manager/dir2/file4.txt").createNewFile();
        new File("src/test/resources/manager/dir2/file5.txt").createNewFile();
        new File("src/test/resources/manager/dir3/file6.txt").createNewFile();
        new File("src/test/resources/manager/dir3/file7.txt").createNewFile();
        new File("src/test/resources/manager/dir3/file8.txt").createNewFile();
        new File("src/test/resources/manager/dir3/dir4/file9.txt").createNewFile();
        new File("src/test/resources/manager/dir3/dir5/dir6/file10.txt").createNewFile();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteContentTreeForFilesCountMethods() {
        new File("src/test/resources/manager/dir3/dir5/dir6/file10.txt").delete();
        new File("src/test/resources/manager/dir3/dir4/file9.txt").delete();
        new File("src/test/resources/manager/dir3/file8.txt").delete();
        new File("src/test/resources/manager/dir3/file7.txt").delete();
        new File("src/test/resources/manager/dir3/file6.txt").delete();
        new File("src/test/resources/manager/dir2/file5.txt").delete();
        new File("src/test/resources/manager/dir2/file4.txt").delete();
        new File("src/test/resources/manager/dir1/file3.txt").delete();
        new File("src/test/resources/manager/dir1/file2.txt").delete();
        new File("src/test/resources/manager/dir1/file1.txt").delete();

        new File("src/test/resources/manager/dir3/dir5/dir6").delete();
        new File("src/test/resources/manager/dir3/dir5").delete();
        new File("src/test/resources/manager/dir3/dir4").delete();
        new File("src/test/resources/manager/dir3").delete();
        new File("src/test/resources/manager/dir2").delete();
        new File("src/test/resources/manager/dir1").delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createContentForCopyAndMove() throws IOException {
        new File("src/test/resources/manager/dirFrom").mkdir();
        File fileForCopy = new File(FILE_FOR_COPY);
        File fileForMove = new File(FILE_FOR_MOVE);
        fileForCopy.createNewFile();
        fileForMove.createNewFile();
        createContent(FILE_FOR_COPY);
        createContent(FILE_FOR_MOVE);
        new File("src/test/resources/manager/dirTo").mkdir();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteContentForCopyAndMove() {
        new File(FILE_FOR_COPY).delete();
        new File("src/test/resources/manager/dirTo"
                + File.separator + new File(FILE_FOR_COPY).getName()).delete();
        new File("src/test/resources/manager/dirTo"
                + File.separator + new File(FILE_FOR_MOVE).getName()).delete();

        new File("src/test/resources/manager/dirTo").delete();
        new File("src/test/resources/manager/dirFrom").delete();
        flush();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void flush() {
        new File("src/test/resources/manager/dirFrom/fileForMove.txt").delete();
        new File("src/test/resources/manager/dirFrom/").delete();
    }

    private void createContent(String path) {
        File file = new File(path);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            if (!file.createNewFile()) {
                int iterator = 10;
                String content = " <<< Mortal Combat >>> ";
                while (iterator > 0) {
                    outputStream.write(content.getBytes());
                    outputStream.write(System.lineSeparator().getBytes());
                    iterator--;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getContent(String path) throws IOException {
        File file = new File(path);
        try (InputStream input = new FileInputStream(file)) {
            int size = (int) file.length();
            byte[] buffer = new byte[size];
            while (input.read(buffer) != -1) {
                return new String(buffer, StandardCharsets.UTF_8);
            }
        }
        return null;
    }
}
