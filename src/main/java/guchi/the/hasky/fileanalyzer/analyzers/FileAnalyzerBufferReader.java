package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.utils.AnalyzerTools;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static guchi.the.hasky.fileanalyzer.utils.AnalyzerTools.*;


public class FileAnalyzerBufferReader implements FileAnalyzer {


    public FileInfo analyze(String path, String word) {
        validateSources(path, word);
        String content = getContent(path);
        List<String> sentences = getSentences(content);
        List<String> filteredSentences = getFilteredSentences(sentences, word);
        int wordCount = countWord(filteredSentences, word);
        return new FileInfo(wordCount, filteredSentences);
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
        return Arrays.asList(AnalyzerTools.SENTENCES_DELIMER.split(content));
    }

    @DefaultModifierForTests
    List<String> getFilteredSentences(List<String> content, String word) {
        List<String> filteredSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filteredSentences.add(sentence.trim());
            }
        }
        return filteredSentences;
    }


}
