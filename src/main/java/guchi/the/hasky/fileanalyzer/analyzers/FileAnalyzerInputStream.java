package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileStatistic;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static guchi.the.hasky.fileanalyzer.utils.AnalyzerTools.*;

public class FileAnalyzerInputStream implements FileAnalyzer {

    @Override
    public FileStatistic analyze(String path, String word) {
        validateSources(path, word);
        String content = "";
        try {
            content = getContent(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> sentences = sentences(content);
        List<String> filterSentences = filterSentences(sentences, word);
        int wordCount = countWord(filterSentences, word);
        return new FileStatistic(wordCount, filterSentences);
    }

    private String getContent(String path) throws FileNotFoundException {
        File file = new File(path);
        int size = validateFileSize(path);
        try (InputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[size];
            while (input.read(buffer) != -1) {
                return new String(buffer, encoder(path));
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error, current file: " + path + " doesn't exist.");
        }
        return null;
    }

    private List<String> sentences(String content) {
        return Arrays.asList(AnalyzerTools.SENTENCES_DELIMER.split(content));
    }

    private List<String> filterSentences(List<String> content, String word) {
        List<String> filterSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filterSentences.add(sentence.trim());
            }
        }
        return filterSentences;
    }
}

