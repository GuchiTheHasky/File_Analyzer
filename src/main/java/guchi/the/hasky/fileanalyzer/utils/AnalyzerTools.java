package guchi.the.hasky.fileanalyzer.utils;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class AnalyzerTools {
    public static final Pattern SENTENCES_DELIMER = Pattern.compile("[.?!]");
    public static final Pattern WORDS_DELIMER = Pattern.compile(" \n");

    public static int countWord(List<String> list, String word) {
        int count = 0;
        for (String sentence : list) {
            if (sentence.contains(word)) {
                count++;
            }
        }
        return count;
    }

    public static void validateSources(String path, String word) {
        validatePath(path);
        validateWord(word);
    }

    public static void validatePath(String path) {
        if (path == null) {
            throw new NullPointerException("Error, current value: " + path + " is null.");
        }
        File file = new File(path);
        if (file.isFile()) {
            if (file.length() == 0) {
                throw new IllegalArgumentException("Error, current file: " + path + " is empty.");
            }
        } else {
            throw new IllegalArgumentException("Error, current file: " + path + " doesn't exist.");
        }
    }

    public static void validateWord(String word) {
        if (word == null) {
            throw new NullPointerException("Error, current word is null.");
        }
        if (word.length() == 0) {
            throw new IllegalArgumentException("Error, word.length() can't be \"0\".");
        }
    }
}
