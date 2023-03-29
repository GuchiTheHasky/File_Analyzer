package guchi.the.hasky.fileanalyzer.analyzer;

import guchi.the.hasky.fileanalyzer.analyzeinfo.FileInfo;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

public class FileFileAnalyzerInputStreamTest {

    private final String CONTENT_FOR_LIST_SENTENCES = "src/test/resources/fa/contentForListSentences.txt";
    private final String PATH_FOR_COUNTING_WORD = "src/test/resources/fa/CountingWord.txt";
    private final String NOT_SUPPORTED = "src/test/resources/fa/notSupported.html";
    private final String CUSTOM_CONTENT = "src/test/resources/fa/strContent.txt";
    private final String EMPTY_FILE = "src/test/resources/fa/emptyFile.txt";
    private final String UTF_8 = "src/test/resources/fa/utf8.txt";
    private FileFileAnalyzerInputStream analyzer;
    private FileInfo fileInfo;


    @BeforeEach
    public void init() throws IOException {
        analyzer = new FileFileAnalyzerInputStream();
        fileInfo = new FileInfo();
        createFilesAndContentForTesting();
    }

    @AfterEach
    public void delete() {
        deleteFilesAndContentForTesting();
    }

    @Test // 1.
    @DisplayName("Test, try to analyze unsupported file format throw UnsupportedOperationException exception")
    public void testTryToAnalyzeUnsupportedFileFormatThrowException() {
        String path = NOT_SUPPORTED;
        Throwable thrown = assertThrows(UnsupportedOperationException.class, () -> {
            analyzer.validateSources(path, "Hello");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 2.
    @DisplayName("Test, try to analyze empty file, throw exception.")
    public void testTryToAnalyzeEmptyFileThrowException() {
        String path = "src/test/resources/fa/emptyFile.txt";
        Throwable thrown = assertThrows(Exception.class, () -> {
            analyzer.validateSources(path, "Hello");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 3.
    @DisplayName("Test, throw exception if file path is null.")
    public void testThrowNullPointExceptionIfFilePathIsNull() {
        String path = null;
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.validateSources(path, "Hello");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 4.
    @DisplayName("Test, throw exception if String word is null.")
    public void testThrowNullPointExceptionIfStringWordIsNull() {
        String word = null;
        Throwable thrown = assertThrows(NullPointerException.class, () -> {
            analyzer.validateSources("path", word);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test // 5.
    @DisplayName("Test, get content from file source, return String, check length & content.")
    public void testGetContentFromSourceFileCheckLengthAndActualContentReturnString() throws IOException {
        String expected = getStringContent(CUSTOM_CONTENT);
        String actual = analyzer.getContent(CUSTOM_CONTENT);
        assert expected != null;
        assertEquals(expected.length(), actual.length());
        assertEquals(expected, actual);
    }

    @Test // 6.
    @DisplayName("Test, calculate words count, check expected count.")
    public void testCalculateWordsCountFromSourceFileAndCheckExpectedCount() throws Exception {
        String word = "java";
        int expected = 10;
        int actual = analyzer.countWord(PATH_FOR_COUNTING_WORD, word);
        assertEquals(expected, actual);
    }

    @Test // 7.
    @DisplayName("Test, get content from source file, return List<Str> sentences.")
    public void testGetContentFromSourceFileReturnedListCheckSizeAndActualContent() throws IOException {
        List<String> expected = getSentences();
        List<String> actual = analyzer.sentences(getStringContent(CONTENT_FOR_LIST_SENTENCES));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);

        String expectedFirst = expected.get(0);
        String expectedMiddle = expected.get(expected.size() / 2);
        String expectedLast = expected.get(expected.size() - 1);

        String actualFirst = actual.get(0);
        String actualMiddle = actual.get(expected.size() / 2);
        String actualLast = actual.get(expected.size() - 1);

        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedMiddle, actualMiddle);
        assertEquals(expectedLast, actualLast);
    }

    @Test // 8.
    @DisplayName("Test, access List<Str> sentences, return filtered List<Str> filtrSentences.")
    public void testGetListOfStringReturnFilteredListListCheckSizeAndActualContent() throws IOException {
        List<String> expected = getFilteredSentences();
        List<String> actual = analyzer.filterSentences(getSentences(), "world");
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);

        String expectedFirst = expected.get(0);
        String expectedMiddle = expected.get(expected.size() / 2);
        String expectedLast = expected.get(expected.size() - 1);

        String actualFirst = actual.get(0);
        String actualMiddle = actual.get(expected.size() / 2);
        String actualLast = actual.get(expected.size() - 1);

        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedMiddle, actualMiddle);
        assertEquals(expectedLast, actualLast);
    }

    @Test // 9.
    @DisplayName("Test, analyze source file, return object with words count.")
    public void testAnalyzeCheckExpectedFileInfoWordCountWithActualWordsCount() throws Exception {
        String path = "src/test/resources/fa/DuckStory.txt";
        String word = "duck";

        fileInfo = analyzer.analyze(path, word);

        int expectedWordsCount = fileInfo.getCountWord();
        int actualWordsCount = getWordCount(word, path);

        assertEquals(expectedWordsCount, actualWordsCount);
    }

    @Test // 10.
    @DisplayName("Test, analyze source file, return object with List<Str> filterSentences, check List size & content.")
    public void testAnalyzeGetExpectedAndActualListsOfFilteredSentencesCheckSizeAndContent() throws Exception {
        String path = "src/test/resources/fa/DuckStory.txt";
        String word = "duck";
        String content = analyzer.getContent(path);
        List<String> sentences = analyzer.sentences(content);

        FileInfo info = analyzer.analyze(path, word);

        List<String> expectedSentencesList = info.getSentences();
        List<String> actualSentencesList = analyzer.filterSentences(sentences, word);

        assertEquals(expectedSentencesList.size(), actualSentencesList.size());
        assertEquals(expectedSentencesList, actualSentencesList);

        String expectedFirst = expectedSentencesList.get(0);
        String expectedMiddle = expectedSentencesList.get(expectedSentencesList.size() / 2);
        String expectedLast = expectedSentencesList.get(expectedSentencesList.size() - 1);

        String actualFirst = actualSentencesList.get(0);
        String actualMiddle = actualSentencesList.get(expectedSentencesList.size() / 2);
        String actualLast = actualSentencesList.get(expectedSentencesList.size() - 1);

        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedMiddle, actualMiddle);
        assertEquals(expectedLast, actualLast);
    }

    @Test // 11.
    @DisplayName("Test, get file encoding.")
    public void testGetFileEncodingFromSourceFileAndCheckReturnedString() throws IOException {
        FileFileAnalyzerInputStream an = new FileFileAnalyzerInputStream();
        String expected = "UTF8";
        String actual = an.encoder(UTF_8);
        assertEquals(expected, actual);
        /* Хотів створити окремі файли з іншим кодуванням, але не зміг. Міняло кодування зразу в цілому проекті.*/
    }

    @Test // 12.
    @DisplayName("Test, validate File Size.")
    public void testValidateFileSizeIfFileIsToLargeThrowStackOverflowError() {
        Long testValue = 8193L;
        Throwable thrown = assertThrows(StackOverflowError.class, () -> {
            analyzer.validateFileSize(testValue);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Test, try read file & throw FileNotFoundException if Current file doesn't exist.")
    public void testThrowFileNotFoundExceptionIfCurrentPathFileDoesntExist() {
        String path = "this/path/does/not/exist.txt";
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> {
            analyzer.getContent(path);
        });
        assertNotNull(thrown.getMessage());
    }

    private void createFilesAndContentForTesting() throws IOException {
        new File(PATH_FOR_COUNTING_WORD).createNewFile();
        createContentForCounting();
        new File(UTF_8).createNewFile();
        new File(CONTENT_FOR_LIST_SENTENCES).createNewFile();
        createContentForListSentences();
        new File(CUSTOM_CONTENT).createNewFile();
        createContentForStr();
        new File(EMPTY_FILE).createNewFile();
        new File(NOT_SUPPORTED).createNewFile();
    }

    private void deleteFilesAndContentForTesting() {
        new File(PATH_FOR_COUNTING_WORD).delete();
        new File(UTF_8).delete();
        new File(CONTENT_FOR_LIST_SENTENCES).delete();
        new File(CUSTOM_CONTENT).delete();
        new File(EMPTY_FILE).delete();
        new File(NOT_SUPPORTED).delete();
    }

    private String getStringContent(String path) throws IOException {
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

    public int getWordCount(String word, String path) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer
                (Objects.requireNonNull(getStringContent(path)), " \n");
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().contains(word)) {
                count++;
            }
        }
        return count;
    }

    private List<String> getSentences() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer
                (Objects.requireNonNull(getStringContent(CONTENT_FOR_LIST_SENTENCES)), "[.?!]");
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            sentences.add(tokenizer.nextToken());
        }
        return sentences;
    }

    private List<String> getFilteredSentences() throws IOException {
        List<String> filterSentences = new ArrayList<>();
        for (String sentence : getSentences()) {
            if (sentence.contains("world")) {
                filterSentences.add(sentence);
            }
        }
        return filterSentences;
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
