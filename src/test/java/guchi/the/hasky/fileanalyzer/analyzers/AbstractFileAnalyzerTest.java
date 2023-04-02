package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileStatistic;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractFileAnalyzerTest {

    private final String CONTENT_FOR_LIST_SENTENCES = "src/test/resources/fileanalyzer/contentForListSentences.txt";
    private final String PATH_FOR_COUNTING_WORD = "src/test/resources/fileanalyzer/CountingWord.txt";
    private final String LARGE_SIZE_FILE = "src/test/resources/fileanalyzer/largeFileSize.txt";
    private final String DUCK_STORY = "src/test/resources/fileanalyzer/DuckStory.txt";
    private final String EMPTY_FILE = "src/test/resources/fileanalyzer/emptyFile.txt";

    private FileAnalyzer analyzer;

    @BeforeEach
    void init() throws IOException {
        analyzer = getAnalyzer();
        createFilesAndContentForTesting();
    }

    @AfterEach
    void delete() {
        deleteFilesAndContentForTesting();
    }

    abstract FileAnalyzer getAnalyzer();

    @Test
    @DisplayName("Test, throw exception if file path is null.")
    public void testThrowNullPointExceptionIfFilePathIsNull() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.analyze(null, "Hello");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, throw exception if word is null.")
    public void testThrowNullPointExceptionIfWordIsNull() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.analyze(DUCK_STORY, null);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, throw exception if file is empty.")
    public void testThrowIllegalArgumentExceptionIfFileIsEmpty() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(EMPTY_FILE, "duck");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, throw exception if file doesn't exist.")
    public void testThrowIllegalArgumentExceptionIfFileDoesntExist() {
        String path = "this/file/does/not/exist.txt";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(path, "word");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, throw exception if word length is zero.")
    public void testThrowIllegalArgumentExceptionIfWordLengthIsZero() {
        String path = "this/file/does/not/exist.txt";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(path, "");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, calculate words count, check expected count.")
    public void testCalculateWordsCountFromSourceFileAndCheckExpectedCount() {
        String word = "world";
        int expected = 5;
        FileStatistic info = analyzer.analyze(CONTENT_FOR_LIST_SENTENCES, word);
        assertEquals(expected, info.getCountWord());
    }

    @Test
    @DisplayName("Test, test get filtered sentences List, check content.")
    public void testFilteredListOfSentences() {
        String word = "world";
        String content = getContent(CONTENT_FOR_LIST_SENTENCES);

        List<String> sentences = getSentences(content);
        List<String> expectedList = getFilteredSentences(sentences, word);

        FileStatistic info = analyzer.analyze(CONTENT_FOR_LIST_SENTENCES, word);
        List<String> actualList = info.getSentences();

        assertEquals(expectedList.size(), actualList.size());

        assertEquals(expectedList.get(0), actualList.get(0));
        assertEquals(expectedList.get(expectedList.size() / 2), actualList.get(actualList.size() / 2));
        assertEquals(expectedList.get(expectedList.size() - 1), actualList.get(actualList.size() - 1));
    }

    @Test
    @DisplayName("Test, throw exception if file length() is too large.")
    public void testThrowStackOverflowErrorIfFileSizeIsTooLarge() {
        Throwable thrown = assertThrows(StackOverflowError.class, () -> {
            analyzer.analyze(LARGE_SIZE_FILE, "word");
        });
        assertNotNull(thrown.getMessage());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFilesAndContentForTesting() throws IOException {
        new File(PATH_FOR_COUNTING_WORD).createNewFile();
        createContentForCounting();
        new File(CONTENT_FOR_LIST_SENTENCES).createNewFile();
        createContentForListSentences();
        new File(EMPTY_FILE).createNewFile();
        new File(LARGE_SIZE_FILE).createNewFile();
        createContentForLargeSizeFile();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteFilesAndContentForTesting() {
        new File(PATH_FOR_COUNTING_WORD).delete();
        new File(CONTENT_FOR_LIST_SENTENCES).delete();
        new File(EMPTY_FILE).delete();
        new File(LARGE_SIZE_FILE).delete();
    }

    @SneakyThrows
    private void createContentForLargeSizeFile() {
        File file = new File(LARGE_SIZE_FILE);
        String content = " content ";
        @Cleanup FileWriter writer = new FileWriter(file);
        while (file.length() < 8193) {
            writer.write(content);
        }
    }

    @SneakyThrows
    String getContent(String path) {
        return Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
    }

    private List<String> getSentences(String content) {
        return Arrays.stream(AnalyzerTools.SENTENCES_DELIMER.split(content))
                .map(String::trim)
                .filter(sentences -> !sentences.isEmpty())
                .collect(Collectors.toList());
    }

    static List<String> getFilteredSentences(List<String> sentences, String word) {
        return sentences.stream()
                .filter(sentence -> sentence.contains(word))
                .collect(Collectors.toList());
    }

    private void createContentForCounting() throws IOException {
        String content = "Hello java";
        int counter = 10;
        try (OutputStream outputStream = new FileOutputStream(PATH_FOR_COUNTING_WORD)) {
            while (counter != 0) {
                outputStream.write(content.getBytes());
                outputStream.write("\n".getBytes());
                counter--;
            }
        }
    }

    private void createContentForListSentences() throws IOException {
        String point = "Hello java.";
        String questionMark = "Hello java world?";
        String exclamationMark = "Hello java!";
        int counter = 5;
        try (OutputStream outputStream = new FileOutputStream(CONTENT_FOR_LIST_SENTENCES)) {
            while (counter != 0) {
                outputStream.write(point.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write(questionMark.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write(exclamationMark.getBytes());
                outputStream.write("\n".getBytes());
                counter--;
            }
        }
    }
}
