package guchi.the.hasky.fileanalyzer.analyzers;

import guchi.the.hasky.fileanalyzer.entity.FileInfo;
import guchi.the.hasky.fileanalyzer.utils.DefaultModifierForTests;
import guchi.the.hasky.fileanalyzer.interfaces.FileAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileAnalyzerInputStream implements FileAnalyzer {
    private static final String SENTENCES_DELIM = String.valueOf(Pattern.compile("[.?!]"));
    private static final String WORDS_DELIM = String.valueOf(Pattern.compile(" \n"));

    @Override
    public FileInfo analyze(String path, String word)  { //TODO: переробити
        try {
            validateSources(path, word);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String content = null;
        try {
            content = getContent(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int wordCount = countWord(path, word);
        List<String> sentences = sentences(content);
        List<String> filterSentences = filterSentences(sentences, word);
        return new FileInfo(wordCount, filterSentences);
    }

    @Override
    public int countWord(String path, String word)  {
        try {
            validateSources(path, word);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String content = null;
        try {
            content = getContent(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringTokenizer tokenizer = new StringTokenizer(content, WORDS_DELIM);
        int count = 0;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().contains(word)) {
                count++;
            }
        }
        return count;
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
        InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        String encoder = reader.getEncoding();
        reader.close();
        return encoder;
    }

    @DefaultModifierForTests
    List<String> sentences(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content, SENTENCES_DELIM);
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            sentences.add(tokenizer.nextToken());
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

    @DefaultModifierForTests
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
            throw new Exception("File :" + path + " is empty.");
        }
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
