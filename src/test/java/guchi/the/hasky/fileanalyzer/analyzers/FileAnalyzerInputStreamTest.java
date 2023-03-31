package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerInputStreamTest extends AbstractFileAnalyzerTest {


    @Override
    FileAnalyzerInputStream getAnalyzer() {
        return new FileAnalyzerInputStream();
    }


//
//    @Test // 5.
//    @DisplayName("Test, get content from file source, return String, check length & content.")
//    public void testGetContentFromSourceFileCheckLengthAndActualContentReturnString() throws IOException {
//        String expected = getStringContent(CUSTOM_CONTENT);
//        String actual = analyzer.getContent(CUSTOM_CONTENT);
//        assert expected != null;
//        assertEquals(expected.length(), actual.length());
//        assertEquals(expected, actual);
//    }
//


//
//    @Test // 11.
//    @DisplayName("Test, get file encoding.")
//    public void testGetFileEncodingFromSourceFileAndCheckReturnedString() throws IOException {
//        FileAnalyzerInputStream an = new FileAnalyzerInputStream();
//        String expected = "UTF8";
//        String actual = an.encoder(UTF_8);
//        assertEquals(expected, actual);
//        /* Хотів створити окремі файли з іншим кодуванням, але не зміг. Міняло кодування зразу в цілому проекті.*/
//    }
//
//    @Test // 12.
//    @DisplayName("Test, validate File Size.")
//    public void testValidateFileSizeIfFileIsToLargeThrowStackOverflowError() {
//        Long testValue = 8193L;
//        Throwable thrown = assertThrows(StackOverflowError.class, () -> {
//            analyzer.validateFileSize(testValue);
//        });
//        assertNotNull(thrown.getMessage());
//    }
}
