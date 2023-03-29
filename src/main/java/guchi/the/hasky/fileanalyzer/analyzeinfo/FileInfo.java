package guchi.the.hasky.fileanalyzer.analyzeinfo; // папку переіменувати в entity

import java.util.List;

public class FileInfo {
    private int countWord;
    private List<String> sentences;

    public FileInfo() {
    }

    public FileInfo(int countWord, List<String> sentences) {
        this.countWord = countWord;
        this.sentences = sentences;
    }

    public int getCountWord() {
        return countWord;
    }

    public List<String> getSentences() {
        return sentences;
    }

}

