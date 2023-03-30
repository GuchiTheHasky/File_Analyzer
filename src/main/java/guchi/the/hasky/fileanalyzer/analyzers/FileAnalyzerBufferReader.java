package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class FileAnalyzerBufferReader implements FileAnalyzer {
    //private static final Pattern SENTENCES_DELIMER = Pattern.compile("[.?!]");

    public static void main(String[] args) {
        FileAnalyzerBufferReader br = new FileAnalyzerBufferReader();
        String s = br.getContent("src/test/resources/fa/DuckStory.txt");
        System.out.println(s);
    }



    public FileInfo analyze(String path, String word) {
        validateSources(path, word);
        String content = getContent(path);
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = countWord(filteredSentences, word);
        return new FileInfo(wordCount, filteredSentences);
    }

    @Override
    public int countWord(String path, String word) {
        validateSources(path, word);
        String content = getContent(path);
        StringTokenizer tokenizer = new StringTokenizer(content);
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().contains(word)) {
                count++;
            }
        }
        return count;
    }

    @DefaultModifierForTests
    String getContent(String path) {
        File file = new File(path);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder contentBuilder = new StringBuilder();
            String content = "";
            while ((content = reader.readLine()) != null) {
                contentBuilder.append(content).append(System.lineSeparator());
            }
            return contentBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Exception during file is reading.", e);
        }
    }

    @DefaultModifierForTests
    List<String> getSentences(String content) {
        return List.of(AnalyzerTools.SENTENCES_DELIMER.split(content));
    }

    @DefaultModifierForTests
    List<String> getFilteredSentences(List<String> content, String word) {
        List<String> filteredSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filteredSentences.add(sentence);
            }
        }
        return filteredSentences;
    }

    @DefaultModifierForTests
    void validateSources(String path, String word) {
        if (path == null || word == null) {
            throw new NullPointerException("Error code 404. File name: " + path + "can't be null. " +
                    "String word: " + word + "can't be null;");
        }
        if (new File(path).length() == 0) {
            throw new IllegalArgumentException("File: " + path + " is empty.");
        }
    }

    int countWord(List<String> list, String word) {
        int count = 0;
        for (String sentence : list) {
            if (sentence.contains(word)) {
                count++;
            }
        }
        return count;
    }
}
