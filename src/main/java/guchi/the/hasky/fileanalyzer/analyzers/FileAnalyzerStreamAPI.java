package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileAnalyzerStreamAPI implements FileAnalyzer {

    public static void main(String[] args) {
        String cont = getContent("src/test/resources/fileanalyzer/DuckStory.txt");
        List<String> sent = getSentences(cont);
        List<String> fit = getFilteredSentences(sent, "duck");
        System.out.println(fit.size());
        System.out.println(fit);
    }

    @Override
    public FileInfo analyze(String path, String word) {
        validateSources(path, word);
        String content = getContent(path);
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = countWord(filteredSentences, word);
        return new FileInfo(wordCount, filteredSentences);
    }

    @Override
    @SuppressWarnings("uncheked")
    public int countWord(String source, String word) {
        return 0;
    }

    int countWord(List<String> filteredSentences, String word) {
        return (int) filteredSentences.stream()
                .filter(sentence -> sentence.contains(word))
                .count();
    }


    static List<String> getFilteredSentences(List<String> sentences, String word) {
        return sentences.stream()
                .filter(sentence -> sentence.contains(word))
                .collect(Collectors.toList());
    }

    static List<String> getSentences(String content) {
            return Arrays.stream(AnalyzerTools.SENTENCES_DELIMER.split(content))
                    .map(String::trim)
                    .filter(sentences -> !sentences.isEmpty())
                    .collect(Collectors.toList());
    }

    static String getContent(String path) {
        validateSources(path);
        try {
            return Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error, you don't have access to read this file: " + path + '.');
        }
    }

    @DefaultModifierForTests
    static void validateSources(String entity) {
        if (entity == null) {
            throw new NullPointerException("Error, current value: " + entity + " is null.");
        }
        File file = new File(entity);
        if (file.isFile()) {
            if (file.length() == 0) {
                throw new IllegalArgumentException("Error, current file: " + entity + " is empty.");
            }
        } else {
            throw new IllegalArgumentException("Error, current file: " + entity + " doesn't exist.");
        }
    }

    private static void validateSources(String path, String word) {
        validateSources(path);
        validateSources(word);
    }

}


