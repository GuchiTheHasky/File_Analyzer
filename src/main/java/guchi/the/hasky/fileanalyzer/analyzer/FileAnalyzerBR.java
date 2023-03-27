package guchi.the.hasky.fileanalyzer.analyzer;

import guchi.the.hasky.fileanalyzer.analyzeinfo.FileInfo;
import guchi.the.hasky.fileanalyzer.annotations.TestsVisibility;
import guchi.the.hasky.fileanalyzer.interfaces.Analyze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class FileAnalyzerBR implements Analyze {
    private static final String SENTENCES_DELIM = String.valueOf(Pattern.compile("[.?!]"));
    private static final String WORDS_DELIM = String.valueOf(Pattern.compile(" \n"));

    @Override
    public FileInfo analyze(String path, String word) throws Exception {
        validateSources(path, word);
        String content = getContent(path);
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = wordCounter(filteredSentences, word);
        return new FileInfo(wordCount, filteredSentences);
    }

    @Override
    public int wordCount(String path, String word) throws Exception {
        validateSources(path, word);
        String content = getContent(path);
        StringTokenizer tokenizer = new StringTokenizer(content, WORDS_DELIM);
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().contains(word)) {
                count++;
            }
        }
        return count;
    }

    @TestsVisibility
    String getContent(String path) throws IOException {
        File file = new File(path);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder contentBuilder = new StringBuilder();
            String content = "";
            while ((content = reader.readLine()) != null) {
                contentBuilder.append(content).append(System.lineSeparator());
            }
            return contentBuilder.toString();
        }
    }

    @TestsVisibility
    List<String> getSentences(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content, SENTENCES_DELIM);
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            sentences.add(tokenizer.nextToken());
        }
        return sentences;
    }

    @TestsVisibility
    List<String> getFilteredSentences(List<String> content, String word) {
        List<String> filteredSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filteredSentences.add(sentence);
            }
        }
        return filteredSentences;
    }

    @TestsVisibility
    void validateSources(String path, String word) throws Exception {
        if (path == null || word == null) {
            throw new NullPointerException("Error code 404:\nFile name: " + path +
                    "can't be null;\nString word: " + word + "can't be null;");
        }
        String fileName = new File(path).getName();
        if (!fileName.endsWith(".txt") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")) {
            throw new UnsupportedOperationException("Current file format is not supported" + path);
        }
        if (new File(path).length() == 0) {
            throw new Exception("File: " + path + " is empty.");
        }
    }

    int wordCounter(List<String> list, String word) {
        int count = 0;
        for (String sentence : list) {
            if (sentence.contains(word)) {
                count++;
            }
        }
        return count;
    }
}
