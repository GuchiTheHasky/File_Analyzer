package guchi.the.hasky.fileanalyzer.interfaces;

import guchi.the.hasky.fileanalyzer.analyzeinfo.FileInfo;

public interface FileAnalyzer {
    /**
     * Return new FileInfo with:
     * List<String> sentences;
     * int count word;
     */
    FileInfo analyze(String source, String word); // забрати ексепшини з інтерфейсу

    /**
     * Return int count of current word in String.
     */
    int countWord(String source, String word);

}
