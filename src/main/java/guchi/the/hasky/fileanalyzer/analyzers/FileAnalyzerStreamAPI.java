package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static guchi.the.hasky.fileanalyzer.utils.AnalyzerTools.*;

public class FileAnalyzerStreamAPI implements FileAnalyzer {

    @Override
    public FileInfo analyze(String path, String word) {
        validateSources(path, word);
        String content = getContent(path);
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = countWord(filteredSentences, word);
        return new FileInfo(wordCount, filteredSentences);
    }

    int countWord(List<String> filteredSentences, String word) {
        return (int) filteredSentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
                .filter(word::equals)
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

    @DefaultModifierForTests
    String getContent(String path) {
        try {
            return Files.lines(Paths.get(path)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error, you don't have access to read this file: " + path + '.');
        }
    }
}


