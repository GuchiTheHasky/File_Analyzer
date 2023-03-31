package guchi.the.hasky.fileanalyzer.interfaces;

import guchi.the.hasky.fileanalyzer.entity.FileStatistic;

public interface FileAnalyzer {
    /**
     * Return new FileStatistic with:
     * List<String> sentences;
     * int count word;
     */
    FileStatistic analyze(String path, String word);
}
