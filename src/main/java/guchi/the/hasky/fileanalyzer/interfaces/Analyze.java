package guchi.the.hasky.fileanalyzer.interfaces;

import guchi.the.hasky.fileanalyzer.analyzeinfo.FileInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public interface Analyze {
    /**
     * Return new FileInfo with:
     * List<String> sentences;
     * int count word;
     */
    FileInfo analyze(String source, String word) throws Exception;

    /**
     * Return int count of current word in String.
     */
    int wordCount(String source, String word) throws Exception;

}
