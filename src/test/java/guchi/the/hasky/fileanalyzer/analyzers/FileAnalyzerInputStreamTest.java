package guchi.the.hasky.fileanalyzer.analyzers;

public class FileAnalyzerInputStreamTest extends AbstractFileAnalyzerTest {


    @Override
    FileAnalyzerInputStream getAnalyzer() {
        return new FileAnalyzerInputStream();
    }

//    @Test // 11.
//    @DisplayName("Test, get file encoding.")
//    public void testGetFileEncodingFromSourceFileAndCheckReturnedString() throws IOException {
//        FileAnalyzerInputStream an = new FileAnalyzerInputStream();
//        String expected = "UTF8";
//        String actual = an.encoder(UTF_8);
//        assertEquals(expected, actual);
//        /* Хотів створити окремі файли з іншим кодуванням, але не зміг. Міняло кодування зразу в цілому проекті.*/
//    }

}
