package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;
import lombok.Cleanup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static guchi.the.hasky.fileanalyzer.utils.AnalyzerTools.*;

public class FileAnalyzerInputStream implements FileAnalyzer {
    private static final String SENTENCES_DELIM = String.valueOf(Pattern.compile("[.?!]"));

    @Override
    public FileInfo analyze(String path, String word)  {
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
        return new FileInfo(wordCount, filterSentences);
    }

    @DefaultModifierForTests
    String getContent(String path) throws FileNotFoundException {
        File file = new File(path);
        int size = validateFileSize(file.length());
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

    @DefaultModifierForTests
    String encoder(String path) throws IOException {
        @Cleanup InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        return reader.getEncoding();
    }

    @DefaultModifierForTests
    List<String> sentences(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content, SENTENCES_DELIM);
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String sentence = tokenizer.nextToken().trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }
        return sentences;
    }

    @DefaultModifierForTests
    List<String> filterSentences(List<String> content, String word) {
        List<String> filterSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filterSentences.add(sentence);
            }
        }
        return filterSentences;
    }

    @DefaultModifierForTests
    int validateFileSize(Long length) {
        if (length > 8192) {
            throw new StackOverflowError("Error, file size is too large.");
        }
        return Math.toIntExact(length);
    }
}

/*Используем классы FileInputStream, FileOutputStream, File
Практика:
1: Написать программу FileAnalyzer, которая в консоли принимает 2 параметра:
1) путь к файлу
2) слово
Usage:
java FileAnalyzer C:/test/story.txt duck

Выводит:
1) Кол-во вхождений искомого слова в файле
2) Все предложения содержащие искомое слово(предложение заканчивается символами ".", "?", "!").
Каждое предложение выводится с новой строки.
*/
