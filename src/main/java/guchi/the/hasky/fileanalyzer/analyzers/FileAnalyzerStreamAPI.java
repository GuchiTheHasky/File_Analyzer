package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileStatistic;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static guchi.the.hasky.fileanalyzer.utils.AnalyzerTools.*;

public class FileAnalyzerStreamAPI implements FileAnalyzer {

    @Override
    public FileStatistic analyze(String path, String word) {
        validateSources(path, word);
        validateFileSize(path);
        String content = "";
        try {
            content = getContent(path);
        } catch (IOException e) {
            throw new RuntimeException("Exception during file is reading.", e);
        }
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = countWord(filteredSentences, word);
        return new FileStatistic(wordCount, filteredSentences);
    }

    private int countWord(List<String> filteredSentences, String word) {
        return (int) filteredSentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
                .filter(word::equals)
                .count();
    }

    private List<String> getFilteredSentences(List<String> sentences, String word) {
        return sentences.stream()
                .filter(sentence -> sentence.contains(word))
                .collect(Collectors.toList());
    }

    private List<String> getSentences(String content) {
        return Arrays.stream(AnalyzerTools.SENTENCES_DELIMER.split(content))
                .map(String::trim)
                .filter(sentences -> !sentences.isEmpty())
                .collect(Collectors.toList());
    }

    private String getContent(String path) throws IOException {
        Charset charset = Charset.forName(encoder(path));
        try {
            return Files.lines(Paths.get(path), charset).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error, you don't have access to read this file: " + path + '.');
        }
    }
}


