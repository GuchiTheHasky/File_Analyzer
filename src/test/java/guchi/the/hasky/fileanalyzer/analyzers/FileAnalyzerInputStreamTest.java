package guchi.the.hasky.fileanalyzer.analyzers;

public class FileAnalyzerInputStreamTest extends AbstractFileAnalyzerTest {

    @Override
    FileAnalyzerInputStream getAnalyzer() {
        return new FileAnalyzerInputStream();
    }

}
