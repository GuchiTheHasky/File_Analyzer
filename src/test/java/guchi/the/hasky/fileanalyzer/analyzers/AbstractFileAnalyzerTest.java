package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractFileAnalyzerTest  {

    private final String CONTENT_FOR_LIST_SENTENCES = "src/test/resources/fileanalyzer/contentForListSentences.txt";
    private final String PATH_FOR_COUNTING_WORD = "src/test/resources/fileanalyzer/CountingWord.txt";
    private final String CUSTOM_CONTENT = "src/test/resources/fileanalyzer/strContent.txt";
    private final String DUCK_STORY = "src/test/resources/fileanalyzer/DuckStory.txt";
    private final String EMPTY_FILE = "src/test/resources/fileanalyzer/emptyFile.txt";
    private final String UTF_8 = "src/test/resources/fileanalyzer/utf8.txt";
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

    @Test // 1.
    @DisplayName("Test 1, throw exception if file path is null.")
    public void testThrowNullPointExceptionIfFilePathIsNull() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.analyze(null, "Hello");
        });
        assertNotNull(thrown.getMessage());
    }
    @Test // 2.
    @DisplayName("Test 2, throw exception if word is null.")
    public void testThrowNullPointExceptionIfWordIsNull() {
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.analyze(DUCK_STORY, null);
        });
        assertNotNull(thrown.getMessage());
    }
    @Test // 3.
    @DisplayName("Test 3, throw exception if file is empty.")
    public void testThrowIllegalArgumentExceptionIfFileIsEmpty() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(EMPTY_FILE, "duck");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 4.
    @DisplayName("Test 4, throw exception if file doesn't exist.")
    public void testThrowIllegalArgumentExceptionIfFileDoesntExist() {
        String path = "this/file/does/not/exist.txt";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(path, "word");
        });
        assertNotNull(thrown.getMessage());
    }
    @Test // 6.
    @DisplayName("Test 6, throw exception if word length is zero.")
    public void testThrowIllegalArgumentExceptionIfWordLengthIsZero() {
        String path = "this/file/does/not/exist.txt";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(path, "");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 5.
    @DisplayName("Test 5, calculate words count, check expected count.")
    public void testCalculateWordsCountFromSourceFileAndCheckExpectedCount() {
        String word = "world";
        int expected = 5;
        FileInfo info = analyzer.analyze(CONTENT_FOR_LIST_SENTENCES, word);
        assertEquals(expected, info.getCountWord());
    }


    @Test // 7.
    @DisplayName("Test 7, test get filtered sentences List, check content.")
    public void testFilteredListOfSentences() {
        String word = "world";
        String content = getContent(CONTENT_FOR_LIST_SENTENCES);

        List<String> sentences = getSentences(content);
        List<String> expectedList = getFilteredSentences(sentences, word);

        FileInfo info = analyzer.analyze(CONTENT_FOR_LIST_SENTENCES, word);
        List<String> actualList = info.getSentences();

        assertEquals(expectedList.size(), actualList.size());

        assertEquals(expectedList.get(0), actualList.get(0));
        assertEquals(expectedList.get(expectedList.size() / 2), actualList.get(actualList.size() / 2));
        assertEquals(expectedList.get(expectedList.size() - 1), actualList.get(actualList.size() - 1));
    }




    //____________________________________________________________________

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createFilesAndContentForTesting() throws IOException {
        new File(PATH_FOR_COUNTING_WORD).createNewFile();
        createContentForCounting();
        new File(UTF_8).createNewFile();
        new File(CONTENT_FOR_LIST_SENTENCES).createNewFile();
        createContentForListSentences();
        new File(CUSTOM_CONTENT).createNewFile();
        createContentForStr();
        new File(EMPTY_FILE).createNewFile();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteFilesAndContentForTesting() {
        new File(PATH_FOR_COUNTING_WORD).delete();
        new File(UTF_8).delete();
        new File(CONTENT_FOR_LIST_SENTENCES).delete();
        new File(CUSTOM_CONTENT).delete();
        new File(EMPTY_FILE).delete();
    }

    String getContent(String path) {
        try {
            return Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error, you don't have access to read this file: " + path + '.');
        }
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

    private void createContentForStr() throws IOException {
        String content = " Hello java world!";
        int counter = 3;
        try (OutputStream outputStream = new FileOutputStream(CUSTOM_CONTENT)) {
            while (counter != 0) {
                outputStream.write(content.getBytes());
                counter--;
            }
        }
    }

}
