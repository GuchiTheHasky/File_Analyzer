package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileAnalyzerStreamAPITest {

    @Test // 1.
    @DisplayName("Test, try to analyze empty file, throw exception.")
    public void testTryToAnalyzeEmptyFileThrowException() {
//        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
//            analyzer.validateSources(EMPTY_FILE, "Hello");
//        });
//        assertNotNull(thrown.getMessage());
    }

    @Test // 2.
    @DisplayName("Test, throw exception if file path is null.")
    public void testThrowNullPointExceptionIfFilePathIsNull() {
//        Throwable thrown = assertThrows(NullPointerException.class, () -> {
//            analyzer.validateSources(null, "Hello");
//        });
//        assertNotNull(thrown.getMessage());
    }

    @Test // 3.
    @DisplayName("Test, throw exception if String word is null.")
    public void testThrowNullPointExceptionIfStringWordIsNull() {
//        Throwable thrown = assertThrows(NullPointerException.class, () -> {
//            analyzer.validateSources("path", null);
//        });
//        assertNotNull(thrown.getMessage());
    }

    @Test // 4.
    @DisplayName("Test, get content from file source, return String, check length & content.")
    public void testGetContentFromSourceFileCheckLengthAndActualContentReturnString() throws IOException {
//        String expected = getContent(CUSTOM_CONTENT);
//        String actual = analyzer.getContent(CUSTOM_CONTENT);
//        assert expected != null;
//        assertEquals(expected.length(), actual.length());
//        assertEquals(expected, actual);
    }

    @Test // 5.
    @DisplayName("Test, calculate words count, check expected count.")
    public void testCalculateWordsCountFromSourceFileAndCheckExpectedCount() throws Exception {
//        String word = "java";
//        int expected = 10;
//        int actual = analyzer.countWord(PATH_FOR_COUNTING_WORD, word);
//        assertEquals(expected, actual);
    }

    @Test // 6.
    @DisplayName("Test, get content from source file, return List<Str> sentences.")
    public void testGetContentFromSourceFileReturnedListCheckSizeAndActualContent() throws IOException {
//        List<String> expected = getSentences();
//        List<String> actual = analyzer.getSentences(getContent(CONTENT_FOR_LIST_SENTENCES));
//        assertEquals(expected.size(), actual.size());
//        assertEquals(expected, actual);
//
//        String expectedFirst = expected.get(0);
//        String expectedMiddle = expected.get(expected.size() / 2);
//        String expectedLast = expected.get(expected.size() - 1);
//
//        String actualFirst = actual.get(0);
//        String actualMiddle = actual.get(expected.size() / 2);
//        String actualLast = actual.get(expected.size() - 1);
//
//        assertEquals(expectedFirst, actualFirst);
//        assertEquals(expectedMiddle, actualMiddle);
//        assertEquals(expectedLast, actualLast);
    }

    @Test // 7.
    @DisplayName("Test, access List<Str> sentences, return filtered List<Str> filtrSentences.")
    public void testGetListOfStringReturnFilteredListListCheckSizeAndActualContent() throws IOException {
//        List<String> expected = getFilteredSentences();
//        List<String> actual = analyzer.getFilteredSentences(getSentences(), "world");
//        assertEquals(expected.size(), actual.size());
//        assertEquals(expected, actual);
//
//        String expectedFirst = expected.get(0);
//        String expectedMiddle = expected.get(expected.size() / 2);
//        String expectedLast = expected.get(expected.size() - 1);
//
//        String actualFirst = actual.get(0);
//        String actualMiddle = actual.get(expected.size() / 2);
//        String actualLast = actual.get(expected.size() - 1);
//
//        assertEquals(expectedFirst, actualFirst);
//        assertEquals(expectedMiddle, actualMiddle);
//        assertEquals(expectedLast, actualLast);
    }

    @Test // 8.
    @DisplayName("Test, analyze source file, return object with words count.")
    public void testAnalyzeCheckExpectedFileInfoWordCountWithActualWordsCount() throws Exception {
//        String path = "src/test/resources/fileanalyzer/DuckStory.txt";
//        String word = "duck";
//
//        fileInfo = analyzer.analyze(path, word);
//
//        int expectedWordsCount = fileInfo.getCountWord();
//        int actualWordsCount = getWordCount(word, path);
//
//        assertEquals(expectedWordsCount, actualWordsCount);
    }

    @Test // 9.
    @DisplayName("Test, analyze source file, return object with List<Str> filterSentences, check List size & content.")
    public void testAnalyzeGetExpectedAndActualListsOfFilteredSentencesCheckSizeAndContent() {
//        String path = "src/test/resources/fileanalyzer/DuckStory.txt";
//        String word = "duck";
//        String content = analyzer.getContent(path);
//        List<String> sentences = analyzer.getSentences(content);
//
//        FileInfo info = analyzer.analyze(path, word);
//
//        List<String> expectedSentencesList = info.getSentences();
//        List<String> actualSentencesList = analyzer.getFilteredSentences(sentences, word);
//
//        assertEquals(expectedSentencesList.size(), actualSentencesList.size());
//        assertEquals(expectedSentencesList, actualSentencesList);
//
//        String expectedFirst = expectedSentencesList.get(0);
//        String expectedMiddle = expectedSentencesList.get(expectedSentencesList.size() / 2);
//        String expectedLast = expectedSentencesList.get(expectedSentencesList.size() - 1);
//
//        String actualFirst = actualSentencesList.get(0);
//        String actualMiddle = actualSentencesList.get(expectedSentencesList.size() / 2);
//        String actualLast = actualSentencesList.get(expectedSentencesList.size() - 1);
//
//        assertEquals(expectedFirst, actualFirst);
//        assertEquals(expectedMiddle, actualMiddle);
//        assertEquals(expectedLast, actualLast);
    }

    @Test // 10.
    @DisplayName("Test 10: try read file & throw FileNotFoundException if Current file doesn't exist.")
    public void testThrowFileNotFoundExceptionIfCurrentPathFileDoesNotExist() {
//        String path = "this/path/does/not/exist.txt";
//        Throwable thrown = assertThrows(RuntimeException.class, () -> {
//            analyzer.getContent(path);
//        });
//        assertNotNull(thrown.getMessage());
    }
}
