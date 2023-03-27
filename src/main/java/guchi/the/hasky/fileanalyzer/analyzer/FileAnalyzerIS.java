package guchi.the.hasky.fileanalyzer.analyzer;

import guchi.the.hasky.fileanalyzer.analyzeinfo.FileInfo;
import guchi.the.hasky.fileanalyzer.annotations.TestsVisibility;
import guchi.the.hasky.fileanalyzer.interfaces.Analyze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileAnalyzerIS implements Analyze {
    private static final String SENTENCES_DELIM = String.valueOf(Pattern.compile("[.?!]"));
    private static final String WORDS_DELIM = String.valueOf(Pattern.compile(" \n"));

    @Override
    public FileInfo analyze(String path, String word) throws Exception { //TODO: переробити
        validateSources(path, word);
        String content = getContent(path);
        int wordCount = wordCount(path, word);
        List<String> sentences = sentences(content);
        List<String> filterSentences = filterSentences(sentences, word);
        return new FileInfo(wordCount, filterSentences);
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
    String getContent(String path) throws FileNotFoundException {
        File file = new File(path);
        try (InputStream input = new FileInputStream(file)) {
            int size = validateFileSize(file.length());
            byte[] buffer = new byte[size];
            while (input.read(buffer) != -1) {
                return new String(buffer, encoder(path));
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Error, current file: " + path + " doesn't exist.");
        }
        return null;
    }

    @TestsVisibility
    String encoder(String path) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
        String encoder = reader.getEncoding();
        reader.close();
        return encoder;
    }

    @TestsVisibility
    List<String> sentences(String content) {
        StringTokenizer tokenizer = new StringTokenizer(content, SENTENCES_DELIM);
        List<String> sentences = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            sentences.add(tokenizer.nextToken());
        }
        return sentences;
    }

    @TestsVisibility
    List<String> filterSentences(List<String> content, String word) {
        List<String> filterSentences = new ArrayList<>();
        for (String sentence : content) {
            if (sentence.contains(word)) {
                filterSentences.add(sentence);
            }
        }
        return filterSentences;
    }

    @TestsVisibility
    int validateFileSize(Long length) {
        if (length > 8192) {
            throw new StackOverflowError("Error, file size is too large.");
        }
        return Math.toIntExact(length);
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
